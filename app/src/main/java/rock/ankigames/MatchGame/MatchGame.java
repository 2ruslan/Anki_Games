package rock.ankigames.MatchGame;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import rock.ankigames.Anki.AnkiHelper;
import rock.ankigames.Anki.NoteInfo;

import rock.ankigames.Helper;
import rock.ankigames.Preferences.PreferencesHelper;
import rock.ankigames.R;


public class MatchGame extends Activity {

    ArrayList<NoteInfo> _notes;
    long _startTime;

    MatchWordTextView[] _cardViewElements = new MatchWordTextView[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_game);

        start();
    }

    private void start(){
        _notes = AnkiHelper.getRandomNotes(6);

        initCardViewElements();

        showNotes();

        _startTime = Calendar.getInstance().getTimeInMillis();
    }

    private void end(){
        int result = (int)(Calendar.getInstance().getTimeInMillis() - _startTime);
        int best = PreferencesHelper.getRecordMatch();

        Helper.endGame(this, Helper.GameType.match, result, best);

        if (best < result)
            PreferencesHelper.setRecordMatch(result);
    }

    private void initCardViewElements(){
        _cardViewElements[0] = findViewById(R.id.btW0);
        _cardViewElements[1] = findViewById(R.id.btW1);
        _cardViewElements[2] = findViewById(R.id.btW2);
        _cardViewElements[3] = findViewById(R.id.btW3);
        _cardViewElements[4] = findViewById(R.id.btW4);
        _cardViewElements[5] = findViewById(R.id.btW5);
        _cardViewElements[6] = findViewById(R.id.btW6);
        _cardViewElements[7] = findViewById(R.id.btW7);
        _cardViewElements[8] = findViewById(R.id.btW8);
        _cardViewElements[9] = findViewById(R.id.btW9);
        _cardViewElements[10] = findViewById(R.id.btW10);
        _cardViewElements[11] = findViewById(R.id.btW11);

        Random rnd = new Random();
        int qnt = rnd.nextInt(24);
        for (int i =0; i < qnt; i++){
            int s = rnd.nextInt(11);
            int d = rnd.nextInt(11);
            MatchWordTextView t = _cardViewElements[s];
            _cardViewElements[s] = _cardViewElements[d];
            _cardViewElements[d] = t;
        }

    }

    private void showNotes(){
        int pos = 0;
        for(NoteInfo i : _notes){
            _cardViewElements[pos++].setAnkiNote(i, true);
            _cardViewElements[pos++].setAnkiNote(i, false);
        }
    }

    private void checkMatch(MatchWordTextView tv){
        boolean isClearSel = false;

        for (MatchWordTextView t : _cardViewElements)
            if (t.getIsSelect())
                isClearSel = true;

        tv.setIsSelect(!tv.getIsSelect());

        if (tv.getIsSelect()) {
            MatchWordTextView matchView = null;
            for (MatchWordTextView t : _cardViewElements)
                if (t.getIsSelect() && !tv.equals(t)  && t.getNoteInfo().equals(tv.getNoteInfo()))
                    matchView = t;

            if (matchView != null){
                matchView.setVisibility(View.INVISIBLE);
                tv.setVisibility(View.INVISIBLE);
            }

            if (isClearSel)
                for (MatchWordTextView t : _cardViewElements)
                    t.setIsSelect(false);

            boolean isEnd = true;
            for (MatchWordTextView t : _cardViewElements)
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
