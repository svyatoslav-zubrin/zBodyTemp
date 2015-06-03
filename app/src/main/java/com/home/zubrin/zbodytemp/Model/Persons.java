package com.home.zubrin.zbodytemp.Model;

import com.home.zubrin.zbodytemp.Interfaces.ZBodyTempXMLSerializedObject;
import com.home.zubrin.zbodytemp.Storage.XMLStorage;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by zubrin on 4/25/15.
 */
public class Persons implements ZBodyTempXMLSerializedObject {

    public static String XML_TAG_MAIN = "persons";

    public static final Persons sharedInstance = new Persons();
    // TODO: correct singleton implementation (prevent creation of multiple instances from code)...

    private ArrayList<Person> mPersons;

    public ArrayList<Person> getPersons() {
        return mPersons;
    }

    public void addPerson(Person person) {
        mPersons.add(person);
    }

    public void removePerson(Person person) {
        mPersons.remove(person);
    }

    public Person findPersonById(UUID id) {
        for (Person p: mPersons) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // XML Serialization

    @Override
    public
    void toXML(XmlSerializer serializer) throws IOException {
        serializer.startTag("", XML_TAG_MAIN);
        for (Person p: mPersons) {
            p.toXML(serializer);
        }
        serializer.endTag("", XML_TAG_MAIN);
    }

    // XML Deserialization

    public
    Object fromXML(String xml, XmlPullParser parser) throws XmlPullParserException, IOException {
        return null;
    }

    // DEBUG: custom constructor

    private Persons() {
        mPersons = new ArrayList<Person>();

        Person adam = new Person("Adam", 33);
        Person eve  = new Person("Eve", 22);

        mPersons.add(adam);
        mPersons.add(eve);
    }
}
