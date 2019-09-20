package rock.ankigames.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import rock.ankigames.Common.Common;


public class PreferencesHelper {



    private static SharedPreferences mSettings;

    public static final String APP_PREFERENCES = "preference";
    public static final String DEFAULT_DECK = "DEFAULT_DECK";
    public static final String RECORD_MATCH = "RECORD_MATCH";
    public static final String DAY_NIGHT_MODE = "DAY_NIGHT_MODE";

    private static PreferenceValue _defaultDeck;
    private static PreferenceValue _recordMatch;
    private static PreferenceValue _dayNightMode;


    public static void init(Context context) {
        mSettings = context.getSharedPreferences(PreferencesHelper.APP_PREFERENCES, Context.MODE_PRIVATE);

        _defaultDeck = new PreferenceValue(mSettings, DEFAULT_DECK, Common._NO_VALUE);
        _recordMatch = new PreferenceValue(mSettings, RECORD_MATCH, Common._NO_INT_VALUE);
        _dayNightMode = new PreferenceValue(mSettings, DAY_NIGHT_MODE, Common._NO_INT_VALUE);

    }

    //region DEFAULT_DECK
    public static void setDefaultDeck(String val) {
        _defaultDeck.setStr(val);
    }

    public static String getDefaultDeck() {
        return _defaultDeck.getStr();
    }
    //endregion DEFAULT_DECK

    //region RECORD_MATCH
    public static void setRecordMatch(int val) {
        _recordMatch.setInt(val);
    }

    public static int getRecordMatch() {
        return _recordMatch.getInt();
    }
    //endregion RECORD_MATCH

    //region DAY_NIGHT_MODE
    public static void setDayNightMode(int val) {
        _dayNightMode.setInt(val);
    }

    public static int getDayNightMode() {
        return _dayNightMode.getInt();
    }
    //endregion DAY_NIGHT_MODE
}
