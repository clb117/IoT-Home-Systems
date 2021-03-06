package com.example.acer.practicefinal;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
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
     * there can be itemsPerRow - 1 amount of items per row for android reasons
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position/itemsPerRow;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RoomObject room = roomObjects[position];
        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int height = mContext.getResources().getDisplayMetrics().heightPixels;
        //int width = parent.getWidth();
        if (convertView == null){
            // room has image, make the view an imageview
            if (room.hasImage()){
                Log.d(TAG, String.format("view at position %d has an image", position));
                ImageView imageView = new ImageView(mContext);
                imageView.setImageBitmap(room.getImage());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
                imageView.setLayoutParams(new GridView.LayoutParams(width/2, width/2));
                return imageView;
            }
            else{
                //room does not have an image, make the view a textview
                Log.d(TAG, String.format("view at position %d has a text", position));
                TextView textView = new TextView(mContext);
                String roomName = room.getRoomName();
                textView.setText(roomName);
                textView.setPadding(8, 8, 8, 8);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setLayoutParams(new GridView.LayoutParams(width / 2-250, width / 2-250));
                textView.setTextSize(12);
                textView.setLines(2);
                if (roomName.toLowerCase().contains("bed"))
                    textView.setBackgroundResource(R.mipmap.bedroom_512);
                else textView.setBackgroundResource(R.mipmap.living_512);
                textView.setTextColor(Color.BLACK);
                textView.invalidate();
                //textView.setLayoutParams(new GridView.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)));
                return textView;
            }
        }
        else{
            return convertView;
        }
    }

    /**
     * Goes through the directory and loads all the CSV in there to the arraylist
     * Doesn't actually open the csv. That should happen when the user touches the corresponding grid
     */
    private Collection<RoomObject> listAllCSV(){
        final File folder = new File (Environment.getExternalStorageDirectory().toString() + mContext.getString(R.string.directory));
        final HashMap<String, RoomObject> roomMap = new HashMap<String, RoomObject>();
        if(folder.mkdir()){
            //mkdir returns true if there was no folder before and it was created
            //nothing in folder, can return early
            Log.d(TAG, "directory created, it didn't exist before");
            return roomMap.values();
        }
        // only care about csv and png files
        folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.toLowerCase().endsWith(".csv")){
                    Log.d(TAG, String.format("File name of the csv is %s", filename));
                    RoomObject room = new RoomObject(new File(dir.getAbsolutePath()+File.separator+filename), mContext);
                    roomMap.put(room.getRoomName(), room);
                    return true;
                }
                return false;
            }
        });
        folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.toLowerCase().endsWith(".png")){
                    Log.d(TAG, String.format("File name of the png is %s", filename));
                    String roomName;
                    String name = new File(filename).getName();
                    // room name is the file name without the extension
                    roomName = name.substring(0, name.length()-".png".length());
                    if (!roomMap.containsKey(roomName)){
                        // Doesn't contain the room, illegal picture
                        return false;
                    }
                    roomMap.get(roomName).addImage(new File(folder, filename));
                    return true;
                }
                return false;
            }
        });
        return roomMap.values();
    }
}
