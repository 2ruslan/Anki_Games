package rock.ankigames.ViewCardsGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import rock.ankigames.Anki.AnkiHelper;
import rock.ankigames.Helper;
import rock.ankigames.Preferences.PreferencesHelper;
import rock.ankigames.R;

public class CardViewListGame extends AppCompatActivity {

    NoteInfoAdapter _infoAdapter;
    ListView _notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_list_game);

        _notesList = findViewById(R.id.lvNotesList);

        start();
    }


    void start() {
        _infoAdapter = new NoteInfoAdapter(this, R.layout.note_info_list_item, AnkiHelper.getAllNotes());
        if (_infoAdapter != null) {
            _notesList.setAdapter(_infoAdapter);
            _notesList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PreferencesHelper.setNoteViewPos(position);
                    Intent intent = new Intent();
                    setResult(CardViewGame._REQUEST_POS, intent);
                    finish();
                }
            });

        }
    }
}
