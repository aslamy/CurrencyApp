package com.benjamin.currencyapp.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import com.benjamin.currencyapp.activity.MainActivity;

import java.util.Map;

/**
 * Created by Benjamin on 2015-11-19.
 */
public class UpdateCurrencyRate extends AsyncTask<Void, Void, Void> {

    private final String URI = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private final String FILENAME = "currency_data.txt";
    MainActivity activity;
    CurrencyFileIO fileInputOutput;
    RestCommunication communication;
    XMLCurrencyPullParser parser;
    boolean isUpdated = false;
    Context context;

    public UpdateCurrencyRate(Context context) {
        this.context = context;
        this.activity = (MainActivity) context;
        communication = new RestCommunication();
        parser = new XMLCurrencyPullParser();
        fileInputOutput = new CurrencyFileIO(context);
    }


    @Override
    protected Void doInBackground(Void... params) {

        if (isNetworkAvailable() && !isCancelled()) {

            String XMLData = communication.read(URI);

            if (XMLData != null) {
                String data = parser.parse(XMLData);
                fileInputOutput.write(data, FILENAME);
            }
            isUpdated = true;
            publishProgress();
        } else {
            isUpdated = false;
        }

        return null;
    }

    //Updating View components
    @Override
    protected void onProgressUpdate(Void... values) {
        Map<String, String> currencyMap = fileInputOutput.readParseToMap(FILENAME);
        activity.getCurrencyMap().clear();
        activity.getCurrencyMap().putAll(currencyMap);
        activity.getCurrencyMap().put("EUR", "1");
    }

    @Override
    protected void onPostExecute(Void Void) {
        if (isUpdated) {
            Toast.makeText(context, "All data has been updated!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "No internet connection is found!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
