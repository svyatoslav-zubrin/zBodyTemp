package com.home.zubrin.zbodytemp.Model.xml;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.home.zubrin.zbodytemp.Model.Card;
import com.home.zubrin.zbodytemp.Model.Person;
import com.home.zubrin.zbodytemp.Model.Persons;

import org.w3c.dom.Element;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.annotation.Documented;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by SlavaZu on 5/19/15.
 */
public class ZBodyTempXMLSerializer {

    private Context mContext;
    private String mFileName;

    public
    ZBodyTempXMLSerializer(Context c, String fileName) {
        mContext = c;
        mFileName = fileName;
    }

    public
    void serializePersons(Persons persons) throws IOException {

        // see https://xjaphx.wordpress.com/2011/10/27/android-xml-adventure-create-write-xml-data/ for help (section 3)

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);

        serializer.startDocument("UTF-8", true);
        persons.toXML(serializer);
        serializer.endDocument();

        Log.d("XML", writer.toString());
    }

    public static
    void serializeCard(Card card) throws IOException {

        // see https://xjaphx.wordpress.com/2011/10/27/android-xml-adventure-create-write-xml-data/ for help (section 3)

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);

        serializer.startDocument("UTF-8", true);
        card.toXML(serializer);
        serializer.endDocument();

        Log.d("XML", writer.toString());
    }

    public static
    void serializePerson(Person person) {

        // see https://xjaphx.wordpress.com/2011/10/27/android-xml-adventure-create-write-xml-data/ for help (section 3)

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            serializer.setOutput(writer);

            serializer.startDocument("UTF-8", true);
            person.toXML(serializer);
            serializer.endDocument();

            Log.i("XML", writer.toString());
        }
        catch (IOException exception) {
            Log.i("XML", exception.getLocalizedMessage());
        }
    }
}
