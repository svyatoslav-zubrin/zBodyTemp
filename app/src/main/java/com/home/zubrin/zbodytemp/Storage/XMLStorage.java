package com.home.zubrin.zbodytemp.Storage;

import com.home.zubrin.zbodytemp.Model.Persons;
import com.home.zubrin.zbodytemp.Model.xml.ZBodyTempXMLSerializer;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zubrin on 5/22/15.
 */
public class XMLStorage {

    static private String storageBaseFileName = "zBodyTempXMLStorage";

    static public
    void save() {
        FileOutputStream fileOutputStream;

        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(storageBaseFileName));
            String xml = ZBodyTempXMLSerializer.serializePersons(Persons.sharedInstance);
            out.write(xml.getBytes());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
