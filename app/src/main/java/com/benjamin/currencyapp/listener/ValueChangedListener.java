package com.benjamin.currencyapp.listener;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benjamin.currencyapp.activity.MainActivity;
import com.benjamin.currencyapp.R;
import com.benjamin.currencyapp.model.CurrencyCalculator;

import java.util.Map;

/**
 * Created by Benjamin on 2015-11-18.
 */
public class ValueChangedListener implements EditText.OnKeyListener {

    MainActivity activity;

    EditText editTextFirst;
    EditText editTextSecond;
    TextView firstText;
    TextView secondText;
    Map<String, String> currencyMap;


    public ValueChangedListener(Context context) {
        this.activity = (MainActivity) context;
        editTextFirst = (EditText) this.activity.findViewById(R.id.editTextFirst);
        editTextSecond = (EditText) this.activity.findViewById(R.id.editTextSecond);


        this.firstText = (TextView) activity.findViewById(R.id.textViewFirst);
        this.secondText = (TextView) activity.findViewById(R.id.textViewSecond);

        currencyMap = this.activity.getCurrencyMap();

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_UP) {

            double rate1 = doubleOf(currencyMap.get(firstText.getText()));
            double rate2 = doubleOf(currencyMap.get(secondText.getText()));

            CurrencyCalculator calculator = new CurrencyCalculator();
            if (v.getId() == R.id.editTextFirst) {

                String text = editTextFirst.getText().toString();

                if (!text.isEmpty()) {

                    double value = doubleOf(text);
                    double calculatedValue = calculator.Calculate(rate1, rate2, value);
                    String result = String.format("%.2f", calculatedValue);

                    editTextSecond.setText(result);
                } else {
                    editTextSecond.setText("");
                }
            } else if (v.getId() == R.id.editTextSecond) {

                String text = editTextSecond.getText().toString();

                if (!text.isEmpty()) {

                    double value = doubleOf(text);
                    double calculatedValue = calculator.Calculate(rate2, rate1, value);
                    String result = String.format("%.2f", calculatedValue);
                    editTextFirst.setText(result);
                } else {
                    editTextFirst.setText("");
                }
            }
        }
        return false;
    }

    private double doubleOf(String data) {

        data = data.replaceAll(",", ".");
        return Double.valueOf(data);
    }
}

