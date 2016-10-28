package com.benjamin.currencyapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.benjamin.currencyapp.R;
import com.benjamin.currencyapp.listener.CurrencyChangedListener;
import com.benjamin.currencyapp.listener.UpDownArrowListener;
import com.benjamin.currencyapp.listener.ValueChangedListener;
import com.benjamin.currencyapp.model.CurrencyFileIO;
import com.benjamin.currencyapp.model.CustomAdapter;
import com.benjamin.currencyapp.model.UpdateCurrencyRate;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<String, String> currencyMap = new HashMap<String, String>();
    private ArrayAdapter<String> adapter;
    private SharedPreferences preferences;
    private final String FILENAME = "currency_data.txt";
    TextView textViewFirst;
    TextView textViewSecond;
    EditText editTextFirst;
    EditText editTextSecond;
    ListView listViewFirst;
    ListView listViewSecond;
    Toolbar toolbar;
    UpdateCurrencyRate backgroundWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewFirst = (TextView) findViewById(R.id.textViewFirst);
        textViewSecond = (TextView) findViewById(R.id.textViewSecond);
        editTextFirst = (EditText) findViewById(R.id.editTextFirst);
        editTextSecond = (EditText) findViewById(R.id.editTextSecond);
        listViewFirst = (ListView) findViewById(R.id.listViewFirst);
        listViewSecond = (ListView) findViewById(R.id.listViewSecond);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        backgroundWork = new UpdateCurrencyRate(this);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean firstBoot = preferences.getBoolean("first_boot", true);
        textViewFirst.setText(preferences.getString("currency_first", "EUR"));
        textViewSecond.setText(preferences.getString("currency_second", "SEK"));

        //Check if it's first time app running
        if (firstBoot) {
            //save file with data in root directory.
            InputStream inputStream = getResources().openRawResource(R.raw.currency_data);
            CurrencyFileIO fileIO = new CurrencyFileIO(this);
            fileIO.write(inputStream, FILENAME);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("first_boot", false);
            editor.apply();
        }

        //Read file with data and save it in a hashMap.
        CurrencyFileIO fileIO = new CurrencyFileIO(this);
        currencyMap = fileIO.readParseToMap(FILENAME);
        currencyMap.put("EUR", "1");

        //Save hashMap data into arrayList
        ArrayList<String> list = new ArrayList<String>(currencyMap.keySet());
        Collections.sort(list);
        list.remove("DATE");

        //Connect editText componets to listener.
        ValueChangedListener valueChangedListener = new ValueChangedListener(this);
        editTextFirst.setOnKeyListener(valueChangedListener);
        editTextSecond.setOnKeyListener(valueChangedListener);

        //Connect editText componets to listener.
        CurrencyChangedListener currencyChangedListener = new CurrencyChangedListener(this);
        listViewFirst.setOnItemClickListener(currencyChangedListener);
        listViewSecond.setOnItemClickListener(currencyChangedListener);

        //Connect adapterto listView.
        CustomAdapter adapter = new CustomAdapter(this, list);
        listViewFirst.setAdapter(adapter);
        listViewSecond.setAdapter(adapter);

        //set listener to imageView.
        ImageView imageView = (ImageView) findViewById(R.id.imageViewUpDown);
        imageView.setOnClickListener(new UpDownArrowListener(this));

    }

    //onStart update currency rate.
    @Override
    protected void onStart() {
        super.onStart();

        try {
            String dateInString = currencyMap.get("DATE");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateInString);
            int diffInDays = diffInDays(new Date(), date);
            int days = preferences.getInt("update_days", 1);
            if (diffInDays > days && backgroundWork.getStatus() != AsyncTask.Status.FINISHED) {
                backgroundWork.execute();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Errors during updating!", Toast.LENGTH_LONG).show();
        }
    }

    //onStop save last selected  data.
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("currency_first", textViewFirst.getText().toString());
        editor.putString("currency_second", textViewSecond.getText().toString());
        editor.apply();
    }

    //onPause (on cancel) stop data updating.
    @Override
    protected void onPause() {
        super.onPause();
        backgroundWork.cancel(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public Map<String, String> getCurrencyMap() {
        return currencyMap;
    }

    public void setCurrencyMap(Map<String, String> currencyMap) {
        this.currencyMap = currencyMap;
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    public void setAdapter(ArrayAdapter<String> adapter) {
        this.adapter = adapter;
    }

    //count days
    private int diffInDays(Date dateNow, Date dateOld) {
        int diffInDays = (int) ((dateNow.getTime() - dateOld.getTime())
                / (1000 * 60 * 60 * 24));
        return diffInDays;
    }
}
