package com.home.zubrin.zbodytemp.Model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by zubrin on 4/25/15.
 */
public class Card {

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
