package ar.edu.um.isa.oncall.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ar.edu.um.isa.oncall.domain.ServicioCritico} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicioCriticoDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant volvioCriticoEn;

    @Size(max = 400)
    private String motivo;

    @NotNull
    private ServicioDTO servicio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getVolvioCriticoEn() {
        return volvioCriticoEn;
    }

    public void setVolvioCriticoEn(Instant volvioCriticoEn) {
        this.volvioCriticoEn = volvioCriticoEn;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public ServicioDTO getServicio() {
        return servicio;
    }

    public void setServicio(ServicioDTO servicio) {
        this.servicio = servicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicioCriticoDTO)) {
            return false;
        }

        ServicioCriticoDTO servicioCriticoDTO = (ServicioCriticoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, servicioCriticoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicioCriticoDTO{" +
            "id=" + getId() +
            ", volvioCriticoEn='" + getVolvioCriticoEn() + "'" +
            ", motivo='" + getMotivo() + "'" +
            ", servicio=" + getServicio() +
            "}";
    }
}
