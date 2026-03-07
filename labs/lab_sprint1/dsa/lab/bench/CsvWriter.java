package dsa.lab.bench;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public final class CsvWriter {
    private CsvWriter() { }

    public static void writeRaw(String filePath, List<ResultRow> rows) throws IOException {
        try (FileWriter out = new FileWriter(filePath)) {
            out.write("dataset,n,category,algorithm,runIndex,nanos,ok\n");
            for (ResultRow r : rows) {
                out.write(r.dataset() + "," + r.n() + "," + r.category() + "," + r.algorithm()
                        + "," + r.runIndex() + "," + r.nanos() + "," + r.ok() + "\n");
            }
        }
    }

    public static void writeOverallSummary(String filePath, List<SummaryRow> rows) throws IOException {
        try (FileWriter out = new FileWriter(filePath)) {
            out.write("n,category,algorithm,datasets,runs,avg_ms,final_avg_ms,min_ms,max_ms,ok_rate\n");
            for (SummaryRow r : rows) {
                double avgMs = r.avgNanos() / 1_000_000.0;
                double finalAvgMs = r.finalAvgNanos() / 1_000_000.0;
                double minMs = r.minNanos() / 1_000_000.0;
                double maxMs = r.maxNanos() / 1_000_000.0;
                double okRate = (r.runs() == 0) ? 0.0 : (r.okCount() * 1.0 / r.runs());

                out.write(r.n() + "," + r.category() + "," + r.algorithm() + ","
                        + r.datasets() + "," + r.runs() + ","
                        + avgMs + "," + finalAvgMs + "," + minMs + "," + maxMs + ","
                        + okRate + "\n");
            }
        }
    }
}
