package com.application.paramountfinefoods;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.List;

public class FilterSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> values;

    public FilterSpinnerAdapter(Context context, int textViewResourceId,
                                List<String> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public String getItem(int position) {
        return values.get(position);//position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.WHITE);
        label.setText(values.get(position));

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.WHITE);
        label.setText(values.get(position));

        return label;
    }
}
