package com.example.acer.practicefinal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * A class made to represent the room as an object
 *
 */
public class RoomObject {

    private String roomName;
    private Bitmap roomBitmap;

    public RoomObject(File csvPath){
        roomBitmap = null;
        String name = csvPath.getName();
        // room name is the file name without the extension
        roomName = name.substring(0, name.length()-".csv".length());
    }

    public RoomObject (File csvPath, File pngPath){
        this(csvPath);
        roomBitmap = getRoomBitmapFromFile(pngPath);
    }

    public String getRoomName(){
        return roomName;
    }
    
    public boolean hasImage(){
        return roomBitmap!=null;
    }

    public Bitmap getImage(){
        return roomBitmap;
    }

    /**
     * loads the png image from the file
     * @param pngPath
     * @return
     */
    private Bitmap getRoomBitmapFromFile (File pngPath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(pngPath.toString(), options);
    }
}
