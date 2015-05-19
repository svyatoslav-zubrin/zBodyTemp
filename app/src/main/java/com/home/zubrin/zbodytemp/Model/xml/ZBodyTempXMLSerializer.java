package com.home.zubrin.zbodytemp.Model.xml;

import android.content.Context;
import android.util.Xml;

import com.home.zubrin.zbodytemp.Model.Card;
import com.home.zubrin.zbodytemp.Model.Person;

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

    public ZBodyTempXMLSerializer(Context c, String fileName) {
        mContext = c;
        mFileName = fileName;
    }

    public void saveCard(Card card) throws IOException {

        // see https://xjaphx.wordpress.com/2011/10/27/android-xml-adventure-create-write-xml-data/ for help (section 3)

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);

        serializer.startDocument("UTF-8", true);
        serializer.startTag("", Card.XML_TAG_MAIN);         // <card>
        serializer.startTag("", Person.XML_TAG_MAIN);       //   <person>
        serializer.startTag("", Person.XML_TAG_NAME);       //     <name>
        serializer.attribute("", Person.XML_TAG_ID, String.valueOf(card.)) // TODO: continue from here
    }
}
