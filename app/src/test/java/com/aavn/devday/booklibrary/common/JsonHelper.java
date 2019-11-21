package com.aavn.devday.booklibrary.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class JsonHelper {
    private static JsonHelper instance;

    private JsonHelper() {
    }

    public static JsonHelper getInstance() {
        if (instance == null) {
            instance = new JsonHelper();
        }
        return instance;
    }

    public String readJSONFromAsset(String path) {
        InputStream source = getClass().getResourceAsStream("/" + path);
        BufferedInputStream bis = new BufferedInputStream(source);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        String textReturn;
        try {
            int result = bis.read();
            while (result != -1) {
                buf.write(result);
                result = bis.read();
            }
            textReturn = buf.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return textReturn;
    }
}
