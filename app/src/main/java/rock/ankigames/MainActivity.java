package rock.ankigames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private AnkiDroidHelper mAnkiDroid;
    private static final int AD_PERM_REQUEST = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAnkiDroid = new AnkiDroidHelper(this);

        if (mAnkiDroid.shouldRequestPermission()) {
            mAnkiDroid.requestPermission(MainActivity.this, AD_PERM_REQUEST);
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

         StaticHolder.init(this);

        //getInfo();
     }



     void getInfo(){
         Map<Long, String> decks =mAnkiDroid.getDeckList();

         StringBuffer sb = new StringBuffer();

         for(Map.Entry<Long, String> d : decks.entrySet())
         {
            sb.append(d.getValue() + "\r");
         }

         //TextView t = findViewById(R.id.tInfo);
         //t.setText(sb.toString());

      //   WebView webW = findViewById(R.id.webW);


        // webW.loadData( dt, "text/html; charset=utf-8", "UTF-8");

      //   mAnkiDroid.removeDuplicates();
     }



     public void OnClickSettings(View v){
     //   startActivity(new Intent( this, Settings.class ));
     }

    public void OnClickMatchGame(View v){
        //startActivityForResult(new Intent( this, Settings.class ));
    }

    public void OnClickVievCardsGame(View v){
        //startActivityForResult(new Intent( this, Settings.class ));
    }

}
