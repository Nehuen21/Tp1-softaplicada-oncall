package ar.edu.um.isa.oncall.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServicioCritico.
 */
@Entity
@Table(name = "servicio_critico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicioCritico implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "volvio_critico_en", nullable = false)
    private Instant volvioCriticoEn;

    @Size(max = 400)
    @Column(name = "motivo", length = 400)
    private String motivo;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServicioCritico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getVolvioCriticoEn() {
        return this.volvioCriticoEn;
    }

    public ServicioCritico volvioCriticoEn(Instant volvioCriticoEn) {
        this.setVolvioCriticoEn(volvioCriticoEn);
        return this;
    }

    public void setVolvioCriticoEn(Instant volvioCriticoEn) {
        this.volvioCriticoEn = volvioCriticoEn;
    }

    public String getMotivo() {
        return this.motivo;
    }

    public ServicioCritico motivo(String motivo) {
        this.setMotivo(motivo);
        return this;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Servicio getServicio() {
        return this.servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public ServicioCritico servicio(Servicio servicio) {
        this.setServicio(servicio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicioCritico)) {
            return false;
        }
        return getId() != null && getId().equals(((ServicioCritico) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicioCritico{" +
            "id=" + getId() +
            ", volvioCriticoEn='" + getVolvioCriticoEn() + "'" +
            ", motivo='" + getMotivo() + "'" +
            "}";
    }
}
