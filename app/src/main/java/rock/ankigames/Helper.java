package rock.ankigames;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import rock.ankigames.Anki.AnkiHelper;
import rock.ankigames.Common.Common;
import rock.ankigames.MatchGame.MatchGame;
import rock.ankigames.Preferences.PreferencesHelper;
import rock.ankigames.ViewCardsGame.CardViewGame;

import static com.ichi2.anki.api.AddContentApi.READ_WRITE_PERMISSION;

public class Helper {

    public static final String _ANKI_DROID = "com.ichi2.anki";

    public static boolean shouldRequestPermission(Context c) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        return ContextCompat.checkSelfPermission(c, READ_WRITE_PERMISSION) != PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity callbackActivity, int callbackCode) {
        ActivityCompat.requestPermissions(callbackActivity, new String[]{READ_WRITE_PERMISSION}, callbackCode);
    }

    public static boolean checkInstallAnkiDroid(Context c){
        PackageManager pm = c.getPackageManager();
        try {
            pm.getPackageInfo(_ANKI_DROID, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public enum GameType{
        match(0),
        cardView(1)
        ;

        private final int value;
        GameType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    private static final String _LAST_DECK = "_LAST_DECK";
    public static void startGame(Context c, GameType game){
        startGame(c, _LAST_DECK, game);
    }
    public static void startGame(Context c, String deckName, GameType game){

        if (deckName.equals(Common._NO_VALUE)){
            showDialog(c, R.string.no_anki_no_decks);
        }
        else {
            if (!deckName.equals(_LAST_DECK)) {
                if (!AnkiHelper.initNotes(deckName))
                    return;
            }

            if (AnkiHelper.getCountNotes() == 0)
                showDialog(c, R.string.no_cards_in_deck);
            else if (game == GameType.match)
                c.startActivity(new Intent(c, MatchGame.class));
            else if (game == GameType.cardView)
                c.startActivity(new Intent(c, CardViewGame.class));
        }
    }

    public static void endGame(Activity a, GameType g){
        endGame(a, g, Common._NO_INT_VALUE, Common._NO_INT_VALUE);
    }

    public static void endGame(Activity a, GameType g, int timeResult, int timeBest){
        Intent i = new Intent(a, EndGameActivity.class);
        i.putExtra(EndGameActivity._GAME_TYPE, g.getValue());
        i.putExtra(EndGameActivity._GAME_TIME, timeResult);
        i.putExtra(EndGameActivity._RECORD_TIME, timeBest);
        a.startActivity(i);
        a.finish();
    }


    public static void showDialog(Context c, int msgId){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(msgId)
                .setTitle("Anki Games")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
