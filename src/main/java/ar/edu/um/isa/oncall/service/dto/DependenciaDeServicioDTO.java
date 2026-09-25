package ar.edu.um.isa.oncall.service.dto;

import ar.edu.um.isa.oncall.domain.enumeration.PesoDependencia;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ar.edu.um.isa.oncall.domain.DependenciaDeServicio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependenciaDeServicioDTO implements Serializable {

    private Long id;

    @NotNull
    private PesoDependencia peso;

    @Size(max = 400)
    private String descripcion;

    @NotNull
    private ServicioDTO dependiente;

    @NotNull
    private ServicioDTO origen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PesoDependencia getPeso() {
        return peso;
    }

    public void setPeso(PesoDependencia peso) {
        this.peso = peso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ServicioDTO getDependiente() {
        return dependiente;
    }

    public void setDependiente(ServicioDTO dependiente) {
        this.dependiente = dependiente;
    }

    public ServicioDTO getOrigen() {
        return origen;
    }

    public void setOrigen(ServicioDTO origen) {
        this.origen = origen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DependenciaDeServicioDTO)) {
            return false;
        }

        DependenciaDeServicioDTO dependenciaDeServicioDTO = (DependenciaDeServicioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dependenciaDeServicioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DependenciaDeServicioDTO{" +
            "id=" + getId() +
            ", peso='" + getPeso() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", dependiente=" + getDependiente() +
            ", origen=" + getOrigen() +
            "}";
    }
}
