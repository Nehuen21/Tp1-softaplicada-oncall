import dayjs from 'dayjs/esm';

import { IDependenciaDeServicio } from 'app/entities/dependencia-de-servicio/dependencia-de-servicio.model';
import { Severidad } from 'app/entities/enumerations/severidad.model';
import { IIncidente } from 'app/entities/incidente/incidente.model';
import { IServicio } from 'app/entities/servicio/servicio.model';

export interface IImpactoPropagado {
  id: number;
  severidad?: keyof typeof Severidad | null;
  desde?: dayjs.Dayjs | null;
  hasta?: dayjs.Dayjs | null;
  incidente?: Pick<IIncidente, 'id' | 'titulo'> | null;
  servicio?: Pick<IServicio, 'id' | 'nombre'> | null;
  dependencia?: Pick<IDependenciaDeServicio, 'id' | 'descripcion'> | null;
}

export type NewImpactoPropagado = Omit<IImpactoPropagado, 'id'> & { id: null };
