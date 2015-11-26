package com.example.acer.practicefinal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Add room activity is the activity where
 * the user enters all the information regarding the room
 * including
 * name of the room (string)
 * picture of the room (optional)
 * [multiple] controller number (string)
 * [multiple] device name (string)
 * [multiple] port (string)
 * [multiple] type(switch or sensor) (boolean)
 */
public class AddRoomActivity extends FragmentActivity {

    private EditText mRoomNameEditText;
    private Button mTakePhotoOfRoomButton;
    private Button mAddMoreSensorButton;
    private Button mCommitAddRoomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        mRoomNameEditText = (EditText)findViewById(R.id.roomNameEditText);
        mTakePhotoOfRoomButton = (Button)findViewById(R.id.takePhotoOfRoomButton);
        mAddMoreSensorButton = (Button)findViewById(R.id.addMoreSensorButton);
        mCommitAddRoomButton = (Button)findViewById(R.id.commitAddRoomButton);

        mTakePhotoOfRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO take photo intent
            }
        });

        mAddMoreSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add another add sensor fragment
            }
        });

        mCommitAddRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO go through and save all the room info and sensor info to csv
            }
        });

        if (savedInstanceState == null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            AddSensorFragment fragment = new AddSensorFragment();
            transaction.replace(R.id.fragment_room_sensor, fragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
