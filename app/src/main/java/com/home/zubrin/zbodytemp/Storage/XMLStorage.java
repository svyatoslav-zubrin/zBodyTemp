package com.home.zubrin.zbodytemp.Storage;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
