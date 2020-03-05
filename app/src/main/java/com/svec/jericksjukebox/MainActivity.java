package com.svec.jericksjukebox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static int THEME_ID;
    public static int CHOSE_ID;
    public static int SORT_ASC;
    public static String SORT_FIELD;
    public static SharedPreferences sPref = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        themeCreation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateSongView();
    }

    private void populateSongView() {
        Song.audioResolver = getContentResolver();
        ArrayList<Song> songs = Song.updateSongList(null, SORT_FIELD, SORT_ASC == 1);
        if (songs.size() == 0) {
            songs = new ArrayList(Arrays.asList(new Song(0, "NO SONGS FOUND","", "", "")));
        }
        SongAdapter adapter = new SongAdapter(this, songs);
        ListView songView = (ListView) findViewById(R.id.songListView);
        songView.setAdapter(adapter);
    }

    private void themeCreation() {
        sPref = getSharedPreferences("JERICKS_JUKEBOX", MODE_PRIVATE);
        THEME_ID = sPref.getInt("THEME_ID", R.style.RedTheme);
        CHOSE_ID = sPref.getInt("CHOSE_ID", 0);
        SORT_ASC = sPref.getInt("SORT_ASC", 1);
        SORT_FIELD = sPref.getString("SORT_FIELD", "TITLE");
        setTheme(THEME_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "HELLO!", Toast.LENGTH_SHORT);
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(this, "HELLO!", Toast.LENGTH_SHORT);
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, 722);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 722) {
            recreate();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showAudioPage() {

    }

    public void setNowPlaying(@NonNull final Song song) {
    }
}