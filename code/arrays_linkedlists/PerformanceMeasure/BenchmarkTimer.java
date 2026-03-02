// File: BenchmarkTimer.java
public final class BenchmarkTimer {
    private BenchmarkTimer() {}

    private static volatile long sink = 0;

    public static long averageNsPerOperation(int warmupRuns, int trials, int operationsPerTrial, Runnable work) {
        for (int i = 0; i < warmupRuns; i++) work.run();

        long total = 0;
        for (int t = 0; t < trials; t++) {
            long start = System.nanoTime();
            work.run();
            long end = System.nanoTime();
            total += (end - start);
        }

        long avgPerTrial = total / trials;
        return avgPerTrial / operationsPerTrial;
    }

    public static void blackhole(long x) {
        sink += x;
    }
}
