package rock.ankigames.ViewCardsGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import rock.ankigames.Anki.AnkiHelper;
import rock.ankigames.Anki.NoteInfo;
import rock.ankigames.Common.Common;
import rock.ankigames.Common.OnSwipeTouchListener;
import rock.ankigames.Preferences.PreferencesHelper;
import rock.ankigames.R;

public class CardViewGame extends Activity {

    public static final int _REQUEST_POS = 123;


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
            public void onSwipeTop(){
                finish();
            }
            @Override
            public void onSwipeBottom(){
                showList();
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

        _pos = PreferencesHelper.getNoteViewPos();

        if (_pos == Common._POS_END || _pos > _count)
            _pos = _count - 1;

        showCard();
    }

    private void showCard(){
        if (_pos < 0)
            _pos = 0;
        if (_pos >= _count)
            _pos = _count - 1;

        PreferencesHelper.setNoteViewPos(_pos);

        _note = AnkiHelper.getNoteByNum(_pos);
        _isReverse = true;
        showReverse();
    }

    private void showReverse(){
        _isReverse = !_isReverse;
        _view.setText(_isReverse ? _note.getAnswer() : _note.getQuestion() );
    }


    private void showList(){
        startActivityForResult(new Intent(this, CardViewListGame.class), _REQUEST_POS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == _REQUEST_POS) {
            _pos = PreferencesHelper.getNoteViewPos();
            showCard();
        }


    }
}
