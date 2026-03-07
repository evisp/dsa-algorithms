package dsa.lab.bench;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import dsa.lab.alg.search.SearchAlgorithm;
import dsa.lab.alg.sort.SortAlgorithm;
import dsa.lab.data.DataGenerator;
import dsa.lab.data.DatasetType;
import dsa.lab.model.Record;
import dsa.lab.util.Copy;
import dsa.lab.util.Validate;

public final class BenchRunner {

    public List<ResultRow> runAll(
            int n,
            long seed,
            int warmupRuns,
            int measuredRuns,
            DatasetType[] datasets,
            SortAlgorithm[] sorts,
            SearchAlgorithm[] searches
    ) {
        List<ResultRow> rows = new ArrayList<>();

        int totalJobs = datasets.length * (sorts.length + searches.length);
        int job = 0;

        for (DatasetType dt : datasets) {
            System.out.printf("%nDataset: %s (n=%d)%n", dt, n);

            Record[] base = DataGenerator.generate(n, dt, seed);

            // ---- SORT benchmarks ----
            for (SortAlgorithm alg : sorts) {
                job++;
                printJob(job, totalJobs, "SORT", alg.name(), dt);

                // warmup (not recorded)
                for (int w = 0; w < warmupRuns; w++) {
                    Record[] a = Copy.copyOf(base);
                    alg.sort(a);
                }

                for (int r = 0; r < measuredRuns; r++) {
                    Record[] a = Copy.copyOf(base);

                    long t0 = System.nanoTime();
                    alg.sort(a);
                    long t1 = System.nanoTime();

                    boolean ok = Validate.isSortedByKey(a);
                    rows.add(new ResultRow(dt, n, "sort", alg.name(), r, (t1 - t0), ok));
                }
            }

            // ---- SEARCH benchmarks ----
            // For binary search, prepare a sorted copy once per dataset.
            Record[] sorted = Copy.copyOf(base);
            Arrays.sort(sorted, Comparator.comparingInt(Record::key));

            // Query keys: mix of hits and misses (simple, deterministic)
            int[] queryKeys = buildQueryKeys(sorted);

            for (SearchAlgorithm alg : searches) {
                job++;
                printJob(job, totalJobs, "SEARCH", alg.name(), dt);

                boolean binary = alg.name().toLowerCase().contains("binary");
                Record[] searchArray = binary ? sorted : base;

                // warmup
                for (int w = 0; w < warmupRuns; w++) {
                    for (int key : queryKeys) alg.searchByKey(searchArray, key);
                }

                for (int r = 0; r < measuredRuns; r++) {
                    long t0 = System.nanoTime();
                    int checksum = 0;
                    for (int key : queryKeys) {
                        checksum += alg.searchByKey(searchArray, key);
                    }
                    long t1 = System.nanoTime();

                    // light correctness check (kept outside timing)
                    boolean ok = Validate.isValidSearchResult(searchArray, queryKeys[0],
                            alg.searchByKey(searchArray, queryKeys[0]));
                    ok = ok && (checksum != Integer.MIN_VALUE);

                    rows.add(new ResultRow(dt, n, "search", alg.name(), r, (t1 - t0), ok));
                }
            }
        }

        // finish the progress line cleanly
        System.out.println();
        System.out.println("Benchmarking complete.");

        return rows;
    }

    private static void printJob(int job, int totalJobs, String phase, String algName, DatasetType dt) {
        // \r returns to start of line so we overwrite the previous status line. [web:183]
        System.out.printf("\rJob %d/%d: %-6s %-22s | %s", job, totalJobs, phase, algName, dt);
        System.out.flush(); // force output now when using print/printf without newline. [web:181]
    }

    private static int[] buildQueryKeys(Record[] sorted) {
        if (sorted.length == 0) return new int[] { 0, 1, 2, 3 };

        int min = sorted[0].key();
        int max = sorted[sorted.length - 1].key();

        // 2 hits + 2 misses (usually)
        return new int[] {
                sorted[sorted.length / 3].key(),
                sorted[(2 * sorted.length) / 3].key(),
                min - 1,
                max + 1
        };
    }
}
