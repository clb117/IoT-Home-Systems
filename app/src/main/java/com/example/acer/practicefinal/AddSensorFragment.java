package com.example.acer.practicefinal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;


public class AddSensorFragment extends Fragment {

    // Layout views
    private EditText mControllerNumberEditText;
    private EditText mDeviceNameEditText;
    private EditText mPortEditText;
    private Switch mSwitchOrSensorSwitch;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
        mControllerNumberEditText = (EditText)view.findViewById(R.id.sensorControllerNumberEditText);
        mDeviceNameEditText = (EditText)view.findViewById(R.id.sensorDeviceNameEditText);
        mPortEditText = (EditText)view.findViewById(R.id.sensorPortEditText);
        mSwitchOrSensorSwitch = (Switch)view.findViewById(R.id.sensorSwitchOrSensorSwitch);
        saveToCSV();
    }

    // TODO implement saving these entered forms into csv
    private void saveToCSV(){
        
    }
}
