package ar.edu.um.isa.oncall.service.criteria;

import ar.edu.um.isa.oncall.domain.enumeration.Severidad;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ar.edu.um.isa.oncall.domain.ImpactoPropagado} entity. This class is used
 * in {@link ar.edu.um.isa.oncall.web.rest.ImpactoPropagadoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /impacto-propagados?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImpactoPropagadoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Severidad
     */
    public static class SeveridadFilter extends Filter<Severidad> {

        public SeveridadFilter() {}

        public SeveridadFilter(SeveridadFilter filter) {
            super(filter);
        }

        @Override
        public SeveridadFilter copy() {
            return new SeveridadFilter(this);
        }
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SeveridadFilter severidad;

    private InstantFilter desde;

    private InstantFilter hasta;

    private LongFilter incidenteId;

    private LongFilter servicioId;

    private LongFilter dependenciaId;

    private Boolean distinct;

    public ImpactoPropagadoCriteria() {}

    public ImpactoPropagadoCriteria(ImpactoPropagadoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.severidad = other.optionalSeveridad().map(SeveridadFilter::copy).orElse(null);
        this.desde = other.optionalDesde().map(InstantFilter::copy).orElse(null);
        this.hasta = other.optionalHasta().map(InstantFilter::copy).orElse(null);
        this.incidenteId = other.optionalIncidenteId().map(LongFilter::copy).orElse(null);
        this.servicioId = other.optionalServicioId().map(LongFilter::copy).orElse(null);
        this.dependenciaId = other.optionalDependenciaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ImpactoPropagadoCriteria copy() {
        return new ImpactoPropagadoCriteria(this);
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

    public SeveridadFilter getSeveridad() {
        return severidad;
    }

    public Optional<SeveridadFilter> optionalSeveridad() {
        return Optional.ofNullable(severidad);
    }

    public SeveridadFilter severidad() {
        if (severidad == null) {
            setSeveridad(new SeveridadFilter());
        }
        return severidad;
    }

    public void setSeveridad(SeveridadFilter severidad) {
        this.severidad = severidad;
    }

    public InstantFilter getDesde() {
        return desde;
    }

    public Optional<InstantFilter> optionalDesde() {
        return Optional.ofNullable(desde);
    }

    public InstantFilter desde() {
        if (desde == null) {
            setDesde(new InstantFilter());
        }
        return desde;
    }

    public void setDesde(InstantFilter desde) {
        this.desde = desde;
    }

    public InstantFilter getHasta() {
        return hasta;
    }

    public Optional<InstantFilter> optionalHasta() {
        return Optional.ofNullable(hasta);
    }

    public InstantFilter hasta() {
        if (hasta == null) {
            setHasta(new InstantFilter());
        }
        return hasta;
    }

    public void setHasta(InstantFilter hasta) {
        this.hasta = hasta;
    }

    public LongFilter getIncidenteId() {
        return incidenteId;
    }

    public Optional<LongFilter> optionalIncidenteId() {
        return Optional.ofNullable(incidenteId);
    }

    public LongFilter incidenteId() {
        if (incidenteId == null) {
            setIncidenteId(new LongFilter());
        }
        return incidenteId;
    }

    public void setIncidenteId(LongFilter incidenteId) {
        this.incidenteId = incidenteId;
    }

    public LongFilter getServicioId() {
        return servicioId;
    }

    public Optional<LongFilter> optionalServicioId() {
        return Optional.ofNullable(servicioId);
    }

    public LongFilter servicioId() {
        if (servicioId == null) {
            setServicioId(new LongFilter());
        }
        return servicioId;
    }

    public void setServicioId(LongFilter servicioId) {
        this.servicioId = servicioId;
    }

    public LongFilter getDependenciaId() {
        return dependenciaId;
    }

    public Optional<LongFilter> optionalDependenciaId() {
        return Optional.ofNullable(dependenciaId);
    }

    public LongFilter dependenciaId() {
        if (dependenciaId == null) {
            setDependenciaId(new LongFilter());
        }
        return dependenciaId;
    }

    public void setDependenciaId(LongFilter dependenciaId) {
        this.dependenciaId = dependenciaId;
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
        final ImpactoPropagadoCriteria that = (ImpactoPropagadoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(severidad, that.severidad) &&
            Objects.equals(desde, that.desde) &&
            Objects.equals(hasta, that.hasta) &&
            Objects.equals(incidenteId, that.incidenteId) &&
            Objects.equals(servicioId, that.servicioId) &&
            Objects.equals(dependenciaId, that.dependenciaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, severidad, desde, hasta, incidenteId, servicioId, dependenciaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImpactoPropagadoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSeveridad().map(f -> "severidad=" + f + ", ").orElse("") +
            optionalDesde().map(f -> "desde=" + f + ", ").orElse("") +
            optionalHasta().map(f -> "hasta=" + f + ", ").orElse("") +
            optionalIncidenteId().map(f -> "incidenteId=" + f + ", ").orElse("") +
            optionalServicioId().map(f -> "servicioId=" + f + ", ").orElse("") +
            optionalDependenciaId().map(f -> "dependenciaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
