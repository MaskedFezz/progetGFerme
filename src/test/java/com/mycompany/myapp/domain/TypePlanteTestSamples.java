package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TypePlanteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TypePlante getTypePlanteSample1() {
        return new TypePlante().id(1L).nom("nom1").humiditeMax(1L).humiditeMin(1L).temperature(1L);
    }

    public static TypePlante getTypePlanteSample2() {
        return new TypePlante().id(2L).nom("nom2").humiditeMax(2L).humiditeMin(2L).temperature(2L);
    }

    public static TypePlante getTypePlanteRandomSampleGenerator() {
        return new TypePlante()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .humiditeMax(longCount.incrementAndGet())
            .humiditeMin(longCount.incrementAndGet())
            .temperature(longCount.incrementAndGet());
    }
}
