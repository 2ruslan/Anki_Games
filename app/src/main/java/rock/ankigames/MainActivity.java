package rock.ankigames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import rock.ankigames.MatchGame.MatchGame;
import rock.ankigames.Anki.AnkiHelper;

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
        ArrayAdapter<String> adapter
                = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, AnkiHelper.getDeckNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeck.setAdapter(adapter);
    }

    private String getCurrentDeckName(){
        return spinnerDeck.getSelectedItem().toString();
    }

    public void OnClickMatchGame(View v){
        Helper.startGame(this, getCurrentDeckName(), Helper.GameType.match);
    }

    public void OnClickVievCardsGame(View v){
        Helper.startGame(this, getCurrentDeckName(), Helper.GameType.match);
    }

}
