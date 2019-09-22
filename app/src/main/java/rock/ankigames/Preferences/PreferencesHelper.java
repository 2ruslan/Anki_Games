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
    public static final String NOTE_VIEW_POS = "DAY_NIGHT_MODE";

    private static PreferenceValue _defaultDeck;
    private static PreferenceValue _recordMatch;
    private static PreferenceValue _dayNightMode;
    private static PreferenceValue _noteViewPos;


    public static void init(Context context) {
        mSettings = context.getSharedPreferences(PreferencesHelper.APP_PREFERENCES, Context.MODE_PRIVATE);

        _defaultDeck = new PreferenceValue(mSettings, DEFAULT_DECK, Common._NO_VALUE);
        _recordMatch = new PreferenceValue(mSettings, RECORD_MATCH, Common._NO_INT_VALUE);
        _dayNightMode = new PreferenceValue(mSettings, DAY_NIGHT_MODE, Common._NO_INT_VALUE);
        _noteViewPos= new PreferenceValue(mSettings, NOTE_VIEW_POS, Common._POS_END);

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


    //region NOTE_VIEW_POS
    public static void setNoteViewPos(int val) {
        _noteViewPos.setInt(val);
    }

    public static int getNoteViewPos() {
        return _noteViewPos.getInt();
    }
    //endregion NOTE_VIEW_POS


}
