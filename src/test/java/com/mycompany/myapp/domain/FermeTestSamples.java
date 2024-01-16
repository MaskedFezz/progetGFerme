package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FermeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ferme getFermeSample1() {
        return new Ferme().id(1L).fermeLibelle("fermeLibelle1");
    }

    public static Ferme getFermeSample2() {
        return new Ferme().id(2L).fermeLibelle("fermeLibelle2");
    }

    public static Ferme getFermeRandomSampleGenerator() {
        return new Ferme().id(longCount.incrementAndGet()).fermeLibelle(UUID.randomUUID().toString());
    }
}
