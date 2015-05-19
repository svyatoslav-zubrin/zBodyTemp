package com.home.zubrin.zbodytemp.Model;

import com.home.zubrin.zbodytemp.Interfaces.ZBodyTempXMLSerializedObject;
import com.home.zubrin.zbodytemp.Model.xml.ZBodyTempXMLSerializer;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zubrin on 4/25/15.
 */
public class Record implements ZBodyTempXMLSerializedObject
{

    public static String XML_TAG_MAIN   = "record";
    public static String XML_ATTR_ID    = "id";
    public static String XML_TAG_NAME   = "name";
    public static String XML_TAG_UNITS  = "units";
    public static String XML_TAG_AMOUNT = "amount";
    public static String XML_ATTR_TYPE  = "type";
    public static String XML_ATTR_DATE  = "date";

    public static Float MinTemp = 34.0f;
    public static Float MaxTemp = 42.0f;

    private UUID mId;
    private Float mValue;
    private Date mDate;
    private Type mType;

    public enum Type {
        TEMPERATURE,
        MEDICINE,
        NOTE;

        public
        String toString() {
            switch (this) {
                case TEMPERATURE: return "temperature";
                case MEDICINE: return "medicine";
                case NOTE: return "note";
            }
        }
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

    // XML Serialization

    @Override
    public void toXML(XmlSerializer serializer) throws IOException {
        serializer.startTag("", XML_TAG_MAIN);
        serializer.attribute("", XML_ATTR_ID, mId.toString());
        serializer.attribute("", XML_ATTR_TYPE, mType.toString());
        serializer.attribute("", XML_ATTR_DATE, mDate.toString()); // TODO: correct formatter needed
        serializer.attribute("", XML_TAG_UNITS, "C");
        serializer.text(mValue.toString());
        serializer.endTag("", XML_TAG_MAIN);
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
