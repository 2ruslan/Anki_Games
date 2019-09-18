package rock.ankigames.MatchGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import rock.ankigames.Anki.AnkiHelper;
import rock.ankigames.Anki.NoteInfo;
import rock.ankigames.EndGameActivity;
import rock.ankigames.Helper;
import rock.ankigames.R;


public class MatchGame extends AppCompatActivity {

    ArrayList<NoteInfo> _notes;

    MatchWordTextView[] _buttons = new MatchWordTextView[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_game);

        start();
    }

    private void start(){
        _notes = AnkiHelper.getRandomNotes(6);

        initBottoms();

        showNotes();
    }

    private void end(){
        Intent i = new Intent(this, EndGameActivity.class);
        i.putExtra(EndGameActivity._GAME_TYPE, Helper.GameType.match.getValue());
        startActivity(i);
        finish();
    }

    private void initBottoms(){
        _buttons[0] = findViewById(R.id.btW0);
        _buttons[1] = findViewById(R.id.btW1);
        _buttons[2] = findViewById(R.id.btW2);
        _buttons[3] = findViewById(R.id.btW3);
        _buttons[4] = findViewById(R.id.btW4);
        _buttons[5] = findViewById(R.id.btW5);
        _buttons[6] = findViewById(R.id.btW6);
        _buttons[7] = findViewById(R.id.btW7);
        _buttons[8] = findViewById(R.id.btW8);
        _buttons[9] = findViewById(R.id.btW9);
        _buttons[10] = findViewById(R.id.btW10);
        _buttons[11] = findViewById(R.id.btW11);

        Random rnd = new Random();
        int qnt = rnd.nextInt(24);
        for (int i =0; i < qnt; i++){
            int s = rnd.nextInt(11);
            int d = rnd.nextInt(11);
            MatchWordTextView t = _buttons[s];
            _buttons[s] = _buttons[d];
            _buttons[d] = t;
        }

    }

    private void showNotes(){
        int pos = 0;
        for(NoteInfo i : _notes){
            _buttons[pos++].setAnkiNote(i, true);
            _buttons[pos++].setAnkiNote(i, false);
        }
    }

    private void checkMatch(MatchWordTextView tv){
        boolean isClearSel = false;

        for (MatchWordTextView t : _buttons)
            if (t.getIsSelect())
                isClearSel = true;

        tv.setIsSelect(!tv.getIsSelect());

        if (tv.getIsSelect()) {
            MatchWordTextView matchView = null;
            for (MatchWordTextView t : _buttons)
                if (t.getIsSelect() && !tv.equals(t)  && t.getNoteInfo().equals(tv.getNoteInfo()))
                    matchView = t;

            if (matchView != null){
                matchView.setVisibility(View.INVISIBLE);
                tv.setVisibility(View.INVISIBLE);
            }

            if (isClearSel)
                for (MatchWordTextView t : _buttons)
                    t.setIsSelect(false);

            boolean isEnd = true;
            for (MatchWordTextView t : _buttons)
                if (t.getVisibility() == View.VISIBLE)
                    isEnd = false;

            if (isEnd)
                end();

        }
    }

    public void OnWordClick(View v){
        checkMatch(((MatchWordTextView)v));
    }
}
