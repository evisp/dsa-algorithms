package dsa.lab.bench;

import dsa.lab.data.DatasetType;

public record ResultRow(
        DatasetType dataset,
        int n,
        String category,   // "sort" or "search"
        String algorithm,
        int runIndex,
        long nanos,
        boolean ok
) { }
