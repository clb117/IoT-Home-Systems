package com.example.acer.practicefinal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.File;
import java.util.ArrayList;

public class RoomAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<File> list;

    public RoomAdapter (Context context){
        mContext = context;
        list = new ArrayList<File>();
    }

    @Override
    public int getCount(){
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    /**
     * Goes through the directory and loads all the CSV in there to the arraylist
     */
    private void openAllCSV(){
        
    }
}
