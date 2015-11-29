package com.example.acer.practicefinal;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DeviceListActivity extends ListActivity {
    private static final String TAG = "DeviceListActivity";
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        String roomName = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            roomName = extras.getString(MainActivity.ROOM_NAME_KEY);
            Log.d(TAG, String.format("room name was passed through the intent and it is [%s]", roomName));
        }
        else {
            Log.wtf(TAG, "room name was not passed through the intent");
            return;
        }

        listAdapter = new DeviceAdapter(this, roomName);
        setListAdapter(listAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){

    }

}