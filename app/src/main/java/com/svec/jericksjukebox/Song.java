package com.svec.jericksjukebox;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;

import static android.provider.MediaStore.Audio.Media.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Song {
    public static ContentResolver audioResolver;
    private static ArrayList<Song> songList = new ArrayList<>();
    private long id;
    private String title;
    private String artist;
    private String album;
    private String path;
    private static MediaPlayer player = null;


    public Song(long id, String title, String artist, String album, String path) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.path = path;
    }

    /**
     * Updates Song list and returns new list
     *
     * @param args String Array of folder paths to check, pass null to check all folders
     * @return an updated ArrayList of Songs found in query
     */
    public static ArrayList<Song> updateSongList(@Nullable String[] args, String sortBy, boolean asc) {
        Uri musicUri = EXTERNAL_CONTENT_URI;

        Cursor cursor = audioResolver.query(musicUri,
                null,
                args != null && args.length != 0 ? RELATIVE_PATH + " like ?" : null,
                args,
                sortBy + " " + (asc ? "ASC" : "DESC"));

        if (cursor != null && cursor.moveToFirst()) {
            songList.clear();
            int Title = cursor.getColumnIndex(TITLE);
            int Artist = cursor.getColumnIndex(ARTIST);
            int Id = cursor.getColumnIndex(_ID);
            int Album = cursor.getColumnIndex(ALBUM);
            int Path = cursor.getColumnIndex(RELATIVE_PATH);

            do {
                long id = cursor.getLong(Id);
                String artist = cursor.getString(Artist);
                String album = cursor.getString(Album);
                String title = cursor.getString(Title);
                String path = cursor.getString(Path);
                Song song = new Song(id, title, artist, album, path);
                songList.add(song);
            } while (cursor.moveToNext());
        }

        return songList;
    }

    public void playAudio(Context c) {
        if (player != null) {
            player.stop();
        }
        Uri uri = ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, id);
        player = MediaPlayer.create(c, uri);
        player.start();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getPath() {
        return path;
    }

}
