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

    public ImageAdapter (Context c) {
        mContext = c;
    }

    public int getCount() {
        return 0;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
           view = new View(mContext);
            view = li.inflate(android.R.layout.room_grid_item, null);
            TextView textView = (TextView)view.findViewById(android.R.id.room_name);
            ImageView imageView = (ImageView)view.findViewById(android.R.drawable.room_image);
            textView.setText("Room Name"); //CB This will change according to room name saved in CSV
            imageView.setImageDrawable(android.R.drawable.ic_room_image);
        } else {
            view = (View) convertView;
        }

        return view;
    }


}
