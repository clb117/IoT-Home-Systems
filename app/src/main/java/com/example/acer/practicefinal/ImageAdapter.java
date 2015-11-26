package com.example.acer.practicefinal;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

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
        return mThumbIds.length;
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
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        // insert on click listener here imageView.setOnClickListener();
        return imageView;
    }
}
