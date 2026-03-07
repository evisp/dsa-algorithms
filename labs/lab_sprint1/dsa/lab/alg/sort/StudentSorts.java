package dsa.lab.alg.sort;

import dsa.lab.model.Record;

public final class StudentSorts {
    private StudentSorts() { }

    public static SortAlgorithm insertionSort() {
        return new SortAlgorithm() {
            @Override public String name() { return "InsertionSort"; }

            @Override public void sort(Record[] a) {
                // YOUR CODE HERE
            }
        };
    }

    public static SortAlgorithm mergeSort() {
        return new SortAlgorithm() {
            @Override public String name() { return "MergeSort"; }

            @Override public void sort(Record[] a) {
                // YOUR CODE HERE
            }

            private void sort(Record[] a, Record[] aux, int lo, int hi) {
                // YOUR CODE HERE
            }

            private void merge(Record[] a, Record[] aux, int lo, int mid, int hi) {
                // YOUR CODE HERE
            }
        };
    }

    public static SortAlgorithm quickSort() {
        return new SortAlgorithm() {
            @Override public String name() { return "QuickSort"; }

            @Override public void sort(Record[] a) {
                // YOUR CODE HERE
            }

            // 3-way quicksort: good on lots of duplicates (your DUPLICATES dataset). [web:154][web:150]
            private void quick3(Record[] a, int lo, int hi) {
                // YOUR CODE HERE
            }

            private void swap(Record[] a, int i, int j) {
                Record tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
            }
        };
    }
}
