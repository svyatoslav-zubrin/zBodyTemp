package com.home.zubrin.zbodytemp.Interfaces;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by zubrin on 5/19/15.
 */
public interface ZBodyTempXMLSerializedObject {

    void toXML(XmlSerializer serializer) throws IOException; // TODO: move de/serialization logic from data model to separate objects
}
