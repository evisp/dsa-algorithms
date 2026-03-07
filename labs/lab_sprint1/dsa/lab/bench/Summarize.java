package dsa.lab.bench;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dsa.lab.data.DatasetType;

public final class Summarize {
    private Summarize() { }

    private record Key(int n, String category, String algorithm) { }

    private static final class LastRun {
        int runIndex = -1;
        long nanos = 0;
    }

    private static final class Acc {
        int runs;
        long sum;
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        int okCount;

        // track the final measured run separately per dataset, then average those finals
        final Map<DatasetType, LastRun> lastPerDataset = new HashMap<>();

        void add(ResultRow r) {
            runs++;
            sum += r.nanos();
            min = Math.min(min, r.nanos());
            max = Math.max(max, r.nanos());
            if (r.ok()) okCount++;

            LastRun lr = lastPerDataset.computeIfAbsent(r.dataset(), __ -> new LastRun());
            if (r.runIndex() > lr.runIndex) {
                lr.runIndex = r.runIndex();
                lr.nanos = r.nanos();
            }
        }

        long avgRounded() {
            return runs == 0 ? 0 : Math.round((double) sum / runs);
        }

        long finalAvgRounded() {
            if (lastPerDataset.isEmpty()) return 0;
            long s = 0;
            for (LastRun lr : lastPerDataset.values()) s += lr.nanos;
            return Math.round((double) s / lastPerDataset.size());
        }
    }

    public static List<SummaryRow> overall(List<ResultRow> rows) {
        Map<Key, Acc> map = new HashMap<>();

        for (ResultRow r : rows) {
            Key k = new Key(r.n(), r.category(), r.algorithm());
            map.computeIfAbsent(k, __ -> new Acc()).add(r);
        }

        List<SummaryRow> out = new ArrayList<>(map.size());
        for (Map.Entry<Key, Acc> e : map.entrySet()) {
            Key k = e.getKey();
            Acc a = e.getValue();

            out.add(new SummaryRow(
                    k.n(),
                    k.category(),
                    k.algorithm(),
                    a.lastPerDataset.size(),
                    a.runs,
                    a.avgRounded(),
                    a.finalAvgRounded(),
                    a.min == Long.MAX_VALUE ? 0 : a.min,
                    a.max == Long.MIN_VALUE ? 0 : a.max,
                    a.okCount
            ));
        }

        out.sort(Comparator
                .comparing(SummaryRow::category)
                .thenComparing(SummaryRow::algorithm));

        return out;
    }
}
