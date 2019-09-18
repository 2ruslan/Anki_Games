package rock.ankigames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EndGameActivity extends AppCompatActivity {

    public static final String _GAME_TYPE = "_GAME_TYPE";

    Helper.GameType _gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent intent = getIntent();
        int p = intent.getIntExtra(_GAME_TYPE, -1);
        if (p != -1 )
            _gameType = Helper.GameType.values()[p];
        else
            finish();
    }

    public void OnClickRepeat(View w){
        Helper.startGame(this, _gameType);
        finish();
    }

    public void OnClickEnd(View w){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
