package com.home.zubrin.zbodytemp.Model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zubrin on 4/25/15.
 */
public class Record
{
    private UUID mId;
    private Float mValue;
    private Date mDate;

    // Constructors

    public Record() {
        mId = UUID.randomUUID();
        mDate = new Date();
        mValue = 36.6f; // temperature, C
    }

    public Record(Float value) {
        mId = UUID.randomUUID();
        mDate = new Date();

        mValue = value;
    }

    public Record(Float value, Date date) {
        mId = UUID.randomUUID();

        mValue = value;
        mDate = date;
    }

    // Public setters and getters

    public UUID getId() {
        return mId;
    }

    public Float getValue() {
        return mValue;
    }

    public void setValue(Float value) {
        mValue = value;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
