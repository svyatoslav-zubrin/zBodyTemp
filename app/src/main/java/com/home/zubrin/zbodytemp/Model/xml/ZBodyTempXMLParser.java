package com.home.zubrin.zbodytemp.Model.xml;

import com.home.zubrin.zbodytemp.Model.Card;
import com.home.zubrin.zbodytemp.Model.Person;
import com.home.zubrin.zbodytemp.Model.Persons;
import com.home.zubrin.zbodytemp.Model.Record;
import com.home.zubrin.zbodytemp.Utils.DateUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by slava on 5/30/15.
 */
public class ZBodyTempXMLParser {

    // Defines

    // Public variables

    // Private variables

    // Lifecycle

    // Public methods

    public
    ArrayList<Person> parse(String dataString) {
        ArrayList<Person> persons = null;

        XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(dataString));

            Person currentPerson = null;
            Card currentCard = null;
            while (xpp.next() != XmlPullParser.END_TAG) {
                int eventType = xpp.getEventType();
                if (eventType == XmlPullParser.START_TAG) {
                    String name = xpp.getName();
                    if (name.equals(Persons.XML_TAG_MAIN)) {
                        persons = new ArrayList<>();
                    } else if (name.equals(Person.XML_TAG_MAIN)) {
                        String personName = xpp.getAttributeValue(null, Person.XML_ATTR_NAME);
                        String personId = xpp.getAttributeValue(null, Person.XML_ATTR_ID);
                        int personAge = 0;
                        currentPerson = new Person(personName, personAge);
                        currentPerson.setId(UUID.fromString(personId));
                    } else if (name.equals(Card.XML_TAG_MAIN)) {
                        String cardId = xpp.getAttributeValue(null, Card.XML_ATTR_ID);
                        currentCard = new Card();
                        currentCard.setId(UUID.fromString(cardId));
                    } else if (name.equals(Record.XML_TAG_MAIN)) {
                        UUID recordId = UUID.fromString(xpp.getAttributeValue(null, Record.XML_ATTR_ID));
                        Record.Type recordType = Record.Type.fromString(xpp.getAttributeValue(null, Record.XML_ATTR_TYPE));
                        Date recordDate = DateUtils.xml2date(xpp.getAttributeValue(null, Record.XML_ATTR_DATE));
                        Float recordValue = Float.parseFloat(xpp.getText());
                        Record record = new Record();
                        record.setId(recordId);
                        record.setDate(recordDate);
                        record.setValue(recordValue);
                        if (currentCard != null) { currentCard.addRecord(record); }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String name = xpp.getName();
                    if (name.equals(Persons.XML_TAG_MAIN)) {
                        if (persons != null && persons.size() > 0) {
                            Persons.sharedInstance.clear();
                            for (Person p: persons) {
                                Persons.sharedInstance.addPerson(p);
                            }
                        }
                    } else if (name.equals(Person.XML_TAG_MAIN)) {
                        if (persons != null && currentPerson != null) { persons.add(currentPerson); }
                        currentPerson = null;
                    } else if (name.equals(Card.XML_TAG_MAIN)) {
                        if (currentCard != null && currentPerson != null) { currentPerson.setCard(currentCard); }
                        currentCard = null;
                    } else if (name.equals(Record.XML_TAG_MAIN)) {
                        // do nothing
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return persons;
    }

    // Private methods

    private
    Person processPerson() {
        return null;
    }

    private
    Card processCard() {
        return null;
    }

    private
    Record processRecord() {
        return null;
    }
}
