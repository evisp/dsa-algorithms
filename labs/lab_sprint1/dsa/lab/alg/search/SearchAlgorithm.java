package dsa.lab.alg.search;

import dsa.lab.model.Record;

public interface SearchAlgorithm {
    String name();
    int searchByKey(Record[] a, int key); // return index or -1
}
