package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class base extends BaseAdapter {

    Context context;
    String list[];
    int image[];
    LayoutInflater inflater;

    public base(Context ctx, String [] lst, int[] img) {
        this.context = ctx;
        this.list = lst;
        this.image = img;
        inflater = LayoutInflater.from(ctx);


    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_listview, null);
        TextView textView =  (TextView) view.findViewById(R.id.textview);
        ImageView exImg = (ImageView) view.findViewById(R.id.image);
        textView.setText(list[i]);
        exImg.setImageResource(image[i]);
        return view;
    }
}
