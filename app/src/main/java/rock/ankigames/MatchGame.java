package rock.ankigames;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Random;

import rock.ankigames.Anki.AnkiHelper;
import rock.ankigames.Anki.NoteInfo;

public class MatchGame extends AppCompatActivity {

    ArrayList<NoteInfo> _notes;

    TextView[] buttons = new TextView[12];

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

    private void initBottoms(){
        buttons[0] = findViewById(R.id.btW0);
        buttons[1] = findViewById(R.id.btW1);
        buttons[2] = findViewById(R.id.btW2);
        buttons[3] = findViewById(R.id.btW3);
        buttons[4] = findViewById(R.id.btW4);
        buttons[5] = findViewById(R.id.btW5);
        buttons[6] = findViewById(R.id.btW6);
        buttons[7] = findViewById(R.id.btW7);
        buttons[8] = findViewById(R.id.btW8);
        buttons[9] = findViewById(R.id.btW9);
        buttons[10] = findViewById(R.id.btW10);
        buttons[11] = findViewById(R.id.btW11);

        Random rnd = new Random();
        int qnt = rnd.nextInt(24);
        for (int i =0; i < qnt; i++){
            int s = rnd.nextInt(11);
            int d = rnd.nextInt(11);
            TextView t = buttons[s];
            buttons[s] = buttons[d];
            buttons[d] = t;
        }

    }

    private void showNotes(){
        int pos = 0;
        for(NoteInfo i : _notes){
            buttons[pos].setText(i.getAnswer());
            pos++;
            buttons[pos].setText(i.getQuestion());
            pos++;
        }
    }
}
