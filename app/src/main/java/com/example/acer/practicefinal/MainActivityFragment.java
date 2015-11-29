//package com.example.acer.practicefinal;
//
//import android.support.v4.app.Fragment;
//
////import android.app.Fragment;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.GridView;
//
///**
// * A placeholder fragment containing a simple view.
// */
//public class MainActivityFragment extends Fragment {
//
//    GridView gridview;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.fragment_main);
//        ImageAdapter adapter = new ImageAdapter(MainActivityFragment.this.getContext());
//        gridview.setAdapter(adapter);
//    /*    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            //here it would switch to the list of sensors and switches in the room clicked
//
//        }
//    )};*/
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//       return inflater.inflate(R.layout.fragment_main, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//     /*   gridview = (GridView) view.findViewById(R.id.gridview);
//        gridview.setAdapter(new ImageAdapter(this.getContext()));*/
//    }
//}
