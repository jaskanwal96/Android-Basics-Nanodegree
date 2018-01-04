package com.example.jaska.bazinga;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
/*
Bibliography:
 * MediaStore:
 * https://developer.android.com/reference/android/provider/MediaStore.html
 * https://gist.github.com/novoda/374533
 * https://stackoverflow.com/questions/36810845/android-mediastore-sqliteexception-no-such-column-title
 *
 * Permissions:
 * https://developer.android.com/training/permissions/requesting.html
 * https://stackoverflow.com/questions/35858981/read-and-write-permission-for-storage-and-gallery-usage-for-marshmallow
 */
public class SongsList extends AppCompatActivity {
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private static final int MY_PERMISSION_REQUEST = 1;
    private static final int MAX_SONGS = 50;
    ArrayList<Songs> mySongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);
        mySongs = new ArrayList<Songs>();
        StoragePermissionAndAdd();
        SongAdapter at = new SongAdapter(this, mySongs);
        ListView lt = (ListView)findViewById(R.id.list);
        lt.setAdapter(at);

        lt.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Intent it = new Intent(SongsList.this, SongDetails.class);
                it.putExtra("Song_Name",mySongs.get(i).getSongName());
                it.putExtra("Song_Artist", mySongs.get(i).getArtistName());
                it.putExtra("Song_Album_Art", mySongs.get(i).getAlbumArt());
                it.putExtra("Song_Display_Name", mySongs.get(i).getDisplayName());
                it.putExtra("Song_Album_Name", mySongs.get(i).getAlbumName());
                it.putExtra("Song_Duration", mySongs.get(i).getDuration());
                it.putExtra("Song_Size", mySongs.get(i).getSize());

                startActivity(it);
            }
        });

    }

    public  boolean StoragePermissionAndAdd() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission is granted", Toast.LENGTH_LONG).show();
                getSongsData();
                return true;
            } else {

                Toast.makeText(this, "Permission is revoked", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Toast.makeText(this, "Permission is granted", Toast.LENGTH_LONG).show();
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            getSongsData();
        }
        else {
            Toast.makeText(this, "Permission is revoked", Toast.LENGTH_LONG).show();
        }
    }

    private void getSongsData() {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.SIZE
        };

        Cursor cursor = this.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);
        String[] imgProjection = {
                MediaStore.Audio.Albums.ALBUM_ART
        };
        int songCounter = 0;

        while(cursor.moveToNext() && songCounter++ < MAX_SONGS){
            String imgSelection = MediaStore.Audio.Albums._ID + " = " + cursor.getString(6);

            Cursor imgCursor = this.getContentResolver().query(
                    MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    imgProjection,
                    imgSelection,
                    null,
                    null);
            while(imgCursor.moveToNext()){

                mySongs.add(new Songs( cursor.getString(2), cursor.getString(1),
                        imgCursor.getCount()!=0 ? imgCursor.getString(0) :"no_image",
                        cursor.getString(4), cursor.getString(5), cursor.getString(7),
                        cursor.getString(8)));

            }
        }
    }
}
