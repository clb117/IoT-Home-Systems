//package com.example.acer.practicefinal;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.Switch;
//
//
//public class AddSensorFragment extends Fragment {
//
//    // Layout views
//    private EditText mControllerNumberEditText;
//    private EditText mDeviceNameEditText;
//    private EditText mPortEditText;
//    private Switch mSwitchOrSensorSwitch;
//
//    private boolean switchValue;
//
//    @Override
//    public void onCreate (Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        switchValue = false;
//    }
//
//    @Override
//    public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
//        mControllerNumberEditText = (EditText)view.findViewById(R.id.sensorControllerNumberEditText);
//        mDeviceNameEditText = (EditText)view.findViewById(R.id.sensorDeviceNameEditText);
//        mPortEditText = (EditText)view.findViewById(R.id.sensorPortEditText);
//        mSwitchOrSensorSwitch = (Switch)view.findViewById(R.id.sensorSwitchOrSensorSwitch);
//
//        mSwitchOrSensorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                switchValue = isChecked;
//            }
//        });
//    }
//
//    public String getControllerNumber(){
//        return mControllerNumberEditText.getText().toString();
//    }
//
//    public String getDeviceName(){
//        return mDeviceNameEditText.getText().toString();
//    }
//
//    public String getPort(){
//        return mPortEditText.getText().toString();
//    }
//
//    public boolean getSensorType(){
//        return switchValue;
//    }
//
//}
