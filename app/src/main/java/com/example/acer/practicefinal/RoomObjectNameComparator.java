package com.example.acer.practicefinal;

import java.util.Comparator;

/**
 * Created by Ted-Desktop on 11/28/2015.
 */
public class RoomObjectNameComparator implements Comparator<RoomObject> {

    @Override
    public int compare(RoomObject lhs, RoomObject rhs) {
        return lhs.getRoomName().compareTo(rhs.getRoomName());
    }
}
