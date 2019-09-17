package rock.ankigames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int AD_PERM_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

         StaticHolder.init(this);

        //getInfo();
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
