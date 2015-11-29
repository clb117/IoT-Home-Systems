package com.example.acer.practicefinal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

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

    private static final int REQUEST_ADD_ROOM = 0;
    private static final int REQUEST_EDIT_ROOM = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "AddRoomActivity";
    private EditText mRoomNameEditText;
    private Button mTakePhotoOfRoomButton;
    private Button mAddMoreSensorButton;
    private Button mCommitAddRoomButton;

    private Bitmap roomImageBitmap = null;

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
                dispatchTakePictureIntent();
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
                commit();
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

    /**
     * dispatches the intent to take picture of the room
     */
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            roomImageBitmap = (Bitmap) extras.get("data");
        }
    }

    /**
     * Finalizes and saves the user's input fields to SD
     */
    private void commit(){
        String roomName = mRoomNameEditText.getText().toString();
        File roomDir = new File (Environment.getExternalStorageDirectory().toString() + getString(R.string.directory));
        roomDir.mkdir();
        File csvFile = new File(roomDir, roomName+".csv");
        File pictureFile = new File(roomDir, roomName+".png");
        if (csvFile.exists()){
            Toast.makeText(AddRoomActivity.this, "You already have this room added", Toast.LENGTH_LONG).show();
            return;
        }
        //TODO write to csv


        //save the bitmap
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(pictureFile);
            roomImageBitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        catch (Exception e){
            Log.wtf(TAG, "could not save the picture", e);
        }
    }
}
