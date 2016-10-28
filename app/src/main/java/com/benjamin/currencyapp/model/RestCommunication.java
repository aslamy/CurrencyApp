package com.benjamin.currencyapp.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Benjamin on 2015-11-14.
 */
public class RestCommunication {
    private String mediaType;

    public RestCommunication() {
        this.mediaType = "application/xml";
    }


    public String create(String uri, String data) {
        return curd("POST", uri, data);
    }

    public String update(String uri, String data) {
        return curd("PUT", uri, data);
    }

    public String read(String uri) {
        return curd("GET", uri, null);
    }

    public String delete(String uri) {
        return curd("DELETE", uri, null);
    }

    private String curd(String method, String uri, String data) {
        StringBuilder buffer = new StringBuilder();
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", mediaType);

            if (data != null) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(data.getBytes());
                outputStream.flush();
            }
            if (conn.getResponseCode() == 204) {
                return null;
            }
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            while ((output = bufferedReader.readLine()) != null) {
                buffer.append(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
