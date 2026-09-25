package ar.edu.um.isa.oncall.domain;

import ar.edu.um.isa.oncall.domain.enumeration.PesoDependencia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DependenciaDeServicio.
 */
@Entity
@Table(name = "dependencia_de_servicio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependenciaDeServicio implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "peso", nullable = false)
    private PesoDependencia peso;

    @Size(max = 400)
    @Column(name = "descripcion", length = 400)
    private String descripcion;

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
    private Servicio dependiente;

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
    private Servicio origen;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dependencia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "incidente", "servicio", "dependencia" }, allowSetters = true)
    private Set<ImpactoPropagado> impactos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DependenciaDeServicio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PesoDependencia getPeso() {
        return this.peso;
    }

    public DependenciaDeServicio peso(PesoDependencia peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(PesoDependencia peso) {
        this.peso = peso;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public DependenciaDeServicio descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Servicio getDependiente() {
        return this.dependiente;
    }

    public void setDependiente(Servicio servicio) {
        this.dependiente = servicio;
    }

    public DependenciaDeServicio dependiente(Servicio servicio) {
        this.setDependiente(servicio);
        return this;
    }

    public Servicio getOrigen() {
        return this.origen;
    }

    public void setOrigen(Servicio servicio) {
        this.origen = servicio;
    }

    public DependenciaDeServicio origen(Servicio servicio) {
        this.setOrigen(servicio);
        return this;
    }

    public Set<ImpactoPropagado> getImpactos() {
        return this.impactos;
    }

    public void setImpactos(Set<ImpactoPropagado> impactoPropagados) {
        if (this.impactos != null) {
            this.impactos.forEach(i -> i.setDependencia(null));
        }
        if (impactoPropagados != null) {
            impactoPropagados.forEach(i -> i.setDependencia(this));
        }
        this.impactos = impactoPropagados;
    }

    public DependenciaDeServicio impactos(Set<ImpactoPropagado> impactoPropagados) {
        this.setImpactos(impactoPropagados);
        return this;
    }

    public DependenciaDeServicio addImpacto(ImpactoPropagado impactoPropagado) {
        this.impactos.add(impactoPropagado);
        impactoPropagado.setDependencia(this);
        return this;
    }

    public DependenciaDeServicio removeImpacto(ImpactoPropagado impactoPropagado) {
        this.impactos.remove(impactoPropagado);
        impactoPropagado.setDependencia(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DependenciaDeServicio)) {
            return false;
        }
        return getId() != null && getId().equals(((DependenciaDeServicio) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DependenciaDeServicio{" +
            "id=" + getId() +
            ", peso='" + getPeso() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
