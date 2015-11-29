package com.example.acer.practicefinal;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DeviceListActivity extends ListActivity {
    private static final String TAG = "DeviceListActivity";
    private static final int REQUEST_EDIT_ROOM = 1;

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

    /**
     * do we even need this?
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){

    }

    /**
     * TODO make a menu for editting the room csv file
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_device_list, menu);
        return true;
    }

    /**
     * TODO make sure this is the right menu item
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.content_add) {
            // launch the add room activity
            Intent intent = new Intent(this, AddRoomActivity.class);
            startActivityForResult(intent, REQUEST_EDIT_ROOM);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}