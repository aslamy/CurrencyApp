package com.benjamin.currencyapp.listener;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benjamin.currencyapp.R;
import com.benjamin.currencyapp.activity.MainActivity;

/**
 * Created by Benjamin on 2015-11-22.
 */
public class UpDownArrowListener implements View.OnClickListener {
    Context context;

    public UpDownArrowListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) context;
        TextView textViewFirst = (TextView) mainActivity.findViewById(R.id.textViewFirst);
        TextView textViewSecond = (TextView) mainActivity.findViewById(R.id.textViewSecond);
        EditText editTextFirst = (EditText) mainActivity.findViewById(R.id.editTextFirst);
        EditText editTextSecond = (EditText) mainActivity.findViewById(R.id.editTextSecond);

        String textTemp = textViewFirst.getText().toString();
        String editTemp = editTextFirst.getText().toString();

        textViewFirst.setText(textViewSecond.getText());
        editTextFirst.setText(editTextSecond.getText());
        textViewSecond.setText(textTemp);
        editTextSecond.setText(editTemp);
    }
}
