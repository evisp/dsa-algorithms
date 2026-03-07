package dsa.lab.util;

import dsa.lab.model.Record;

public final class Validate {
    private Validate() { }

    public static boolean isSortedByKey(Record[] a) {
        if (a == null) return false;
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1].key() > a[i].key()) return false;
        }
        return true;
    }

    public static boolean isValidSearchResult(Record[] a, int key, int index) {
        if (a == null) return false;
        if (index == -1) {
            // verify key is not present
            for (Record r : a) if (r.key() == key) return false;
            return true;
        }
        return index >= 0 && index < a.length && a[index].key() == key;
    }
}
