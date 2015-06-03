package com.home.zubrin.zbodytemp.Model.xml;

import com.home.zubrin.zbodytemp.Model.Card;
import com.home.zubrin.zbodytemp.Model.Person;
import com.home.zubrin.zbodytemp.Model.Persons;
import com.home.zubrin.zbodytemp.Model.Record;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

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
    Persons parse(String dataString) {
        XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(dataString));

            int eventType = xpp.next();
            if (eventType == XmlPullParser.START_TAG) {
                // TODO: place all parsing logic inside this function (and not in model classes)
            }


        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }


        return null;
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
