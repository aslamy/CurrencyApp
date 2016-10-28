package com.benjamin.currencyapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.benjamin.currencyapp.R;

import java.util.zip.Inflater;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        int updateDays = preferences.getInt("update_days", 1);
        RadioButton radioButton;

        switch (updateDays) {
            case 1:
                radioButton = (RadioButton) findViewById(R.id.one_day);
                radioButton.setChecked(true);
                break;
            case 3:
                radioButton = (RadioButton) findViewById(R.id.three_days);
                radioButton.setChecked(true);
                break;
            case 7:
                radioButton = (RadioButton) findViewById(R.id.one_week);
                radioButton.setChecked(true);
                break;
            case 30:
                radioButton = (RadioButton) findViewById(R.id.one_month);
                radioButton.setChecked(true);
                break;
        }

        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
                Toast.makeText(SettingActivity.this, "Saved!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

    private void onRadioButtonClicked(View view) {

        SharedPreferences.Editor editor = preferences.edit();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        View radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        int checkedRadioId = radioGroup.indexOfChild(radioButton);

        switch (checkedRadioId) {
            case 0:
                editor.putInt("update_days", 1);
                break;
            case 1:
                editor.putInt("update_days", 3);

                break;
            case 2:
                editor.putInt("update_days", 7);

                break;
            case 3:
                editor.putInt("update_days", 30);
                break;
        }
        editor.apply();
    }

}
