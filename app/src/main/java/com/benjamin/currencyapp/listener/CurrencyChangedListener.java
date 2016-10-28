package com.benjamin.currencyapp.listener;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.benjamin.currencyapp.activity.MainActivity;
import com.benjamin.currencyapp.R;
import com.benjamin.currencyapp.model.Currency;
import com.benjamin.currencyapp.model.CurrencyCalculator;

import java.util.Map;


/**
 * Created by Benjamin on 2015-11-18.
 */
public class CurrencyChangedListener implements AdapterView.OnItemClickListener {
    Context context;
    MainActivity activity;
    EditText editTextFirst;
    EditText editTextSecond;
    CurrencyCalculator calculator;
    Map<String, String> currencyMap;
    ListView listViewFirst;
    ListView listViewSecond;
    TextView textViewFirst;
    TextView textViewSecond;


    public CurrencyChangedListener(Context context) {
        this.context = context;
        this.activity = (MainActivity) context;
        this.listViewFirst = (ListView) activity.findViewById(R.id.listViewFirst);
        this.listViewSecond = (ListView) activity.findViewById(R.id.listViewSecond);
        this.textViewFirst = (TextView) activity.findViewById(R.id.textViewFirst);
        this.textViewSecond = (TextView) activity.findViewById(R.id.textViewSecond);
        editTextFirst = (EditText) this.activity.findViewById(R.id.editTextFirst);
        editTextSecond = (EditText) this.activity.findViewById(R.id.editTextSecond);
        calculator = new CurrencyCalculator();
        currencyMap = this.activity.getCurrencyMap();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.listViewFirst) {
            Currency selectedItem = (Currency) listViewFirst.getAdapter().getItem(position);
            textViewFirst.setText(selectedItem.getTitle());
            double rate1 = doubleOf(currencyMap.get(textViewFirst.getText()));
            double rate2 = doubleOf(currencyMap.get(textViewSecond.getText()));
            String text = editTextSecond.getText().toString();


            if (!text.isEmpty()) {

                double value = doubleOf(text);
                double calculatedValue = calculator.Calculate(rate2, rate1, value);
                String result = String.format("%.2f", calculatedValue);
                editTextFirst.setText(result);
            } else {
                editTextFirst.setText("");
            }
        } else if (parent.getId() == R.id.listViewSecond) {

            Currency selectedItem = (Currency) listViewSecond.getAdapter().getItem(position);
            textViewSecond.setText(selectedItem.getTitle());
            double rate2 = doubleOf(currencyMap.get(textViewSecond.getText()));
            double rate1 = doubleOf(currencyMap.get(textViewFirst.getText()));
            String text = editTextFirst.getText().toString();

            if (!text.isEmpty()) {

                double value = doubleOf(text);
                double calculatedValue = calculator.Calculate(rate1, rate2, value);
                String result = String.format("%.2f", calculatedValue);

                editTextSecond.setText(result);
            } else {
                editTextSecond.setText("");
            }
        }
    }


    private double doubleOf(String data) {

        data = data.replaceAll(",", "\\.");
        return Double.valueOf(data);
    }
}
