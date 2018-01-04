package com.example.jaska.citybeautiful;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaska on 19-Dec-17.
 */

public class PlaceAdapter extends ArrayAdapter<Places> {
    int backgroundColor;
    public PlaceAdapter(@NonNull Context context, @NonNull List<Places> objects, int color) {
        super(context, 0, objects);
        backgroundColor = color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Places currentPlace = getItem(position);
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        final View final_view = listItemView;
        View textcontainer = listItemView.findViewById(R.id.text_container);
        textcontainer.setBackgroundResource(backgroundColor);
        TextView p = (TextView)listItemView.findViewById(R.id.place);
        p.setText(currentPlace.getPlaceName());
        p = (TextView)listItemView.findViewById(R.id.location);
        p.setText(currentPlace.getLocation());
        ImageView img = (ImageView)listItemView.findViewById(R.id.my_image);
        int imageId = currentPlace.getImageResourceId();
        if(imageId == -1){
            img.setVisibility(View.GONE);
        }
        else
        {
            img.setImageResource(imageId);
        }

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(final_view.getContext(), PlaceDetailsActivity.class);
                it.putExtra("Name", currentPlace.getPlaceName());
                it.putExtra("Details", currentPlace.getPlaceDetails());
                it.putExtra("Image", currentPlace.getImageResourceId());
                it.putExtra("Location", currentPlace.getLocation());
                it.putExtra("Background", backgroundColor);
                getContext().startActivity(it);
            }
        });
        return listItemView;
    }
}
