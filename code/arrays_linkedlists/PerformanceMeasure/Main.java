// File: Main.java
import java.util.Random;

public class Main {
    private static final int[] SIZES = {1_000, 10_000, 100_000, 500_000, 1_000_000};

    public static void main(String[] args) {
        Random rng = new Random(42);

        System.out.println("1) ACCESS (read by index)  — arrays O(1), ArrayList middle read O(1), LinkedList middle read O(n) [theory]");
        AccessBenchmark.run(SIZES, rng);

        System.out.println();
        System.out.println("2) INSERT/DELETE at position (middle) — shifting/copying makes it O(n) for arrays/ArrayList; traversal makes it O(n) for LinkedList [theory]");
        InsertDeleteBenchmark.run(SIZES);

        System.out.println();
        System.out.println("3) SEARCH (contains / linear scan) — O(n) for all three [theory]");
        SearchBenchmark.run(SIZES, rng);
    }
}
