package com.example.acer.practicefinal;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.File;

public class DeviceAdapter extends BaseAdapter{
    private static final String TAG = "DeviceAdapter";

    private Context mContext;

    public DeviceAdapter(Context context, RoomObject room){
        this.mContext = context;

        //open the room csv file

    }

    @Override
    public int getCount() {
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

    private String[] openCSV(RoomObject room){
        final File folder = new File (Environment.getExternalStorageDirectory().toString() + mContext.getString(R.string.directory));
        File csvFile = new File(folder,room.getRoomName()+".csv");

        return null;
    }
}
