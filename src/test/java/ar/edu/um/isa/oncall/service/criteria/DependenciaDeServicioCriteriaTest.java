package ar.edu.um.isa.oncall.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DependenciaDeServicioCriteriaTest {

    @Test
    void newDependenciaDeServicioCriteriaHasAllFiltersNullTest() {
        var dependenciaDeServicioCriteria = new DependenciaDeServicioCriteria();
        assertThat(dependenciaDeServicioCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void dependenciaDeServicioCriteriaFluentMethodsCreatesFiltersTest() {
        var dependenciaDeServicioCriteria = new DependenciaDeServicioCriteria();

        setAllFilters(dependenciaDeServicioCriteria);

        assertThat(dependenciaDeServicioCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void dependenciaDeServicioCriteriaCopyCreatesNullFilterTest() {
        var dependenciaDeServicioCriteria = new DependenciaDeServicioCriteria();
        var copy = dependenciaDeServicioCriteria.copy();

        assertThat(dependenciaDeServicioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(copyFiltersAre(copy, (a, b) -> a == null || a instanceof Boolean ? a == b : a != b && a.equals(b))),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(dependenciaDeServicioCriteria)
        );
    }

    @Test
    void dependenciaDeServicioCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var dependenciaDeServicioCriteria = new DependenciaDeServicioCriteria();
        setAllFilters(dependenciaDeServicioCriteria);

        var copy = dependenciaDeServicioCriteria.copy();

        assertThat(dependenciaDeServicioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(copyFiltersAre(copy, (a, b) -> a == null || a instanceof Boolean ? a == b : a != b && a.equals(b))),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(dependenciaDeServicioCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var dependenciaDeServicioCriteria = new DependenciaDeServicioCriteria();

        assertThat(dependenciaDeServicioCriteria).hasToString("DependenciaDeServicioCriteria{}");
    }

    private static void setAllFilters(DependenciaDeServicioCriteria dependenciaDeServicioCriteria) {
        dependenciaDeServicioCriteria.id();
        dependenciaDeServicioCriteria.peso();
        dependenciaDeServicioCriteria.descripcion();
        dependenciaDeServicioCriteria.dependienteId();
        dependenciaDeServicioCriteria.origenId();
        dependenciaDeServicioCriteria.impactoId();
        dependenciaDeServicioCriteria.distinct();
    }

    private static Condition<DependenciaDeServicioCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPeso()) &&
                condition.apply(criteria.getDescripcion()) &&
                condition.apply(criteria.getDependienteId()) &&
                condition.apply(criteria.getOrigenId()) &&
                condition.apply(criteria.getImpactoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DependenciaDeServicioCriteria> copyFiltersAre(
        DependenciaDeServicioCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPeso(), copy.getPeso()) &&
                condition.apply(criteria.getDescripcion(), copy.getDescripcion()) &&
                condition.apply(criteria.getDependienteId(), copy.getDependienteId()) &&
                condition.apply(criteria.getOrigenId(), copy.getOrigenId()) &&
                condition.apply(criteria.getImpactoId(), copy.getImpactoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
