package ar.edu.um.isa.oncall.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ImpactoPropagadoCriteriaTest {

    @Test
    void newImpactoPropagadoCriteriaHasAllFiltersNullTest() {
        var impactoPropagadoCriteria = new ImpactoPropagadoCriteria();
        assertThat(impactoPropagadoCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void impactoPropagadoCriteriaFluentMethodsCreatesFiltersTest() {
        var impactoPropagadoCriteria = new ImpactoPropagadoCriteria();

        setAllFilters(impactoPropagadoCriteria);

        assertThat(impactoPropagadoCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void impactoPropagadoCriteriaCopyCreatesNullFilterTest() {
        var impactoPropagadoCriteria = new ImpactoPropagadoCriteria();
        var copy = impactoPropagadoCriteria.copy();

        assertThat(impactoPropagadoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(copyFiltersAre(copy, (a, b) -> a == null || a instanceof Boolean ? a == b : a != b && a.equals(b))),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(impactoPropagadoCriteria)
        );
    }

    @Test
    void impactoPropagadoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var impactoPropagadoCriteria = new ImpactoPropagadoCriteria();
        setAllFilters(impactoPropagadoCriteria);

        var copy = impactoPropagadoCriteria.copy();

        assertThat(impactoPropagadoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(copyFiltersAre(copy, (a, b) -> a == null || a instanceof Boolean ? a == b : a != b && a.equals(b))),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(impactoPropagadoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var impactoPropagadoCriteria = new ImpactoPropagadoCriteria();

        assertThat(impactoPropagadoCriteria).hasToString("ImpactoPropagadoCriteria{}");
    }

    private static void setAllFilters(ImpactoPropagadoCriteria impactoPropagadoCriteria) {
        impactoPropagadoCriteria.id();
        impactoPropagadoCriteria.severidad();
        impactoPropagadoCriteria.desde();
        impactoPropagadoCriteria.hasta();
        impactoPropagadoCriteria.incidenteId();
        impactoPropagadoCriteria.servicioId();
        impactoPropagadoCriteria.dependenciaId();
        impactoPropagadoCriteria.distinct();
    }

    private static Condition<ImpactoPropagadoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSeveridad()) &&
                condition.apply(criteria.getDesde()) &&
                condition.apply(criteria.getHasta()) &&
                condition.apply(criteria.getIncidenteId()) &&
                condition.apply(criteria.getServicioId()) &&
                condition.apply(criteria.getDependenciaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ImpactoPropagadoCriteria> copyFiltersAre(
        ImpactoPropagadoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSeveridad(), copy.getSeveridad()) &&
                condition.apply(criteria.getDesde(), copy.getDesde()) &&
                condition.apply(criteria.getHasta(), copy.getHasta()) &&
                condition.apply(criteria.getIncidenteId(), copy.getIncidenteId()) &&
                condition.apply(criteria.getServicioId(), copy.getServicioId()) &&
                condition.apply(criteria.getDependenciaId(), copy.getDependenciaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
