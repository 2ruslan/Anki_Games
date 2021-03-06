package rock.ankigames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import rock.ankigames.Common.Common;
import rock.ankigames.Preferences.PreferencesHelper;

public class EndGameActivity extends AppCompatActivity {

    public static final String _GAME_TYPE = "_GAME_TYPE";
    public static final String _GAME_TIME = "_GAME_TIME";
    public static final String _RECORD_TIME = "_RECORD_TIME";


    Helper.GameType _gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent intent = getIntent();

        int p = intent.getIntExtra(_GAME_TYPE, Common._NO_INT_VALUE);

        int gt = intent.getIntExtra(_GAME_TIME, Common._NO_INT_VALUE);
        int rt = intent.getIntExtra(_RECORD_TIME, Common._NO_INT_VALUE);

        StringBuilder sb = new StringBuilder();

        if (gt != Common._NO_INT_VALUE)
            sb.append(getResources().getString(R.string.current_result) + " : "+ Math.round(gt / 10) / 100.0  + "\n");
        if (rt != Common._NO_INT_VALUE)
            sb.append(getResources().getString(R.string.best_result) + " : " + Math.round(rt / 10)  / 100.0 + "\n");

        ((TextView)findViewById(R.id.tvInfo)).setText(sb.toString());


        if (p != Common._NO_INT_VALUE )
            _gameType = Helper.GameType.values()[p];
        else
            OnClickEnd(null);
    }

    public void OnClickRepeat(View w){
        Helper.startGame(this, _gameType);
        finish();
    }

    public void OnClickEnd(View w){
        Intent intent =new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
}
