package rock.ankigames;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.ichi2.anki.api.AddContentApi.READ_WRITE_PERMISSION;

public class Helper {

    public static boolean shouldRequestPermission(Context c) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        return ContextCompat.checkSelfPermission(c, READ_WRITE_PERMISSION) != PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity callbackActivity, int callbackCode) {
        ActivityCompat.requestPermissions(callbackActivity, new String[]{READ_WRITE_PERMISSION}, callbackCode);
    }
}
