package dsa.lab.alg.search;

import dsa.lab.model.Record;

public final class StudentSearches {
    private StudentSearches() { }

    public static SearchAlgorithm linearSearch() {
        return new SearchAlgorithm() {
            @Override public String name() { return "LinearSearch"; }

            @Override public int searchByKey(Record[] a, int key) {
                // YOUR CODE HERE
                return -1;
            }
        };
    }

    public static SearchAlgorithm binarySearchAnyMatch() {
        return new SearchAlgorithm() {
            @Override public String name() { return "BinarySearch(any)"; }

            @Override public int searchByKey(Record[] a, int key) {
                // YOUR CODE HERE
                return -1;
            }
        };
    }
}
