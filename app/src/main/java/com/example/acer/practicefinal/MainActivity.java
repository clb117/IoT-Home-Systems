package com.example.acer.practicefinal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_ADD_ROOM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.content_add) {
            // launch the add room activity
            Intent intent = new Intent(this, AddRoomActivity.class);
            startActivityForResult(intent, REQUEST_ADD_ROOM);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case REQUEST_ADD_ROOM:
                break;
            default:
                Log.wtf(TAG, "undefined request returned");
        }
    }
}
