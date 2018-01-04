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
public class RestaurantsFragment extends Fragment {


    public RestaurantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ArrayList<Places> myPlaces = new ArrayList<Places>();
        myPlaces.add(new Places(getString(R.string.restaurant_first_head),
                getString(R.string.restaurant_first_detail),
                R.drawable.nik, getString(R.string.restaurant_first_location)));

        myPlaces.add(new Places(getString(R.string.restaurant_second_head),
                getString(R.string.restaurant_second_detail),
                R.drawable.duck, getString(R.string.restaurant_second_location)));

        myPlaces.add(new Places(getString(R.string.restaurant_third_head),
                getString(R.string.restaurant_third_detail), R.drawable.hops,
                getString(R.string.restaurant_third_location)));

        myPlaces.add(new Places(getString(R.string.restaurant_fourth_head),
                getString(R.string.restaurant_fourth_detail), R.drawable.monicas,
                getString(R.string.restaurant_fourth_location)));

        myPlaces.add(new Places(getString(R.string.restaurant_fifth_head),
                getString(R.string.restaurant_fifth_detail), R.drawable.pal,
                getString(R.string.restaurant_fifth_location)));

        myPlaces.add(new Places(getString(R.string.restaurant_sixth_head),
                getString(R.string.restaurant_sixth_detail), R.drawable.brook,
                getString(R.string.restaurant_sixth_location)));

        myPlaces.add(new Places(getString(R.string.restaurant_seventh_head),
                getString(R.string.restaurant_eighth_detail), R.drawable.beer,
                getString(R.string.restaurant_seventh_location)));

        myPlaces.add(new Places(getString(R.string.restaurant_eighth_head),
                getString(R.string.restaurant_eighth_detail), R.drawable.kingbeer, getString(R.string.restaurant_eighth_location)));



        PlaceAdapter pt = new PlaceAdapter(getContext(), myPlaces, R.color.category_restaurants);
        View RootView = inflater.inflate(R.layout.activity_placelist, container, false);
        ListView lt = (ListView)RootView.findViewById(R.id.list);
        lt.setAdapter(pt);
        return RootView;
    }

}
