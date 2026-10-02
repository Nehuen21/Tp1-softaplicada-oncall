# Justificación de diseño — Dependencia entre servicios

Extensión asignada: modelar dependencias entre servicios, de forma que se pueda
calcular criticidad y propagar el impacto de un incidente a los servicios que
dependen del servicio afectado.

agregamos tres entidades: `DependenciaDeServicio`, `ServicioCritico` e
`ImpactoPropagado`. Van dentro del Bloque 1 (Catálogo) del `oncall.jh`, porque
responden a la misma pregunta sobre qué operamos y cómo se relacionan
entre sí.

## DependenciaDeServicio

Es una relación muchos a muchos entre `Servicio` y sí mismo, pero con datos
propios sobre la relación (peso, descripción). JDL no permite atributos sobre
una relación ManyToMany, así que lo resolvemos  con una entidad intermedia y dos
relaciones ManyToOne hacia `Servicio`.

Las dos relaciones se llaman `dependiente` y `origen``. 

El atributo `peso` es un enum (`PesoDependencia`: DEGRADA, AFECTA, NO_AFECTA,
ROMPE) y describe qué tan acoplados están los dos servicios en general,
siempre, independiente de que haya o no un incidente corriendo. Es un dato de
diseño estático, no un hecho puntual

Paginada con infinite-scroll, porque el volumen esperado crece con la
cantidad de servicios (hasta N×(N-1) en el peor caso) y no tiene sentido
navegarla por el número de página. También lleva filter, porque hay casos de uso
concretos como "dependencias con peso ROMPE" o "dependencias de tal servicio".

## ServicioCritico

Crítico no es algo que alguien declara a mano: se calcula a partir del grafo
de dependencias (cuántos servicios dependen de uno). Pero el cálculo en sí
(un COUNT sobre DependenciaDeServicio) no necesita persistirse como tabla —
lo que sí necesita persistirse es el momento en que un servicio cruzó el
umbral y se volvió crítico, porque esa transición no se puede reconstruir
mirando solo el estado actual del grafo.

Por eso `ServicioCritico` es una bitácora de eventos, no una bandera. Cada
fila es un cruce de umbral puntual: `volvioCriticoEn` (Instant, required) y
`motivo` (String, texto libre explicando la foto del grafo en ese momento,
sin atar el evento a ninguna dependencia puntual porque el criterio es el
conjunto completo, no una dependencia aislada).

Relación ManyToOne simple hacia `Servicio` (muchos eventos de criticidad por
servicio a lo largo del tiempo). No es ManyToMany ni necesita tabla
intermedia: el patrón es el mismo que `EventoDeIncidente` respecto de
`Incidente` en el modelo original, una bitácora colgando de una entidad.

Se decidió una fila nueva por cada cruce de umbral, en vez de actualizar un
único registro, para no perder trazabilidad. Un servicio que oscila
constantemente cruzando el umbral es en sí mismo una señal de alarma, y eso
solo se puede ver con historial completo. Buscamos destacar que un servicio
esta fallando constantemente, y necesita otro tipo de atencion

Paginada con infinite-scroll (misma lógica que DependenciaDeServicio). Sin
filter: es una bitácora que se recorre cronológicamente, no se consulta por
condiciones complejas desde la API, igual que EventoDeIncidente en el
original.

## ImpactoPropagado

Conecta el incidente real con cada servicio afectado indirectamente por la
cadena de dependencias. No es ManyToMany: es un evento (una fila = "en tal
incidente, tal servicio se vio afectado, por tal camino, en tal momento"),
mismo patrón que EventoDeIncidente o ServicioCritico.

Tiene tres relaciones ManyToOne, una por cada dato necesario para explicar el
hecho sin tener que reconstruirlo desde otro lado:

- `incidente`, hacia `Incidente`: qué incidente originó el impacto.
- `servicio`, hacia `Servicio`: qué servicio quedó afectado.
- `dependencia`, hacia `DependenciaDeServicio`: por qué camino llegó el
  impacto. Sin esta referencia no se podría reconstruir por qué un servicio
  determinado apareció como impactado en un incidente dado.

El atributo `severidad` reutiliza el enum `Severidad` que ya existe en el
modelo, en vez de crear uno nuevo. No se calcula en esta entrega: el campo
queda listo para que la regla de negocio (que se resuelve en una clase
posterior) lo complete, de la misma forma que `Incidente.cumplioObjetivo` ya
existe en el modelo sin que todavía exista la lógica que lo llena.

`desde` es Instant required (siempre se sabe al crear el registro, igual que
Incidente.detectadoEn). `hasta` es Instant sin required, porque puede quedar
en null mientras el servicio sigue degradado — se completa después, cuando
se sabe que el impacto terminó.

Paginada con infinite-scroll y con filter (por ejemplo, para consultar
"impactos activos ahora mismo", filtrando por hasta vacío, o por servicio).

## Regla de negocio, para ver en la clase: 

