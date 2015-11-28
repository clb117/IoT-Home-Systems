package com.example.acer.practicefinal;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class RoomAdapter extends BaseAdapter {
    private static final String TAG = "RoomAdapter";
    private static final int itemsPerRow = 4;
    private Context mContext;
    private RoomObject[] roomObjects;

    public RoomAdapter (Context context){
        mContext = context;
        Collection<RoomObject> list = listAllCSV();
        roomObjects =  list.toArray(new RoomObject[list.size()]);
        Arrays.sort(roomObjects, new RoomObjectNameComparator());
    }

    @Override
    public int getCount(){
        return roomObjects.length;
    }

    @Override
    public Object getItem(int position) {
        return roomObjects[position];
    }

    /**
     * returns the row id of the item
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position/itemsPerRow;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    /**
     * Goes through the directory and loads all the CSV in there to the arraylist
     * Doesn't actually open the csv. That should happen when the user touches the corresponding grid
     */
    private Collection<RoomObject> listAllCSV(){
        File folder = new File (Environment.getExternalStorageDirectory().toString() + mContext.getString(R.string.directory));
        final HashMap<String, RoomObject> roomMap = new HashMap<String, RoomObject>();
        if(folder.mkdir()){
            //mkdir returns true if there was no folder before and it was created
            //nothing in folder, can return early
            Log.d(TAG, "directory created, it didn't exist before");
            return roomMap.values();
        }
        // only care about csv files

        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.toLowerCase().endsWith(".csv")){
                    RoomObject room = new RoomObject(new File(filename));
                    roomMap.put(room.getRoomName(), room);
                    return true;
                }
                if (filename.toLowerCase().endsWith(".png")){
                    String roomName;
                    String name = new File(filename).getName();
                    // room name is the file name without the extension
                    roomName = name.substring(0, name.length()-".png".length());
                    roomMap.get(roomName).addImage(new File(filename));
                    return true;
                }
                return false;
            }
        });
        return roomMap.values();
    }
}
