package com.home.zubrin.zbodytemp.Model;

import com.home.zubrin.zbodytemp.Interfaces.ZBodyTempXMLSerializedObject;
import com.home.zubrin.zbodytemp.Utils.DateUtils;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.Date;
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
    private String mSurname;
    private Date mBirthday;
    private Card mCard;

    // Constructors

    public
    Person(String name, Date birthday) {
        mId = UUID.randomUUID();
        mCard = new Card();

        mName = name;
        mSurname = "";
        mBirthday = birthday;
    }

    public
    Person(String name, String surname, Date birthday) {
        mId = UUID.randomUUID();
        mCard = new Card();

        mName = name;
        mSurname = surname;
        mBirthday = birthday;
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
        serializer.attribute("", XML_ATTR_SURNAME, mSurname);
        serializer.attribute("", XML_ATTR_BIRTHDATE, DateUtils.date2xml(mBirthday));
        mCard.toXML(serializer);
        serializer.endTag("", XML_TAG_MAIN);
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

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        mSurname = surname;
    }

    public Date getBirthday() {
        return mBirthday;
    }

    public void setBirthday(Date birthday) {
        mBirthday = birthday;
    }

    public
    Card getCard() {
        return mCard;
    }

    public
    void setCard(Card mCard) {
        this.mCard = mCard;
    }
}
