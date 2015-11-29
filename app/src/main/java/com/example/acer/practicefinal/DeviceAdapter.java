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

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DeviceAdapter extends BaseAdapter{

    private static final String TAG = "DeviceAdapter";

    private Context mContext;
    private List<String[]> rows;


    public DeviceAdapter(Context context, RoomObject room){
        this.mContext = context;

        //open the room csv file
        List<String[]> rows = openCSV(room);
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Assuming the csv is formatted such that
     * [0] contains the name of the device
     * [1] contains whether it is a sensor or switch
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String[] deviceInfo = rows.get(position);
        final int deviceInfoLength = 3;
        if (deviceInfo.length < deviceInfoLength){
            Log.wtf(TAG, "Illegal amount of device info");
            return null;
        }
        if (convertView != null){
            if (isASensor(deviceInfo)){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_device_sensor, parent);
                TextView name = (TextView)convertView.findViewById(R.id.item_device_sensor_name);
                TextView value = (TextView)convertView.findViewById(R.id.item_device_sensor_value);
                name.setText(deviceInfo[0]);
                value.setText(getSensorValueFromExternalDevice(deviceInfo));
            }
            else if (isASwitch(deviceInfo)){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_device_switch, parent);
                TextView name = (TextView)convertView.findViewById(R.id.item_device_switch_name);
                Switch value = (Switch)convertView.findViewById(R.id.item_device_switch_value);
                name.setText(deviceInfo[0]);
                value.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        indicateSwitchValueChangedToExternalDevice(deviceInfo, isChecked);
                    }
                });
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
    private List<String[]> openCSV(RoomObject room){
        final File folder = new File (Environment.getExternalStorageDirectory().toString() + mContext.getString(R.string.directory));
        File csvFile = new File(folder,room.getRoomName()+".csv");
        List<String[]> list = null;
        try {
            CSVReader csvReader = new CSVReader(new FileReader(csvFile));
            list = csvReader.readAll();
            csvReader.close();
        } catch (FileNotFoundException e) {
            Log.wtf(TAG, String.format("csv file: %s.csv was not found", room.getRoomName()), e);
        } catch (IOException e) {
            Log.wtf(TAG, String.format("failed to read from the csv file: %s.csv", room.getRoomName(),e));
        }
        return list;
    }

    /**
     * extracted method in case column position of device type changes
     * @param deviceInfo
     * @return
     */
    private boolean isASensor(String[] deviceInfo){
        return deviceInfo[1].equals("Sensor");
    }

    private boolean isASwitch(String[] deviceInfo){
        return deviceInfo[1].equals("Switch");
    }

    /**
     * * TODO retrieve sensor value from device
     * @param deviceInfo
     * @return
     */
    private String getSensorValueFromExternalDevice(String[] deviceInfo){
        return "stub sensor value";
    }

    /**
     * TODO connect to the device and indicate that the switch value changed
     * @param deviceInfo
     */
    private void indicateSwitchValueChangedToExternalDevice(String[] deviceInfo, boolean isChecked){
        Log.d(TAG, "switch value was changed to " + isChecked);
    }
}