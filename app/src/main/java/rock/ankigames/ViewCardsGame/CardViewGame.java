package rock.ankigames.ViewCardsGame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import rock.ankigames.Anki.AnkiHelper;
import rock.ankigames.Anki.NoteInfo;
import rock.ankigames.Common.OnSwipeTouchListener;
import rock.ankigames.Helper;
import rock.ankigames.Preferences.PreferencesHelper;
import rock.ankigames.R;

public class CardViewGame extends Activity {

    int _count;
    int _pos;
    NoteInfo _note;
    TextView _view;
    boolean _isReverse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_game);

        _view = findViewById(R.id.tvViewCard);

        _view.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();

                _pos --;
                showCard();
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();

                _pos++;
                showCard();
            }
            @Override
            public void onTap() {
                showReverse();
            }

        });

        start();
    }

    private void start(){
        _count = AnkiHelper.getCountNotes();
        _pos = _count - 1;
        showCard();
    }

    private void showCard(){
        if (_pos < 0)
            _pos = 0;
        if (_pos >= _count)
            _pos = _count - 1;

        _note = AnkiHelper.getNoteByNum(_pos);
        _isReverse = true;
        showReverse();
    }

    private void showReverse(){
        _isReverse = !_isReverse;
        _view.setText(_isReverse ? _note.getAnswer() : _note.getQuestion() );
    }

    private void end(){
        Helper.endGame(this, Helper.GameType.cardView);
    }

    public void OnClickEnd(View v){
        end();
    }
}
