package ar.edu.um.isa.oncall.service.criteria;

import ar.edu.um.isa.oncall.domain.enumeration.PesoDependencia;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ar.edu.um.isa.oncall.domain.DependenciaDeServicio} entity. This class is used
 * in {@link ar.edu.um.isa.oncall.web.rest.DependenciaDeServicioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dependencia-de-servicios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependenciaDeServicioCriteria implements Serializable, Criteria {

    /**
     * Class for filtering PesoDependencia
     */
    public static class PesoDependenciaFilter extends Filter<PesoDependencia> {

        public PesoDependenciaFilter() {}

        public PesoDependenciaFilter(PesoDependenciaFilter filter) {
            super(filter);
        }

        @Override
        public PesoDependenciaFilter copy() {
            return new PesoDependenciaFilter(this);
        }
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private PesoDependenciaFilter peso;

    private StringFilter descripcion;

    private LongFilter dependienteId;

    private LongFilter origenId;

    private LongFilter impactoId;

    private Boolean distinct;

    public DependenciaDeServicioCriteria() {}

    public DependenciaDeServicioCriteria(DependenciaDeServicioCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.peso = other.optionalPeso().map(PesoDependenciaFilter::copy).orElse(null);
        this.descripcion = other.optionalDescripcion().map(StringFilter::copy).orElse(null);
        this.dependienteId = other.optionalDependienteId().map(LongFilter::copy).orElse(null);
        this.origenId = other.optionalOrigenId().map(LongFilter::copy).orElse(null);
        this.impactoId = other.optionalImpactoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DependenciaDeServicioCriteria copy() {
        return new DependenciaDeServicioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public PesoDependenciaFilter getPeso() {
        return peso;
    }

    public Optional<PesoDependenciaFilter> optionalPeso() {
        return Optional.ofNullable(peso);
    }

    public PesoDependenciaFilter peso() {
        if (peso == null) {
            setPeso(new PesoDependenciaFilter());
        }
        return peso;
    }

    public void setPeso(PesoDependenciaFilter peso) {
        this.peso = peso;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public Optional<StringFilter> optionalDescripcion() {
        return Optional.ofNullable(descripcion);
    }

    public StringFilter descripcion() {
        if (descripcion == null) {
            setDescripcion(new StringFilter());
        }
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public LongFilter getDependienteId() {
        return dependienteId;
    }

    public Optional<LongFilter> optionalDependienteId() {
        return Optional.ofNullable(dependienteId);
    }

    public LongFilter dependienteId() {
        if (dependienteId == null) {
            setDependienteId(new LongFilter());
        }
        return dependienteId;
    }

    public void setDependienteId(LongFilter dependienteId) {
        this.dependienteId = dependienteId;
    }

    public LongFilter getOrigenId() {
        return origenId;
    }

    public Optional<LongFilter> optionalOrigenId() {
        return Optional.ofNullable(origenId);
    }

    public LongFilter origenId() {
        if (origenId == null) {
            setOrigenId(new LongFilter());
        }
        return origenId;
    }

    public void setOrigenId(LongFilter origenId) {
        this.origenId = origenId;
    }

    public LongFilter getImpactoId() {
        return impactoId;
    }

    public Optional<LongFilter> optionalImpactoId() {
        return Optional.ofNullable(impactoId);
    }

    public LongFilter impactoId() {
        if (impactoId == null) {
            setImpactoId(new LongFilter());
        }
        return impactoId;
    }

    public void setImpactoId(LongFilter impactoId) {
        this.impactoId = impactoId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DependenciaDeServicioCriteria that = (DependenciaDeServicioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(peso, that.peso) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(dependienteId, that.dependienteId) &&
            Objects.equals(origenId, that.origenId) &&
            Objects.equals(impactoId, that.impactoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, peso, descripcion, dependienteId, origenId, impactoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DependenciaDeServicioCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPeso().map(f -> "peso=" + f + ", ").orElse("") +
            optionalDescripcion().map(f -> "descripcion=" + f + ", ").orElse("") +
            optionalDependienteId().map(f -> "dependienteId=" + f + ", ").orElse("") +
            optionalOrigenId().map(f -> "origenId=" + f + ", ").orElse("") +
            optionalImpactoId().map(f -> "impactoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
