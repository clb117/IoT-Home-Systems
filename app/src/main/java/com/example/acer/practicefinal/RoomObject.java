package com.example.acer.practicefinal;

import java.io.File;

/**
 * A class made to represent the room as an object
 *
 */
public class RoomObject {

    private File pngPath;
    private String roomName;

    public RoomObject(File csvPath){
        this.pngPath = null;

        String name = csvPath.getName();
        // room name is the file name without the extension
        roomName = name.substring(0, name.length()-".csv".length());
    }

    public RoomObject (File csvPath, File pngPath){
        this(csvPath);
        this.pngPath = pngPath;
    }

    public boolean hasImage(){
        return pngPath!=null;
    }

    
}
