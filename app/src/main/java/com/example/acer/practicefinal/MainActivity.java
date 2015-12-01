package com.example.acer.practicefinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    public static final String ROOM_NAME_KEY = "room_name";
    private static final int REQUEST_ADD_ROOM = 0;
    private String MainText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupGridView();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        setupGridView();
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
//            Intent intent = new Intent(this, AddRoomActivity.class);
//            startActivityForResult(intent, REQUEST_ADD_ROOM);
//            return true;
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

    public void setOptionsTitle(String status)
    {
        MainText = status;

    }

    private void setupGridView() {
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        final RoomAdapter roomAdapter = new RoomAdapter(this);
        gridView.setAdapter(roomAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent (MainActivity.this, DeviceListActivity.class);
                intent.putExtra(ROOM_NAME_KEY, ((RoomObject)roomAdapter.getItem(position)).getRoomName());
                startActivity(intent);
                // debug stub toast for now
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
