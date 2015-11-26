package com.example.acer.practicefinal;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * CB a class for the list view
 */
public class Devices extends ListActivity {

    //CB will have the devices that will be shown in the list view
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (true) {//switch
            adapter = new ArrayAdapter<String>(this, R.layout.switch_view, R.id.switch_name);
        }
        else {//sensor
            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.sensor_view, R.id.sensor_value);
            setListAdapter(adapter);
            //getMenuInflator().inflate(R.menu.ss_list);
        }
    }

//    @Override
//    protected void onListItemClicked(ListView lv, View v, int position, long id) {
//        String item = (String)getListAdapter().getItem(position);
//        if (item.equals("switch")) {
//            //turn switch on or off
//        }
//        else{
//            if ((item.equals("sensor"))) {
//                //update sensor value
//            }
//        }
//    }
}



