package ar.edu.um.isa.oncall.service.dto;

import ar.edu.um.isa.oncall.domain.enumeration.Severidad;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ar.edu.um.isa.oncall.domain.ImpactoPropagado} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImpactoPropagadoDTO implements Serializable {

    private Long id;

    @NotNull
    private Severidad severidad;

    @NotNull
    private Instant desde;

    private Instant hasta;

    @NotNull
    private IncidenteDTO incidente;

    @NotNull
    private ServicioDTO servicio;

    @NotNull
    private DependenciaDeServicioDTO dependencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Severidad getSeveridad() {
        return severidad;
    }

    public void setSeveridad(Severidad severidad) {
        this.severidad = severidad;
    }

    public Instant getDesde() {
        return desde;
    }

    public void setDesde(Instant desde) {
        this.desde = desde;
    }

    public Instant getHasta() {
        return hasta;
    }

    public void setHasta(Instant hasta) {
        this.hasta = hasta;
    }

    public IncidenteDTO getIncidente() {
        return incidente;
    }

    public void setIncidente(IncidenteDTO incidente) {
        this.incidente = incidente;
    }

    public ServicioDTO getServicio() {
        return servicio;
    }

    public void setServicio(ServicioDTO servicio) {
        this.servicio = servicio;
    }

    public DependenciaDeServicioDTO getDependencia() {
        return dependencia;
    }

    public void setDependencia(DependenciaDeServicioDTO dependencia) {
        this.dependencia = dependencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImpactoPropagadoDTO)) {
            return false;
        }

        ImpactoPropagadoDTO impactoPropagadoDTO = (ImpactoPropagadoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, impactoPropagadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImpactoPropagadoDTO{" +
            "id=" + getId() +
            ", severidad='" + getSeveridad() + "'" +
            ", desde='" + getDesde() + "'" +
            ", hasta='" + getHasta() + "'" +
            ", incidente=" + getIncidente() +
            ", servicio=" + getServicio() +
            ", dependencia=" + getDependencia() +
            "}";
    }
}
