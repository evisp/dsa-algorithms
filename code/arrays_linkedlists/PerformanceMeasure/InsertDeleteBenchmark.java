// File: InsertDeleteBenchmark.java
import java.util.ArrayList;
import java.util.LinkedList;

public final class InsertDeleteBenchmark {
    private InsertDeleteBenchmark() {}

    private static final int WARMUP = 3;
    private static final int TRIALS = 10;

    public static void run(int[] sizes) {
        System.out.printf("%10s  %8s  %14s  %14s  %14s%n",
                "n", "rounds", "int[](ns)", "ArrayList(ns)", "LinkedList(ns)");

        for (int n : sizes) {
            int[] baseArr = DataFactory.buildIntArray(n);
            ArrayList<Integer> baseAL = DataFactory.buildArrayListFromArray(baseArr);
            LinkedList<Integer> baseLL = DataFactory.buildLinkedListFromArray(baseArr);

            int index = n / 2;
            int value = 123456;

            int rounds = roundsPerTrial(n);

            long arrNs = BenchmarkTimer.averageNsPerOperation(WARMUP, TRIALS, rounds, () -> {
                int[] a = baseArr;
                for (int r = 0; r < rounds; r++) {
                    a = insertAt(a, index, value);
                    a = deleteAt(a, index);
                }
                BenchmarkTimer.blackhole(a.length);
            });

            long alNs = BenchmarkTimer.averageNsPerOperation(WARMUP, TRIALS, rounds, () -> {
                // Copy once per trial, then do add/remove to keep size stable.
                ArrayList<Integer> list = new ArrayList<>(baseAL);
                for (int r = 0; r < rounds; r++) {
                    list.add(index, value);
                    list.remove(index);
                }
                BenchmarkTimer.blackhole(list.size());
            });

            long llNs = BenchmarkTimer.averageNsPerOperation(WARMUP, TRIALS, rounds, () -> {
                LinkedList<Integer> list = new LinkedList<>(baseLL);
                for (int r = 0; r < rounds; r++) {
                    list.add(index, value);
                    list.remove(index);
                }
                BenchmarkTimer.blackhole(list.size());
            });

            System.out.printf("%10d  %8d  %14d  %14d  %14d%n", n, rounds, arrNs, alNs, llNs);
        }
    }

    // Arrays are fixed-size; inserting/deleting means creating a new array and copying.
    private static int[] insertAt(int[] a, int index, int value) {
        int[] b = new int[a.length + 1];
        System.arraycopy(a, 0, b, 0, index);
        b[index] = value;
        System.arraycopy(a, index, b, index + 1, a.length - index);
        return b;
    }

    private static int[] deleteAt(int[] a, int index) {
        int[] b = new int[a.length - 1];
        System.arraycopy(a, 0, b, 0, index);
        System.arraycopy(a, index + 1, b, index, a.length - index - 1);
        return b;
    }

    private static int roundsPerTrial(int n) {
        if (n <= 10_000) return 200;
        if (n <= 100_000) return 50;
        return 10;
    }
}
