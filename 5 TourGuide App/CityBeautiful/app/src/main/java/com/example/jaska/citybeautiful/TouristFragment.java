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
public class TouristFragment extends Fragment {


    public TouristFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ArrayList<Places> myPlaces = new ArrayList<Places>();
        myPlaces.add(new Places(getString(R.string.tourist_first_head),
                getString(R.string.tourist_first_detail) , R.drawable.rock_garden,
                getString(R.string.tourist_first_location)));

        myPlaces.add(new Places(getString(R.string.tourist_second_head),
                getString(R.string.tourist_second_detail) ,R.drawable.sukhna_lake,
                getString(R.string.tourist_second_location)));

        myPlaces.add(new Places(getString(R.string.tourist_third_head),
                getString(R.string.tourist_third_detail) , R.drawable.rose_garden,
                getString(R.string.tourist_third_location)));

        myPlaces.add(new Places(getString(R.string.tourist_fourth_head),
                        getString(R.string.tourist_fourth_detail) ,R.drawable.fun_city,
                        getString(R.string.tourist_fourth_location)));

        myPlaces.add(new Places(getString(R.string.tourist_fifth_head),
                getString(R.string.tourist_fifth_detail) ,R.drawable.pinjore_garden,
                getString(R.string.tourist_fifth_location)));

        myPlaces.add(new Places(getString(R.string.tourist_sixth_head),
                getString(R.string.tourist_sixth_detail) ,R.drawable.bougainvillea_garden,
                getString(R.string.tourist_sixth_location)));

        myPlaces.add(new Places(getString(R.string.tourist_seventh_head),
                getString(R.string.tourist_seventh_detail) , R.drawable.international_dolls_museum,
                getString(R.string.tourist_seventh_location)));

        myPlaces.add(new Places(getString(R.string.tourist_eighth_head),
                        getString(R.string.tourist_eighth_detail) ,R.drawable.children_traffic_park,
                        getString(R.string.tourist_eighth_location)));

        myPlaces.add(new Places(getString(R.string.tourist_ninth_head),
                                getString(R.string.tourist_ninth_detail) ,R.drawable.fragnance_garden,
                                getString(R.string.tourist_ninth_location)));
        PlaceAdapter pt = new PlaceAdapter(getContext(), myPlaces, R.color.category_tourist);
        View RootView = inflater.inflate(R.layout.activity_placelist, container, false);
        ListView lt = (ListView)RootView.findViewById(R.id.list);
        lt.setAdapter(pt);

        return RootView;

    }

}
