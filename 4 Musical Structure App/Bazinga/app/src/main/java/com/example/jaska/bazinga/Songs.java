package com.example.jaska.bazinga;

/**
 * Created by jaska on 29-Nov-17.
 */

public class Songs {
    private String songName;
    private String artistName;
    private String albumArt;
    private String displayName;
    private String duration;
    private String albumName;
    private String size;

    public Songs(String song, String artist, String art, String name, String dur,
                 String album, String siz) {
        songName = song;
        artistName = artist;
        albumArt = art;
        displayName = name;
        duration = dur;
        albumName = album;
        size = siz;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDuration() {
        return duration;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getSize() {
        return size;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumArt() {
        return albumArt;
    }
}
