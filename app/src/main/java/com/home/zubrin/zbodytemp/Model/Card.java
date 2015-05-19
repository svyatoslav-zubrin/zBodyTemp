package com.home.zubrin.zbodytemp.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by zubrin on 4/25/15.
 */
public class Card {

    public static String XML_TAG_MAIN    = "card";
    public static String XML_TAG_ID      = "id";
    public static String XML_TAG_DISEASE = "disease";
    public static String XML_TAG_ARCHIVE = "archive";

    private UUID mId;
    private ArrayList<Record> mRecords;

    // Constructors

    public Card() {
        mId = UUID.randomUUID();
        mRecords = new ArrayList<Record>();
    }

    // Public setters and getters

    public
    UUID getId() {
        return mId;
    }

    public
    ArrayList<Record> getRecords() {
        return mRecords;
    }

    // Public methods

    public
    void addRecord(Record record) {
        if (record != null) {
            mRecords.add(record);
            Collections.sort(mRecords, new RecordDateComparator());
        }
    }

    public
    Record findRecordById(UUID id) {
        for (Record r: mRecords) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }
}
