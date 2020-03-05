package com.svec.jericksjukebox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {
    private ArrayList<Song> songs;
    private static Context c;

    SongAdapter(Context c, ArrayList<Song> songs) {
        this.songs = songs;
        this.inf = LayoutInflater.from(c);
        this.c = c;
    }

    private LayoutInflater inf;

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout = (LinearLayout) inf.inflate(R.layout.song, parent, false);
        TextView songTitle= layout.findViewById(R.id.songTitle);
        TextView songArtist = layout.findViewById(R.id.songArtist);
        TextView songPath = layout.findViewById(R.id.songPath);

        final Song song = songs.get(position);

        layout.setOnClickListener(playAudio(song));

        songTitle.setText(song.getTitle());
        songArtist.setText(song.getArtist());
        songPath.setText(song.getPath());
        layout.setTag(position);

        return layout;
    }

    public View.OnClickListener playAudio(final Song song) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song.playAudio(c);
            }
        };
    }
}
