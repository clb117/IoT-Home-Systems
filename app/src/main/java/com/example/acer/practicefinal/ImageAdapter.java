package com.example.acer.practicefinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * CB A class to generate the images on the GridView
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    // references to our images
    //CB this will contain the images read from the CSV file which is saved in the create form
    private Integer[] mThumbIds = {
            R.drawable.ic_add_room,R.drawable.ic_add_room,R.drawable.ic_add_room,R.drawable.ic_add_room,R.drawable.ic_add_room,R.drawable.ic_add_room,R.drawable.ic_add_room,R.drawable.ic_add_room,R.drawable.ic_add_room
    };

    public ImageAdapter (Context c) {
        mContext = c;
    }

    public ImageAdapter (Context c, Integer[] thumbIds){
        mContext = c;
        mThumbIds = thumbIds;
    }

    public int getCount() {
        return 0;
    }

    //TODO Implement this
    public Object getItem(int position) {

        return null;
    }

    //TODO Implement this
    public long getItemId(int position) {

        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
           view = new View(mContext);

            view = li.inflate(R.layout.room_grid_item, null);
            TextView textView = (TextView)view.findViewById(R.id.room_name);
            //ImageView imageView = (ImageView)view.findViewById(R.drawable.room_image);
            textView.setText("Room Name"); //CB This will change according to room name saved in CSV
            //imageView.setImageDrawable(R.drawable.ic_room_image);
        } else {
            view = (View) convertView;
        }

        return view;
    }
}
