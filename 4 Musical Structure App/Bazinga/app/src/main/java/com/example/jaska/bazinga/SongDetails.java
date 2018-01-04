package com.example.jaska.bazinga;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SongDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);
        ImageView rt = (ImageView) findViewById(R.id.background_img);
        ImageView album_art = (ImageView)findViewById(R.id.detail_album_art);
        String coverPath = getIntent().getStringExtra("Song_Album_Art");
        if(coverPath==null){
            rt.setBackgroundResource(R.drawable.cover);
            album_art.setImageResource(R.drawable.cover);
        }
        else
        {
            Drawable imgArt = Drawable.createFromPath(coverPath);
            rt.setBackground(imgArt);
            album_art.setImageDrawable(imgArt);
        }
        TextView song_name = (TextView)findViewById(R.id.detail_song_name);
        song_name.setText(getIntent().getStringExtra("Song_Name"));

        TextView artist_name = (TextView)findViewById(R.id.detail_artist_name);
        artist_name.setText(getIntent().getStringExtra("Song_Artist"));

        TextView display_name = (TextView)findViewById(R.id.display_name);
        display_name.setText(getIntent().getStringExtra("Song_Display_Name"));

        TextView album_name = (TextView)findViewById(R.id.album_name);
        album_name.setText(getIntent().getStringExtra("Song_Album_Name"));

        TextView duration = (TextView)findViewById(R.id.duration);
        int minutesConversionFactor = 1000*60*60;
        Double minutes = (Double.valueOf(getIntent().getStringExtra("Song_Size"))
                /minutesConversionFactor);
        String durationInMinutes = Math.ceil(minutes)+" Minutes";
        duration.setText(durationInMinutes);

        TextView size = (TextView)findViewById(R.id.size);
        int conversionFactor = 1000*1000;
        String sizeSong = (Double.valueOf(getIntent().getStringExtra("Song_Size"))
                                /conversionFactor)+" Mb";
        size.setText(sizeSong);



    }
}
