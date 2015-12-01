package com.example.acer.practicefinal;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeviceAdapter extends BaseAdapter{

    private static final String TAG = "DeviceAdapter";

    private Context mContext;
    private List<String[]> roomList, initValues;
    private String Room;
    private final String switchStateON = "{\"in\":false}";
    private final String switchStateOFF = "{\"in\":true}";

    //TK AB CB Assuming that a room is a List<String[]>, each room may contain a number of controllers (List of)
    // and String[] contains information for each device attached to that controller.


    public DeviceAdapter(Context context, String room){
        this.mContext = context;
        this.Room = room;

        //open the room csv file
        //roomList = openCSV(room);
        initValues = GetInitValue();
        roomList = openCSVfromREST(room);
        //
    }

    @Override
    public int getCount() {
        return roomList.size();
    }

    @Override
    public Object getItem(int position) {
        return roomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Assuming the csv is formatted such that
     * [0] contains the name of the device
     * [1] contains whether it is a sensor or switch
     * [2] contains the URL of the device
     * [3] contains the key of the device
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String[] deviceInfo = roomList.get(position); //AB get the current controller
        final String[] deviceValue = initValues.get(position);
        final int deviceInfoLength = 2; //Name Type Pair
        if (deviceInfo.length < deviceInfoLength){
            Log.wtf(TAG, "Illegal amount of device info");
            return null;
        }
        if (convertView == null){
            Log.d(TAG, String.format("Device type is [%s]", deviceInfo[1]));
            if (isASensor(deviceInfo)){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_device_sensor, null);
                TextView name = (TextView)convertView.findViewById(R.id.item_device_sensor_name);
                TextView value = (TextView)convertView.findViewById(R.id.item_device_sensor_value);
                name.setText(deviceInfo[0]);
                // Communication with device here
                value.setText(GetSensor(deviceInfo[0]));
            }
            else if (isASwitch(deviceInfo)){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_device_switch, null);
                final TextView name = (TextView)convertView.findViewById(R.id.item_device_switch_name);
                Switch value = (Switch)convertView.findViewById(R.id.item_device_switch_value);
                name.setText(deviceInfo[0]);
                // Communication with device here
                //value.setChecked()
                value.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String res = SetSwitch(deviceInfo[0],isChecked);
                        //AB There will be some delay here... (REST)
                        indicateSwitchValueChangedToExternalDevice(deviceInfo, isChecked);
                        String success = name.getText().toString() + " Was toggled " + (isChecked ? "ON" : "OFF");
                        String toaster = (res.equals("{}") ? success : res+" There was a problem..." );
                        Toast.makeText(mContext, toaster, Toast.LENGTH_SHORT).show();
                    }
                });
                value.setChecked((deviceValue[1].equals("1") ? true:false));
            }
            else{
                Log.wtf(TAG, String.format("Illegal device type (%s) stated", deviceInfo[0]));
            }

        }

        return convertView;
    }

    /**
     * opens the csv file using opencsv library
     * @param room
     * @return
     */
    private List<String[]> openCSV(String room){
        final File folder = new File (Environment.getExternalStorageDirectory().toString() + mContext.getString(R.string.directory));
        File csvFile = new File(folder,room+".csv");
        List<String[]> urls = null;
        try {
            CSVReader csvReader = new CSVReader(new FileReader(csvFile));
            urls = csvReader.readAll();
            csvReader.close();
        } catch (FileNotFoundException e) {
            Log.wtf(TAG, String.format("csv file: %s.csv was not found", room), e);
        } catch (IOException e) {
            Log.wtf(TAG, String.format("failed to read from the csv file: %s.csv", room,e));
        }
        return urls;
    }

    // AB This function is to get the URL from the CSV and get Device info from the controller (REST)
    private List<String[]> openCSVfromREST(String room){
        List<String[]> urls = openCSV(room);
        List<String[]> devices = null;
        String devicesURL = urls.get(1)[0]; //AB The Devices url is in the second row in the CSV file.
        devices = GetDeviceList(devicesURL);
        // AB There will be some delay here... (REST)
        return devices;
    }

    /**
     * extracted method in case column position of device type changes
     * @param deviceInfo
     * @return
     */
    private boolean isASensor(String[] deviceInfo){
        return deviceInfo[1].equals("sensor");
    }

    private boolean isASwitch(String[] deviceInfo){
        return deviceInfo[1].equals("switch");
    }

    /**
     * * TODO retrieve sensor value from device
     * @param sensor
     * @return
     */
    public String GetSensor(String sensor) {

        String devicesURL = openCSV(Room).get(1)[0]; //AB The Devices url is in the second row in the CSV file.
        //sensor = sensor.replaceAll(" ", "%20"); //AB UTF-8 Encoding (Although it is not working with Thinger.io)
        String sensorURL = devicesURL.replaceAll("deviceList",sensor); // AB to specify which sensor...

        final RestClient client = new RestClient(sensorURL);
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
        return result[0].substring(result[0].indexOf(':') + 1, result[0].length() - 1);
    }

    private String SetSwitch(String sw, boolean isChecked) {
        String devicesURL = openCSV(Room).get(1)[0]; //AB The Devices url is in the second row in the CSV file.
        //sensor = sensor.replaceAll(" ", "%20"); //AB UTF-8 Encoding (Although it is not working with Thinger.io)
        String switchURL = devicesURL.replaceAll("deviceList",sw); // AB to specify which sensor...
        String state = (isChecked ? switchStateON : switchStateOFF);
        final RestClient client = new RestClient(switchURL);
        final String[] result = new String[1];
        client.setJSONString(state);

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    client.execute(RequestMethod.POST);
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
        return result[0];
    }

    public List<String[]> GetInitValue() {

        String devicesURL = openCSV(Room).get(1)[0]; //AB The Devices url is in the second row in the CSV file.
        //sensor = sensor.replaceAll(" ", "%20"); //AB UTF-8 Encoding (Although it is not working with Thinger.io)
        String sensorURL = devicesURL.replaceAll("deviceList","checkall"); // AB to specify which sensor...
        ArrayList<String[]> initialValues = new ArrayList<String[]>();

        final RestClient client = new RestClient(sensorURL);
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
        // AB output format {"out":{"Switch1":0,"Switch2":0,"Switch3":0,"Buzzer":0,"LightSensor":3}}
        result[0] = result[0].replaceAll("\"", ""); //Remove all quotes
        // result now {out:{Switch1:0,Switch2:0,Switch3:0,Buzzer:0,LightSensor:3}}
        result[0] =  result[0].substring(result[0].indexOf(":") + 2, result[0].length() - 2); //AB +2 is for :{ and -2 is for }}
        String[] nametypeArray = result[0].split(","); //AB name:value array elements
        for (int i = 0; i< nametypeArray.length; i++) {
            nametypeArray[i] = nametypeArray[i].replace("\\", ""); //Just in case
        }
        List<String> temp = Arrays.asList(nametypeArray);
        System.out.println(Arrays.toString(nametypeArray)); //Debug
        // AB by now, temp has a list of "name:value", so we have to separate by :
        for (String nametype : temp) {
            initialValues.add(nametype.split(":"));
        }
        for (String[] nametypepair : initialValues)
            System.out.println(Arrays.toString(nametypepair));
        // AB now we have devices in the list formatted as "Name", "Type"
        return initialValues;
    }

    /**
     * TODO connect to the device and indicate that the switch value changed
     * @param deviceInfo
     */
    private void indicateSwitchValueChangedToExternalDevice(String[] deviceInfo, boolean isChecked){
        Log.d(TAG, "switch value was changed to " + isChecked);
    }

    // Reply example format {"out":{"switch1":0,"switch2":0,"switch3":0,"switch4":0,"light":1024}}
    private String[] CheckDevice(String checkUrl) {
        String[] formatedCheck = new String[getCount()];
        return formatedCheck;
    }

    private List<String[]> GetDeviceList(String url) {
        final RestClient client = new RestClient(url);
        final String[] result = new String[1];
        ArrayList<String[]> devices = new ArrayList<String[]>();
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
        //AB output format {"out":"Switch 1:switch/Switch 2:switch/Switch 3:switch/Buzzer:switch/Light Sensor:sensor"}
        result[0] =  result[0].substring(result[0].indexOf(":") + 2, result[0].length() - 2); //AB +2 is for :" and -2 is for "}
        String[] nametypeArray = result[0].split("/");
        for (int i = 0; i< nametypeArray.length; i++) {
            nametypeArray[i] = nametypeArray[i].replace("\\", "");
        }
        List<String> temp = Arrays.asList(nametypeArray);
        System.out.println(Arrays.toString(nametypeArray));
        // AB by now, temp has a list of "name:type", so we have to separate by :
        for (String nametype : temp) {
            devices.add(nametype.split(":"));
        }
        for (String[] nametypepair : devices)
                System.out.println(Arrays.toString(nametypepair));
        // AB now we have devices in the list formatted as "Name", "Type"
        return devices;
        //AB returning My Living Room
    }
}
