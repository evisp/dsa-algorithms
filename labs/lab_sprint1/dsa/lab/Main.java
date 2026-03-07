package dsa.lab;

import java.util.List;

import dsa.lab.alg.search.SearchAlgorithm;
import dsa.lab.alg.search.StudentSearches;
import dsa.lab.alg.sort.SortAlgorithm;
import dsa.lab.alg.sort.StudentSorts;
import dsa.lab.bench.BenchRunner;
import dsa.lab.bench.CsvWriter;
import dsa.lab.bench.ResultRow;
import dsa.lab.bench.Summarize;
import dsa.lab.bench.SummaryRow;
import dsa.lab.data.DatasetType;

public final class Main {

    public static void main(String[] args) throws Exception {
        int n = 100000;
        long seed = 12345L;
        int warmupRuns = 1;
        int measuredRuns = 5;

        DatasetType[] datasets = {
                DatasetType.RANDOM
        };

        SortAlgorithm[] sorts = {
                StudentSorts.insertionSort(),
                StudentSorts.mergeSort(),
                StudentSorts.quickSort()
        };

        SearchAlgorithm[] searches = {
                StudentSearches.linearSearch(),
                StudentSearches.binarySearchAnyMatch()
        };

        BenchRunner runner = new BenchRunner();
        List<ResultRow> rows = runner.runAll(
                n, seed, warmupRuns, measuredRuns,
                datasets, sorts, searches
        );

        List<SummaryRow> summary = Summarize.overall(rows);
        CsvWriter.writeOverallSummary("results_summary.csv", summary);

        System.out.println("Wrote results_summary.csv (" + summary.size() + " rows)");
    }
}
