// File: SearchBenchmark.java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public final class SearchBenchmark {
    private SearchBenchmark() {}

    private static final int WARMUP = 3;
    private static final int TRIALS = 10;

    public static void run(int[] sizes, Random rng) {
        System.out.printf("%10s  %-7s  %8s  %14s  %14s  %14s%n",
                "n", "case", "checks", "int[](ns)", "ArrayList(ns)", "LinkedList(ns)");

        for (int n : sizes) {
            int[] arr = DataFactory.buildIntArray(n);
            ArrayList<Integer> arrayList = DataFactory.buildArrayListFromArray(arr);
            LinkedList<Integer> linkedList = DataFactory.buildLinkedListFromArray(arr);

            int present = DataFactory.presentTarget(n, rng);
            int missing = DataFactory.missingTarget();

            int checks = checksPerTrial(n);

            runCase(n, "present", checks, arr, arrayList, linkedList, present);
            runCase(n, "missing", checks, arr, arrayList, linkedList, missing);
        }
    }

    private static void runCase(int n, String label, int checks,
                                int[] arr, ArrayList<Integer> arrayList, LinkedList<Integer> linkedList,
                                int target) {

        long arrNs = BenchmarkTimer.averageNsPerOperation(WARMUP, TRIALS, checks, () -> {
            long hits = 0;
            for (int i = 0; i < checks; i++) if (arrayContains(arr, target)) hits++;
            BenchmarkTimer.blackhole(hits);
        });

        long alNs = BenchmarkTimer.averageNsPerOperation(WARMUP, TRIALS, checks, () -> {
            long hits = 0;
            for (int i = 0; i < checks; i++) if (arrayList.contains(target)) hits++;
            BenchmarkTimer.blackhole(hits);
        });

        long llNs = BenchmarkTimer.averageNsPerOperation(WARMUP, TRIALS, checks, () -> {
            long hits = 0;
            for (int i = 0; i < checks; i++) if (linkedList.contains(target)) hits++;
            BenchmarkTimer.blackhole(hits);
        });

        System.out.printf("%10d  %-7s  %8d  %14d  %14d  %14d%n", n, label, checks, arrNs, alNs, llNs);
    }

    private static boolean arrayContains(int[] a, int target) {
        for (int x : a) if (x == target) return true;
        return false;
    }

    private static int checksPerTrial(int n) {
        return Math.max(1, 100_000 / n);
    }
}
