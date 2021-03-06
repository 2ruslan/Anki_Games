package rock.ankigames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import rock.ankigames.Anki.AnkiHelper;
import rock.ankigames.Common.Common;
import rock.ankigames.Preferences.PreferencesHelper;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int AD_PERM_REQUEST = 0;
    Spinner spinnerDeck;

    ToggleButton tbNight;
    ToggleButton tbDay;
    ToggleButton tbSun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerDeck = findViewById(R.id.spDeck);
        tbNight = findViewById(R.id.tbNight);
        tbDay = findViewById(R.id.tbDay);
        tbSun = findViewById(R.id.tbSun);

        indicateDayNight(PreferencesHelper.getDayNightMode());

        if (!Helper.checkInstallAnkiDroid(this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.no_anki_droid_msg)
                    .setTitle("Anki Games")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Helper._ANKI_DROID)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + Helper._ANKI_DROID)));
                            }
                            finish();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            if (Helper.shouldRequestPermission(this)) {
                Helper.requestPermission(MainActivity.this, AD_PERM_REQUEST);
            } else {
                start();
            }
        }
    }

     public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         if (requestCode == AD_PERM_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
             start();
         } else {
                finish();
         }
     }

     void start(){
         AnkiHelper.init(this);
         initSppinerDeck();
     }

    private void initSppinerDeck(){
        spinnerDeck.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                PreferencesHelper.setDefaultDeck(spinnerDeck.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });


        ArrayList<String> d = AnkiHelper.getDeckNames();

        ArrayAdapter<String> adapter
                = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, d);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeck.setAdapter(adapter);

        if(d.contains(PreferencesHelper.getDefaultDeck()))
            spinnerDeck.setSelection(d.indexOf(PreferencesHelper.getDefaultDeck()));

    }

    private String getCurrentDeckName() {
        String result;
        try {
            result = spinnerDeck.getSelectedItem().toString();
        }catch (Exception e){
            result = Common._NO_VALUE;
        }
        return result;
    }

    public void OnClickMatchGame(View v){
        Helper.startGame(this, getCurrentDeckName(), Helper.GameType.match);
    }

    public void OnClickViewGame(View v){
        Helper.startGame(this, getCurrentDeckName(), Helper.GameType.cardView);
    }

    public void OnClickDayMode(View v){
        applyDayNightMode(AppCompatDelegate.MODE_NIGHT_NO, getResources().getString(R.string.day_night_mode_day));
    }

    public void OnClickAutoMode(View v){
        applyDayNightMode(AppCompatDelegate.MODE_NIGHT_AUTO, getResources().getString(R.string.day_night_mode_auto));
    }

    public void OnClickNightMode(View v){
        applyDayNightMode(AppCompatDelegate.MODE_NIGHT_YES, getResources().getString(R.string.day_night_mode_night));
    }


    private void applyDayNightMode(int m, String msg){
        AppCompatDelegate.setDefaultNightMode(m);
        getDelegate().applyDayNight();
        PreferencesHelper.setDayNightMode(m);
        indicateDayNight(m);

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void indicateDayNight(int m){
        if (m == AppCompatDelegate.MODE_NIGHT_YES){
            tbNight.setChecked(true);
            tbSun.setChecked(false);
            tbDay.setChecked(false);
        }
        else if (m == AppCompatDelegate.MODE_NIGHT_AUTO){
            tbNight.setChecked(false);
            tbSun.setChecked(false);
            tbDay.setChecked(true);
        }
        else if (m == AppCompatDelegate.MODE_NIGHT_NO){
            tbNight.setChecked(false);
            tbSun.setChecked(true);
            tbDay.setChecked(false);
        }
    }
}
