package rock.ankigames.Preferences;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesHelper {

    public static final String _NO_VALUE = "!@#$%";
    public static final int _NO_INT_VALUE = -1;

    private static SharedPreferences mSettings;

    public static final String APP_PREFERENCES = "preference";
    public static final String DEFAULT_DECK = "DEFAULT_DECK";
    public static final String RECORD_MATCH = "RECORD_MATCH";

    private static PreferenceValue _defaultDeck;
    private static PreferenceValue _recordMatch;


    public static void init(Context context) {
        mSettings = context.getSharedPreferences(PreferencesHelper.APP_PREFERENCES, Context.MODE_PRIVATE);

        _defaultDeck = new PreferenceValue(mSettings, DEFAULT_DECK, _NO_VALUE);
        _recordMatch = new PreferenceValue(mSettings, RECORD_MATCH, _NO_INT_VALUE);

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

}
