package com.benjamin.currencyapp.model;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Benjamin on 2015-11-18.
 */
public class XMLCurrencyPullParser {


    private static final String TITLE = "Cube";
    private static final String CURRENCY = "currency";
    private static final String RATE = "rate";
    private static final String TIME = "time";

    private XmlPullParser parser;

    public XMLCurrencyPullParser() {
        parser = Xml.newPullParser();
    }

    public String parse(String XMLData) {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            parser.setInput(new StringReader(XMLData));
            int parseEvent = parser.getEventType();
            while (parseEvent != XmlPullParser.END_DOCUMENT) {
                switch (parseEvent) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName;
                        tagName = parser.getName();
                        if (tagName.equalsIgnoreCase(TITLE)) {
                            String currency = parser.getAttributeValue(null, CURRENCY);
                            String rate = parser.getAttributeValue(null, RATE);
                            String time = parser.getAttributeValue(null, TIME);
                            if (currency != null && rate != null) {
                                stringBuilder.append(currency + " " + rate + "\n");
                            }
                            if (time != null) {
                                stringBuilder.append("DATE" + " " + time + "\n");
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    default:

                }
                parseEvent = parser.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
