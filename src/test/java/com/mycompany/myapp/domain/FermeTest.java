package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.FermeTestSamples.*;
import static com.mycompany.myapp.domain.ParcelleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FermeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ferme.class);
        Ferme ferme1 = getFermeSample1();
        Ferme ferme2 = new Ferme();
        assertThat(ferme1).isNotEqualTo(ferme2);

        ferme2.setId(ferme1.getId());
        assertThat(ferme1).isEqualTo(ferme2);

        ferme2 = getFermeSample2();
        assertThat(ferme1).isNotEqualTo(ferme2);
    }

    @Test
    void parcelleTest() throws Exception {
        Ferme ferme = getFermeRandomSampleGenerator();
        Parcelle parcelleBack = getParcelleRandomSampleGenerator();

        ferme.addParcelle(parcelleBack);
        assertThat(ferme.getParcelles()).containsOnly(parcelleBack);
        assertThat(parcelleBack.getFermeLibelle()).isEqualTo(ferme);

        ferme.removeParcelle(parcelleBack);
        assertThat(ferme.getParcelles()).doesNotContain(parcelleBack);
        assertThat(parcelleBack.getFermeLibelle()).isNull();

        ferme.parcelles(new HashSet<>(Set.of(parcelleBack)));
        assertThat(ferme.getParcelles()).containsOnly(parcelleBack);
        assertThat(parcelleBack.getFermeLibelle()).isEqualTo(ferme);

        ferme.setParcelles(new HashSet<>());
        assertThat(ferme.getParcelles()).doesNotContain(parcelleBack);
        assertThat(parcelleBack.getFermeLibelle()).isNull();
    }
}
