package com.home.zubrin.zbodytemp.Model;

import com.home.zubrin.zbodytemp.Interfaces.ZBodyTempXMLSerializedObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOError;
import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;

/**
 * Created by zubrin on 4/25/15.
 */
public
class Person implements ZBodyTempXMLSerializedObject {

    public static String XML_TAG_MAIN       = "person";
    public static String XML_ATTR_ID        = "id";
    public static String XML_ATTR_NAME      = "name";
    public static String XML_ATTR_SURNAME   = "surname";
    public static String XML_ATTR_BIRTHDATE = "birthdate";

    private UUID mId;
    private String mName;
    private Integer mAge;
    private Card mCard;

    // Constructors

    public
    Person(String name, Integer age) {
        mId = UUID.randomUUID();
        mCard = new Card();

        mName = name;
        mAge = age;
    }

    @Override
    public
    String toString() {
        return mName;
    }

    // XML Serialization

    @Override
    public
    void toXML(XmlSerializer serializer) throws IOException {

        serializer.startTag("", XML_TAG_MAIN);
        serializer.attribute("", XML_ATTR_ID, mId.toString());
        serializer.attribute("", XML_ATTR_NAME, mName);
        mCard.toXML(serializer);
        serializer.endTag("", XML_TAG_MAIN);
    }

    // XML Deserialization

    public
    Object fromXML(String xml, XmlPullParser parser) throws XmlPullParserException, IOException {
        return null;
    }

    // Public setters and getters

    public
    UUID getId() {
        return mId;
    }

    public
    void setId(UUID mId) {
        this.mId = mId;
    }

    public
    String getName() {
        return mName;
    }

    public
    void setName(String name) {
        mName = name;
    }

    public
    Integer getAge() {
        return mAge;
    }

    public
    void setAge(Integer age) {
        mAge = age;
    }

    public
    Card getCard() {
        return mCard;
    }

    public void setCard(Card mCard) {
        this.mCard = mCard;
    }
}
