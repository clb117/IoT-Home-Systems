package com.example.acer.practicefinal;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class RoomAdapter extends BaseAdapter {
    private static final String TAG = "RoomAdapter";
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
     * Doesn't actually open the csv. That should happen when the user touches the corresponding grid
     */
    private void listAllCSV(){
        File folder = new File (Environment.getExternalStorageDirectory().toString() + mContext.getString(R.string.directory));
        if(folder.mkdir()){
            //mkdir returns true if there was no folder before and it was created
            //nothing in folder, can return early
            Log.d(TAG, "directory created, it didn't exist before");
            return;
        }
        // only care about csv files
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.toLowerCase().endsWith(".csv");
            }
        });
        for (File file : listOfFiles){
            list.add(file);
        }

    }
}
