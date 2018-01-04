package com.example.jaska.bazinga;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jaska on 29-Nov-17.
 *
 */

public class SongAdapter extends ArrayAdapter<Songs> {
    public SongAdapter(@NonNull Context context, @LayoutRes int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public SongAdapter(@NonNull Context context,  List<Songs> objects) {
        super(context, 0, objects);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Songs currentSong = getItem(position);
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        TextView t = (TextView)listItemView.findViewById(R.id.song_name);
        t.setText(currentSong.getSongName());
        t = (TextView)listItemView.findViewById(R.id.artist_name);
        t.setText(currentSong.getArtistName());
        ImageView img = (ImageView)listItemView.findViewById(R.id.album_art);
        String coverPath = currentSong.getAlbumArt();
        if(coverPath==null){
            img.setImageResource(R.drawable.cover);
        }
        else
        {
            Drawable imgArt = Drawable.createFromPath(coverPath);
            img.setImageDrawable(imgArt);
        }
        return listItemView;
    }
}
