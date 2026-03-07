package dsa.lab.bench;

public record SummaryRow(
        int n,
        String category,    // "sort" or "search"
        String algorithm,
        int datasets,       // how many dataset types contributed
        int runs,           // total measured runs across all datasets
        long avgNanos,      // average of all measured runs
        long finalAvgNanos, // average of "final run per dataset"
        long minNanos,
        long maxNanos,
        int okCount
) { }
