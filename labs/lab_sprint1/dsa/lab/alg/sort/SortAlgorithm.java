package dsa.lab.alg.sort;

import dsa.lab.model.Record;

public interface SortAlgorithm {
    String name();
    void sort(Record[] a); // sort by key ascending
}
