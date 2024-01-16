package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.PlantageTestSamples.*;
import static com.mycompany.myapp.domain.PlanteTestSamples.*;
import static com.mycompany.myapp.domain.TypePlanteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlanteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plante.class);
        Plante plante1 = getPlanteSample1();
        Plante plante2 = new Plante();
        assertThat(plante1).isNotEqualTo(plante2);

        plante2.setId(plante1.getId());
        assertThat(plante1).isEqualTo(plante2);

        plante2 = getPlanteSample2();
        assertThat(plante1).isNotEqualTo(plante2);
    }

    @Test
    void plantageTest() throws Exception {
        Plante plante = getPlanteRandomSampleGenerator();
        Plantage plantageBack = getPlantageRandomSampleGenerator();

        plante.addPlantage(plantageBack);
        assertThat(plante.getPlantages()).containsOnly(plantageBack);
        assertThat(plantageBack.getPlanteLibelle()).isEqualTo(plante);

        plante.removePlantage(plantageBack);
        assertThat(plante.getPlantages()).doesNotContain(plantageBack);
        assertThat(plantageBack.getPlanteLibelle()).isNull();

        plante.plantages(new HashSet<>(Set.of(plantageBack)));
        assertThat(plante.getPlantages()).containsOnly(plantageBack);
        assertThat(plantageBack.getPlanteLibelle()).isEqualTo(plante);

        plante.setPlantages(new HashSet<>());
        assertThat(plante.getPlantages()).doesNotContain(plantageBack);
        assertThat(plantageBack.getPlanteLibelle()).isNull();
    }

    @Test
    void nomTest() throws Exception {
        Plante plante = getPlanteRandomSampleGenerator();
        TypePlante typePlanteBack = getTypePlanteRandomSampleGenerator();

        plante.setNom(typePlanteBack);
        assertThat(plante.getNom()).isEqualTo(typePlanteBack);

        plante.nom(null);
        assertThat(plante.getNom()).isNull();
    }
}
