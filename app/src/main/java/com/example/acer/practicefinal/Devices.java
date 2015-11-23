package com.example.acer.practicefinal;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * CB a class for the list view
 */
public class Devices extends ListActivity {

    //CB will have the devices that will be shown in the list view
    private ArrayList<String> devices;
    //CB will check if switches are on or off
    protected ToggleButton mSwitchToggle;
    //CB String Adapter to handle the listview
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ss_list);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                devices);
        setListAdapter(adapter);
    }


/*    //CB a method to read the devices in the CSV and to add these names to the arrayList to be inserted to list view
    public ArrayList<String> findListItems() {
    }

    mSwitchToggle = (ToggleButtofindViewById(R.id.switch_toggle);

    mSwitchToggle.setOnCheckedChangeListener(
            new ToggleButton.OnCheckedChangeListener()

    {
        public void onCheckedChanged (CompoundButton buttonView,boolean isChecked){
        if (isChecked) {
            Context context = getApplicationContext();
            CharSequence text = "Switch is on";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration); //CB message to user indicating switch is on
            toast.show();
        } else if (!isChecked) {
            Context context = getApplicationContext();
            CharSequence text = "Switch is off";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration); //CB message to user indicating switch is off
            toast.show();
        }
    }
    })*/
}



