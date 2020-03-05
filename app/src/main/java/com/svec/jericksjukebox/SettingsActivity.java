package com.svec.jericksjukebox;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import static com.svec.jericksjukebox.MainActivity.sPref;

public class SettingsActivity extends AppCompatActivity {
    private static int themeId = MainActivity.THEME_ID;
    private static int choseId = MainActivity.CHOSE_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(themeId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //  Handle Scan Group Radio Buttons
        final RadioGroup scanGroup = findViewById(R.id.scanTypeGroup);
        scanGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.choseFolders) {
                    choseId = 1;
                    scanGroup.check(R.id.choseFolders);
                    findViewById(R.id.choseFoldersBtn).setEnabled(true);
                } else {
                    choseId = 0;
                    scanGroup.check(R.id.allFolders);
                    findViewById(R.id.choseFoldersBtn).setEnabled(false);
                }
            }
        });

        //  Set Chose Folder Button
        findViewById(choseId == 0 ? R.id.allFolders : R.id.choseFoldersBtn).setEnabled(true);
        if (choseId == 0) {
            scanGroup.check(R.id.allFolders);
        } else {
            scanGroup.check(R.id.choseFolders);
            findViewById(R.id.choseFoldersBtn).setEnabled(true);
        }

        //  Add Click Handler to cancel button
        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {cancelChanges();    }
        });

        //  Add Click Handler to save button
        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
    }

    public void showThemePicker(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), themeId));
        builder.setTitle(R.string.pick_theme)
                .setItems(R.array.theme_names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1)         //  Dark Theme
                            themeId = R.style.DarkTheme;
                        else if (which == 2)    //  Orange Theme
                            themeId = R.style.OrangeTheme;
                        else if (which == 3)    //  Red Theme
                            themeId = R.style.RedTheme;
                        else                    //  Light Theme By Default
                            themeId = R.style.LightTheme;
                        
                        recreate();
                    }
                });
        builder.create();
        builder.show();
    }

    public void showLanguagePicker(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Pick A Language")
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String lang;
                        switch (which) {
                            case 1: //  French
                                lang = "fr-rFR";
                                break;
                            case 2: //  Spanish
                            case 3: //  Russian
                            case 4: //  Portuguese
                            default://  English By Default
                                lang = "en";
                                break;
                        }
                        Locale locale = new Locale(lang);
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            config.setLocale(locale);
                        } else {
                            config.locale = locale;
                        }
                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    }
                });
        builder.create();
        builder.show();
    }

    private void saveSettings() {
        SharedPreferences.Editor edit = sPref.edit();

        edit.putInt("THEME_ID", MainActivity.THEME_ID = themeId);
        edit.putInt("CHOSE_ID", MainActivity.CHOSE_ID = choseId);
        edit.apply();

        finish();
    }

    private void cancelChanges() {
        themeId = MainActivity.THEME_ID;
        choseId = MainActivity.CHOSE_ID;
        finish();
    }
}
