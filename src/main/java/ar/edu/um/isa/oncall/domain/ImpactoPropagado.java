package ar.edu.um.isa.oncall.domain;

import ar.edu.um.isa.oncall.domain.enumeration.Severidad;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ImpactoPropagado.
 */
@Entity
@Table(name = "impacto_propagado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImpactoPropagado implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "severidad", nullable = false)
    private Severidad severidad;

    @NotNull
    @Column(name = "desde", nullable = false)
    private Instant desde;

    @Column(name = "hasta")
    private Instant hasta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "comandante", "servicios", "postmortem", "alertas", "eventos", "notificacions", "impactos" },
        allowSetters = true
    )
    private Incidente incidente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "equipo",
            "objetivos",
            "alertas",
            "politicas",
            "comoDependientes",
            "comoOrigens",
            "servicioCriticos",
            "impactos",
            "incidentes",
        },
        allowSetters = true
    )
    private Servicio servicio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "dependiente", "origen", "impactos" }, allowSetters = true)
    private DependenciaDeServicio dependencia;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ImpactoPropagado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Severidad getSeveridad() {
        return this.severidad;
    }

    public ImpactoPropagado severidad(Severidad severidad) {
        this.setSeveridad(severidad);
        return this;
    }

    public void setSeveridad(Severidad severidad) {
        this.severidad = severidad;
    }

    public Instant getDesde() {
        return this.desde;
    }

    public ImpactoPropagado desde(Instant desde) {
        this.setDesde(desde);
        return this;
    }

    public void setDesde(Instant desde) {
        this.desde = desde;
    }

    public Instant getHasta() {
        return this.hasta;
    }

    public ImpactoPropagado hasta(Instant hasta) {
        this.setHasta(hasta);
        return this;
    }

    public void setHasta(Instant hasta) {
        this.hasta = hasta;
    }

    public Incidente getIncidente() {
        return this.incidente;
    }

    public void setIncidente(Incidente incidente) {
        this.incidente = incidente;
    }

    public ImpactoPropagado incidente(Incidente incidente) {
        this.setIncidente(incidente);
        return this;
    }

    public Servicio getServicio() {
        return this.servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public ImpactoPropagado servicio(Servicio servicio) {
        this.setServicio(servicio);
        return this;
    }

    public DependenciaDeServicio getDependencia() {
        return this.dependencia;
    }

    public void setDependencia(DependenciaDeServicio dependenciaDeServicio) {
        this.dependencia = dependenciaDeServicio;
    }

    public ImpactoPropagado dependencia(DependenciaDeServicio dependenciaDeServicio) {
        this.setDependencia(dependenciaDeServicio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImpactoPropagado)) {
            return false;
        }
        return getId() != null && getId().equals(((ImpactoPropagado) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImpactoPropagado{" +
            "id=" + getId() +
            ", severidad='" + getSeveridad() + "'" +
            ", desde='" + getDesde() + "'" +
            ", hasta='" + getHasta() + "'" +
            "}";
    }
}
