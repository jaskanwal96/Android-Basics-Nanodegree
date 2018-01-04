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
public class EventsFragment extends Fragment {


    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ArrayList<Places> myPlaces = new ArrayList<Places>();
        myPlaces.add(new Places(getString(R.string.event_first_head),
                getString(R.string.event_first_detail),
                R.drawable.rose_garden, getString(R.string.event_first_location)));

        myPlaces.add(new Places(getString(R.string.event_second_head),
                getString(R.string.event_second_detail), R.drawable.pec,
                getString(R.string.event_second_location)));

        myPlaces.add(new Places(getString(R.string.event_third_head),
                getString(R.string.event_third_detail),
                R.drawable.pinjore_garden, getString(R.string.event_third_location)));

        myPlaces.add(new Places(getString(R.string.event_fourth_head),
                getString(R.string.event_fourth_detail),
                R.drawable.grub, getString(R.string.event_fourth_location)));

        myPlaces.add(new Places(getString(R.string.event_fifth_head),
                getString(R.string.event_fifth_detail),
                R.drawable.carnival, getString(R.string.event_fifth_location)));

        PlaceAdapter pt = new PlaceAdapter(getContext(), myPlaces, R.color.category_events);
        View RootView = inflater.inflate(R.layout.activity_placelist, container, false);
        ListView lt = (ListView)RootView.findViewById(R.id.list);
        lt.setAdapter(pt);
        return RootView;
    }

}
