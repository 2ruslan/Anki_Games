package rock.ankigames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import rock.ankigames.Anki.AnkiHelper;
import rock.ankigames.Preferences.PreferencesHelper;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int AD_PERM_REQUEST = 0;
    Spinner spinnerDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinnerDeck = findViewById(R.id.spDeck);

        if (Helper.shouldRequestPermission(this)) {
            Helper.requestPermission(MainActivity.this, AD_PERM_REQUEST);
        }
        else
        {
            start();
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

    private String getCurrentDeckName(){
        return spinnerDeck.getSelectedItem().toString();
    }

    public void OnClickMatchGame(View v){
        Helper.startGame(this, getCurrentDeckName(), Helper.GameType.match);
    }

    public void OnClickViewGame(View v){
        Helper.startGame(this, getCurrentDeckName(), Helper.GameType.cardView);
    }

    public void OnClickDayMode(View v){
        applyDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void OnClickAutoMode(View v){
        applyDayNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    public void OnClickNightMode(View v){
        applyDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }


    private void applyDayNightMode(int m){
        AppCompatDelegate.setDefaultNightMode(m);
        getDelegate().applyDayNight();
        PreferencesHelper.setDayNightMode(m);
    }
}
