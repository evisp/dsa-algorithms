// File: DataFactory.java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class DataFactory {
    private DataFactory() {}

    public static int[] buildIntArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i; // unique, predictable
        return a;
    }

    public static ArrayList<Integer> buildArrayListFromArray(int[] a) {
        ArrayList<Integer> list = new ArrayList<>(a.length);
        for (int x : a) list.add(x);
        return list;
    }

    public static LinkedList<Integer> buildLinkedListFromArray(int[] a) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int x : a) list.add(x);
        return list;
    }

    public static int[] randomIndices(int count, int n, Random rng) {
        int[] idx = new int[count];
        for (int i = 0; i < count; i++) idx[i] = rng.nextInt(n);
        return idx;
    }

    public static int presentTarget(int n, Random rng) {
        return rng.nextInt(n); // always present because array/list contains 0..n-1
    }

    public static int missingTarget() {
        return -1; // never present in 0..n-1
    }
}
