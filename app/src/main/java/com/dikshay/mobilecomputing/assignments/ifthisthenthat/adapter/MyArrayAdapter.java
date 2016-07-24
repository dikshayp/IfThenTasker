package com.dikshay.mobilecomputing.assignments.ifthisthenthat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dikshay.mobilecomputing.assignments.ifthisthenthat.R;

/**
 * Created by Dikshay on 4/12/2016.
 */
public class MyArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String values[];

    public MyArrayAdapter(Context context, String values[])
    {
        super(context, R.layout.listview_layout,values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.listview_layout,null);
        TextView textView = (TextView)rowView.findViewById(R.id.title);
        ImageView imageView =  (ImageView)rowView.findViewById(R.id.icon);
        textView.setText(values[position]);
        String s = values[position];
        /*
        if(s.startsWith("Calculator"))
        {
            imageView.setImageResource(R.mipmap.calculator);
        }
        else
        if(s.startsWith("Github"))
        {
            imageView.setImageResource(R.mipmap.github);
        }
        else
        if(s.startsWith("About Us"))
        {
            imageView.setImageResource(R.mipmap.us);
        }*/
        return rowView;

    }
}
