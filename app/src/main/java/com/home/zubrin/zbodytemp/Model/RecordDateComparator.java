package com.home.zubrin.zbodytemp.Model;

import java.util.Comparator;

/**
 * Created by SlavaZu on 5/12/15.
 */
public class RecordDateComparator implements Comparator<Record> {
    public int compare(Record left, Record right) {
        return left.getDate().compareTo(right.getDate());
    }
}
