package com.home.zubrin.zbodytemp.Model;

import android.content.Context;

import com.home.zubrin.zbodytemp.Interfaces.ZBodyTempXMLSerializedObject;
import com.home.zubrin.zbodytemp.Model.xml.ZBodyTempXMLParser;
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

    private static Persons sharedInstance;

    static public
    Persons getSharedInstance(Context context) {
        if (sharedInstance != null) return sharedInstance;

        sharedInstance = new Persons();
        String xml = XMLStorage.read(context);
        if (xml != null) {
            ArrayList<Person> persons = ZBodyTempXMLParser.parse(xml);
            if (persons != null && persons.size() > 0) {
                for (Person p : persons) {
                    sharedInstance.addPerson(p);
                }
            }
        }

        return sharedInstance;
    }

    private ArrayList<Person> mPersons;

    public ArrayList<Person> getPersons() {
        return mPersons;
    }

    public
    void addPerson(Person person) {
        mPersons.add(person);
    }

    public
    void removePerson(Person person) {
        mPersons.remove(person);
    }

    public
    void clear() { mPersons.clear(); }

    public
    Person findPersonById(UUID id) {
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

    // DEBUG: custom constructor

    private Persons() {
        mPersons = new ArrayList<Person>();
    }
}
