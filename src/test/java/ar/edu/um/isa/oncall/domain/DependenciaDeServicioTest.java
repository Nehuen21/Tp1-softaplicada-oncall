package ar.edu.um.isa.oncall.domain;

import static ar.edu.um.isa.oncall.domain.DependenciaDeServicioTestSamples.*;
import static ar.edu.um.isa.oncall.domain.ImpactoPropagadoTestSamples.*;
import static ar.edu.um.isa.oncall.domain.ServicioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.isa.oncall.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DependenciaDeServicioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DependenciaDeServicio.class);
        DependenciaDeServicio dependenciaDeServicio1 = getDependenciaDeServicioSample1();
        DependenciaDeServicio dependenciaDeServicio2 = new DependenciaDeServicio();
        assertThat(dependenciaDeServicio1).isNotEqualTo(dependenciaDeServicio2);

        dependenciaDeServicio2.setId(dependenciaDeServicio1.getId());
        assertThat(dependenciaDeServicio1).isEqualTo(dependenciaDeServicio2);

        dependenciaDeServicio2 = getDependenciaDeServicioSample2();
        assertThat(dependenciaDeServicio1).isNotEqualTo(dependenciaDeServicio2);
    }

    @Test
    void dependienteTest() {
        DependenciaDeServicio dependenciaDeServicio = getDependenciaDeServicioRandomSampleGenerator();
        Servicio servicioBack = getServicioRandomSampleGenerator();

        dependenciaDeServicio.setDependiente(servicioBack);
        assertThat(dependenciaDeServicio.getDependiente()).isEqualTo(servicioBack);

        dependenciaDeServicio.dependiente(null);
        assertThat(dependenciaDeServicio.getDependiente()).isNull();
    }

    @Test
    void origenTest() {
        DependenciaDeServicio dependenciaDeServicio = getDependenciaDeServicioRandomSampleGenerator();
        Servicio servicioBack = getServicioRandomSampleGenerator();

        dependenciaDeServicio.setOrigen(servicioBack);
        assertThat(dependenciaDeServicio.getOrigen()).isEqualTo(servicioBack);

        dependenciaDeServicio.origen(null);
        assertThat(dependenciaDeServicio.getOrigen()).isNull();
    }

    @Test
    void impactoTest() {
        DependenciaDeServicio dependenciaDeServicio = getDependenciaDeServicioRandomSampleGenerator();
        ImpactoPropagado impactoPropagadoBack = getImpactoPropagadoRandomSampleGenerator();

        dependenciaDeServicio.addImpacto(impactoPropagadoBack);
        assertThat(dependenciaDeServicio.getImpactos()).containsOnly(impactoPropagadoBack);
        assertThat(impactoPropagadoBack.getDependencia()).isEqualTo(dependenciaDeServicio);

        dependenciaDeServicio.removeImpacto(impactoPropagadoBack);
        assertThat(dependenciaDeServicio.getImpactos()).doesNotContain(impactoPropagadoBack);
        assertThat(impactoPropagadoBack.getDependencia()).isNull();

        dependenciaDeServicio.impactos(new HashSet<>(Set.of(impactoPropagadoBack)));
        assertThat(dependenciaDeServicio.getImpactos()).containsOnly(impactoPropagadoBack);
        assertThat(impactoPropagadoBack.getDependencia()).isEqualTo(dependenciaDeServicio);

        dependenciaDeServicio.setImpactos(new HashSet<>());
        assertThat(dependenciaDeServicio.getImpactos()).doesNotContain(impactoPropagadoBack);
        assertThat(impactoPropagadoBack.getDependencia()).isNull();
    }
}
