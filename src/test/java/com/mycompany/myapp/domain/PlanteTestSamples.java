package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PlanteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Plante getPlanteSample1() {
        return new Plante().id(1L).planteLibelle("planteLibelle1").racine("racine1");
    }

    public static Plante getPlanteSample2() {
        return new Plante().id(2L).planteLibelle("planteLibelle2").racine("racine2");
    }

    public static Plante getPlanteRandomSampleGenerator() {
        return new Plante()
            .id(longCount.incrementAndGet())
            .planteLibelle(UUID.randomUUID().toString())
            .racine(UUID.randomUUID().toString());
    }
}
