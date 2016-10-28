package com.benjamin.currencyapp.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Benjamin on 2015-11-19.
 */
public class CurrencyFileIO {

    Context context;

    public CurrencyFileIO(Context context) {
        this.context = context;

    }

    public Boolean write(String data, String fileName) {
        PrintWriter writer = null;
        OutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new PrintWriter(outputStream);
            writer.println(data);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void write(InputStream inputStream, String fileName) {
        FileOutputStream outputStream = null;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {

        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, String> readParseToMap(String fileName) {
        BufferedReader reader = null;
        Map<String, String> currencyRate = new HashMap<>();
        try {
            InputStream is = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line != null) {
                String[] array = line.split("\\s+");
                if (array.length == 2) {
                    currencyRate.put(array[0], array[1]);
                }
                line = reader.readLine();
            }
        } catch (IOException ioe) {

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return currencyRate;
    }


    public String readParseToString(String fileName) {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> currencyRate = new HashMap<>();
        try {
            InputStream is = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line + "\n");
                line = reader.readLine();
            }
        } catch (IOException ioe) {

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

}
