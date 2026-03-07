package dsa.lab.util;

import java.util.Arrays;

import dsa.lab.model.Record;

public final class Copy {
    private Copy() { }

    public static Record[] copyOf(Record[] a) {
        if (a == null) return null;
        return Arrays.copyOf(a, a.length);
    }
}
