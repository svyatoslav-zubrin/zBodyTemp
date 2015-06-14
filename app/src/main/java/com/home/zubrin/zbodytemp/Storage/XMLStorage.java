package com.home.zubrin.zbodytemp.Storage;

import android.content.Context;
import android.util.Log;

import com.home.zubrin.zbodytemp.Model.Persons;
import com.home.zubrin.zbodytemp.Model.xml.ZBodyTempXMLSerializer;
import com.home.zubrin.zbodytemp.Utils.AppUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by zubrin on 5/22/15.
 */
public class XMLStorage {

    static private String storageBaseFileName = "zBodyTempXMLStorage";

    // Public methods

    static public
    void save(Context context) {
        FileOutputStream fileOutputStream;

        OutputStream out = null;
        try {
            File filesDir = context.getFilesDir();
            out = new BufferedOutputStream(new FileOutputStream(filesDir + storageBaseFileName));
            String xml = ZBodyTempXMLSerializer.serializePersons(Persons.getSharedInstance(context));
            Log.d("XMLStorage", "xml: " + xml);
            out.write(xml.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static public
    String read(Context context) {
        try {
            File filesDir = context.getFilesDir();
            String result = getStringFromFile(filesDir + storageBaseFileName);
            Log.d("XMLStorage", "read: " + result);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Private methods

    private static
    String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    private static
    String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }
}
