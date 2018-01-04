package com.example.jaska.citybeautiful;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollegeFragment extends Fragment {


    public CollegeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ArrayList<Places> myPlaces = new ArrayList<Places>();
        myPlaces.add(new Places(getString(R.string.college_first_head),
                getString(R.string.college_first_detail), R.drawable.punjab_university,
                getString(R.string.college_first_location)));

        myPlaces.add(new Places(getString(R.string.college_second_head),
                getString(R.string.college_second_detail), R.drawable.pec,
                getString(R.string.college_second_location)));

        myPlaces.add(new Places(getString(R.string.college_third_head),
                getString(R.string.college_third_detail), R.drawable.mac_dav,
                getString(R.string.college_third_location)));

        myPlaces.add(new Places(getString(R.string.college_fourth_head),
                getString(R.string.college_fourth_detail), R.drawable.chitkara,
                getString(R.string.college_fourth_location)));

        myPlaces.add(new Places(getString(R.string.college_fifth_head),
                getString(R.string.college_fifth_detail), R.drawable.sdcc,
                getString(R.string.college_fifth_location)));

        myPlaces.add(new Places(getString(R.string.college_sixth_head),
                getString(R.string.college_sixth_detail), R.drawable.cgc,
                getString(R.string.college_sixth_location)));

        myPlaces.add(new Places(getString(R.string.college_seventh_head),
                getString(R.string.college_seventh_detail), R.drawable.csio,
                getString(R.string.college_seventh_location)));


        PlaceAdapter pt = new PlaceAdapter(getContext(), myPlaces, R.color.category_college);
        View RootView = inflater.inflate(R.layout.activity_placelist, container, false);
        ListView lt = (ListView)RootView.findViewById(R.id.list);
        lt.setAdapter(pt);
        return RootView;
    }

}
