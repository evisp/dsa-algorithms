// File: AccessBenchmark.java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public final class AccessBenchmark {
    private AccessBenchmark() {}

    private static final int WARMUP = 3;
    private static final int TRIALS = 10;

    public static void run(int[] sizes, Random rng) {
        System.out.printf("%10s  %8s  %14s  %14s  %14s%n",
                "n", "reads", "int[](ns)", "ArrayList(ns)", "LinkedList(ns)");

        for (int n : sizes) {
            int[] arr = DataFactory.buildIntArray(n);
            ArrayList<Integer> arrayList = DataFactory.buildArrayListFromArray(arr);
            LinkedList<Integer> linkedList = DataFactory.buildLinkedListFromArray(arr);

            int reads = readsPerTrial(n);
            int[] indices = DataFactory.randomIndices(reads, n, rng);

            long arrNs = BenchmarkTimer.averageNsPerOperation(WARMUP, TRIALS, reads, () -> {
                long sum = 0;
                for (int idx : indices) sum += arr[idx];
                BenchmarkTimer.blackhole(sum);
            });

            long alNs = BenchmarkTimer.averageNsPerOperation(WARMUP, TRIALS, reads, () -> {
                long sum = 0;
                for (int idx : indices) sum += arrayList.get(idx);
                BenchmarkTimer.blackhole(sum);
            });

            long llNs = BenchmarkTimer.averageNsPerOperation(WARMUP, TRIALS, reads, () -> {
                long sum = 0;
                for (int idx : indices) sum += linkedList.get(idx);
                BenchmarkTimer.blackhole(sum);
            });

            System.out.printf("%10d  %8d  %14d  %14d  %14d%n", n, reads, arrNs, alNs, llNs);
        }
    }

    private static int readsPerTrial(int n) {
        // Keep runtime reasonable; LinkedList.get(i) is expensive for large n.
        if (n <= 10_000) return 50_000;
        if (n <= 100_000) return 10_000;
        return 2_000;
    }
}
