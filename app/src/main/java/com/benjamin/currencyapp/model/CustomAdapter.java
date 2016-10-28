package com.benjamin.currencyapp.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.benjamin.currencyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 2015-11-21.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Currency> currencyList;
    List<String> list;



    public CustomAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        currencyList = new ArrayList<>();
        this.list = list;
        map();
    }

    //try (Turkish lira) is reserved by Java.
    private void map() {
        for (String country : list) {
            int image;

            if (country.equals("TRY")) {
                image = context.getResources().getIdentifier("tlr", "drawable", context.getPackageName());
            } else {
                image = context.getResources().getIdentifier(country.toLowerCase(), "drawable", context.getPackageName());
            }
            int description = context.getResources().getIdentifier(country, "string", context.getPackageName());
            currencyList.add(new Currency(country, description, image));
        }
    }

    @Override
    public int getCount() {
        return currencyList.size();
    }

    @Override
    public Object getItem(int position) {

        return currencyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Currency temp = currencyList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row, parent, false);

        TextView textViewTitle = (TextView) row.findViewById(R.id.textViewTitle);
        textViewTitle.setText(temp.title);

        TextView textViewDescription = (TextView) row.findViewById(R.id.textViewDescription);
        textViewDescription.setText(context.getResources().getString(temp.description));


        ImageView imageView = (ImageView) row.findViewById(R.id.imageView);
        imageView.setImageResource(temp.image);

        return row;
    }
}
