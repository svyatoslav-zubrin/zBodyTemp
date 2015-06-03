package com.home.zubrin.zbodytemp.Model;

import com.home.zubrin.zbodytemp.Interfaces.ZBodyTempXMLSerializedObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by zubrin on 4/25/15.
 */
public class Card implements ZBodyTempXMLSerializedObject {

    public static String XML_TAG_MAIN    = "card";
    public static String XML_TAG_DISEASE = "disease";
    public static String XML_TAG_ARCHIVE = "archive";
    public static String XML_ATTR_ID     = "id";

    private UUID mId;
    private ArrayList<Record> mRecords;

    // Constructors

    public
    Card() {
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

    // XML Serialization

    @Override
    public
    void toXML(XmlSerializer serializer) throws IOException {

        serializer.startTag("", XML_TAG_MAIN);
        serializer.attribute("", XML_ATTR_ID, mId.toString());
        serializer.startTag("", XML_TAG_DISEASE);
        for (Record r : mRecords) {
            r.toXML(serializer);
        }
        serializer.endTag("", XML_TAG_DISEASE);
        serializer.endTag("", XML_TAG_MAIN);
    }

    // XML Deserialization

    static public
    Object fromXML(String xml, XmlPullParser parser) throws XmlPullParserException, IOException {
        UUID id = UUID.fromString(parser.getAttributeValue(null, XML_ATTR_ID));

        return null;
    }
}
