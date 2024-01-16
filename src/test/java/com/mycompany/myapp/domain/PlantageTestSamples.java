package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PlantageTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Plantage getPlantageSample1() {
        return new Plantage().id(1L).nombre(1L);
    }

    public static Plantage getPlantageSample2() {
        return new Plantage().id(2L).nombre(2L);
    }

    public static Plantage getPlantageRandomSampleGenerator() {
        return new Plantage().id(longCount.incrementAndGet()).nombre(longCount.incrementAndGet());
    }
}
