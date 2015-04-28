package com.home.zubrin.zbodytemp.Model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zubrin on 4/25/15.
 */
public class Record
{
    public static Float MinTemp = 34.0f;
    public static Float MaxTemp = 42.0f;

    private UUID mId;
    private Float mValue;
    private Date mDate;
    private Type mType;

    public enum Type {
        TEMPERATURE,
        MEDICINE,
        NOTE
    }

    // Constructors

    public Record() {
        mId = UUID.randomUUID();
        mDate = new Date();
        mValue = 36.6f; // temperature, C
        mType = Type.TEMPERATURE;
    }

    public Record(Float value) {
        mId = UUID.randomUUID();
        mDate = new Date();
        mType = Type.TEMPERATURE;

        mValue = value;
    }

    public Record(Float value, Date date) {
        mId = UUID.randomUUID();
        mType = Type.TEMPERATURE;

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
