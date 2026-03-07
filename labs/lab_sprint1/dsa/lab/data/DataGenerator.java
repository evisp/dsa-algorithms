package dsa.lab.data;

import java.util.SplittableRandom;

import dsa.lab.model.Record;

public final class DataGenerator {

    private DataGenerator() { }

    // Tunables 
    private static final int RANDOM_KEY_RANGE_MULTIPLIER = 10; // random keys in [0, n*10)

    public static Record[] generate(int n, DatasetType type, long seed) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        if (type == null) throw new IllegalArgumentException("type must not be null");

        SplittableRandom rnd = new SplittableRandom(seed); // reproducible per seed [web:43]

        return switch (type) {
            case RANDOM        -> random(n, rnd);
        };
    }

    private static Record[] random(int n, SplittableRandom rnd) {
        int keyRange = Math.max(1, n * RANDOM_KEY_RANGE_MULTIPLIER);
        Record[] a = new Record[n];
        for (int i = 0; i < n; i++) {
            int key = rnd.nextInt(keyRange);
            int payload = rnd.nextInt(); // arbitrary
            a[i] = new Record(key, payload);
        }
        return a;
    }
}
