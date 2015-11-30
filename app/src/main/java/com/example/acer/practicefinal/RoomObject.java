package com.example.acer.practicefinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVReader;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * A class made to represent the room as an object
 *
 */
public class RoomObject {

    private String roomName;
    private Bitmap roomBitmap;
    private Context mContext;

    public RoomObject(File csvPath, Context context){
        this.mContext = context;
        roomBitmap = null;
        String name = csvPath.getName();
        // room name is the file name without the extension
        //roomName = name.substring(0, name.length()-".csv".length());

        String roomNameURL = getUrlFromCSV(csvPath);
        roomName = GetRoomName(roomNameURL);
        System.out.println(roomName);
        // There will be some delay here to get the room name online...
        File renamedCSV = new File(csvPath.getParent()+File.separator+roomName+".csv");
        csvPath.renameTo(renamedCSV); // To rename the file to the Room Name...
    }

    public RoomObject (File csvPath, File pngPath, Context context){
        this(csvPath, context);
        roomBitmap = getRoomBitmapFromFile(pngPath);
    }

    public void addImage(File pngPath){
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

    private String getUrlFromCSV(File csvFile){
        //final File folder = new File (Environment.getExternalStorageDirectory().toString() + mContext.getString(R.string.directory));
        //File csvFile = new File(folder,roomName+".csv");
        //csvPath = new File(folder)
        List<String[]> list = null;
        try {
            //Thread.sleep(1000);
            FileReader reader = new FileReader(csvFile);
            CSVReader csvReader = new CSVReader(reader);
            list = csvReader.readAll();
            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String roomNameLink = list.get(0)[0]; //AB Room Name URL was stored in the cell [0,0] in the CSV file.
        return roomNameLink;
    }

    private String GetRoomName(String url) {
        final RestClient client = new RestClient(url);
        final String[] result = new String[1];
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    client.execute(RequestMethod.GET);
                    if (client.getResponseCode() != 200) {
                        //return server error
                        result[0] = client.getErrorMessage();
                        return;
                    }
                    //return valid data
                    JSONObject jObj = new JSONObject(client.getResponse());
                    //result = new String[1];
                    result[0] = jObj.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    result[0] = e.toString();
                }
            }
        });
        thread.start();
        while (result[0] == null);
        //AB output format {"out":"My Living Room"}
        return result[0].substring(result[0].indexOf(':')+2, result[0].length()-2);
        //AB returning My Living Room
    }
}
