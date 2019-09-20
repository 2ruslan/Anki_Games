package rock.ankigames;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import rock.ankigames.Common.Common;
import rock.ankigames.Preferences.PreferencesHelper;

public class AnkiGamesApplications extends Application {
    public void onCreate() {
        super.onCreate();
        PreferencesHelper.init(this);

        int m = PreferencesHelper.getDayNightMode();
        if (m != Common._NO_INT_VALUE)
            AppCompatDelegate.setDefaultNightMode(m);
    }
}
