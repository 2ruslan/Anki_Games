package rock.ankigames.Anki;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.ichi2.anki.FlashCardsContract;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import rock.ankigames.Common.Common;

public class AnkiHelper {

    private static Context mContext;

    private static ArrayList<String> _decks;
    private static Map<Long, ModelInfo> _models;
    private static Map<Integer, NoteInfo> _notes;
    private static Random _rand = new Random();

    private static final String _DELIMITER = "\u001F";

    public static void init(Context c){
        mContext = c;
        if (mContext != null) {
            initDecks();
            initModels();
        }
    }

    public static void OnDestroy(){
        _decks = null;
        _models = null;
    }

    public static ArrayList<String> getDeckNames(){
        return _decks;
    }

     private static void initDecks() {
        ContentResolver mResolver = mContext.getContentResolver();
        _decks = new ArrayList<>();

        Cursor decksCursor = mResolver.query(
                            FlashCardsContract.Deck.CONTENT_ALL_URI,
                    null,
                     null,
                  null,
                    null);
        if (decksCursor.moveToFirst()) {

            do {
                String deckName = decksCursor.getString(decksCursor.getColumnIndex(FlashCardsContract.Deck.DECK_NAME));
                _decks.add(deckName);
            } while (decksCursor.moveToNext());
        }

        decksCursor.close();

    }

    private static void initModels() {
        ContentResolver mResolver = mContext.getContentResolver();
        _models = new HashMap<>();

        Cursor decksCursor = mResolver.query(
                FlashCardsContract.Model.CONTENT_URI,
                null,
                null,
                null,
                null);
        if (decksCursor.moveToFirst()) {

            do {
                long modelId = decksCursor.getLong(decksCursor.getColumnIndex(FlashCardsContract.Model._ID));
                String fields = decksCursor.getString(decksCursor.getColumnIndex(FlashCardsContract.Model.FIELD_NAMES));

                ModelInfo mm = new ModelInfo(modelId, fields.split(_DELIMITER ) );

                setAQ2model(mm);

                _models.put(modelId, mm);

            } while (decksCursor.moveToNext());
        }

        decksCursor.close();
    }

    private static final String _back = "Back";
     private static final String _front = "Front";

    private static void setAQ2model(ModelInfo m) {
        ContentResolver mResolver = mContext.getContentResolver();

        Uri uri = Uri.withAppendedPath(FlashCardsContract.Model.CONTENT_URI, Long.toString(m.getId()));
        Uri cardsUri = Uri.withAppendedPath(uri, "templates");

        Cursor decksCursor = mResolver.query(
                cardsUri,
                null,
                null,
                null,
                null);
        if (decksCursor.moveToFirst()) {

            String a = decksCursor.getString(decksCursor.getColumnIndex(FlashCardsContract.CardTemplate.ANSWER_FORMAT));
            String q = decksCursor.getString(decksCursor.getColumnIndex(FlashCardsContract.CardTemplate.QUESTION_FORMAT));

            int pos, pose;

            if (a.contains( "{{" +  _back + "}}" )){
                m.setAnswerField(_back);
            }
            else{
                pos = a.indexOf("<hr id=answer>");

                if (pos == -1)
                    pos = a.indexOf("{{FrontSide}}");

                if (pos >= 0)
                    pos += 13;


                pos = a.indexOf("{{", pos);
                pose = a.indexOf("}}", pos);
                m.setAnswerField(a.substring(pos + 2, pose));

                if (m.getQuestFieldNum() == m.getAnswerFieldNum()){
                    pos = a.indexOf("{{", pose);
                    pose = a.indexOf("}}", pos);
                    m.setAnswerField(a.substring(pos + 2, pose));
                }
            }


            if (q.contains("{{" + _front+ "}}")){
                m.setQuestField(_front);
            }
            else {
                pos = q.indexOf("{{");
                pose = q.indexOf("}}", pos);
                m.setQuestField(q.substring(pos + 2, pose));
            }
        }

        decksCursor.close();
    }

    private static String _deckName = "!@#$%^&";
    public static void initNotes(String deckName) {

        if(deckName == null || deckName.equals("") || deckName.equals(Common._NO_VALUE))
            return;

        if (_deckName.equals(deckName))
            return;

        ContentResolver mResolver = mContext.getContentResolver();

        _notes = new HashMap<>();
        int pos = 0;

        Cursor decksCursor = mResolver.query(
                FlashCardsContract.Note.CONTENT_URI,
                null,
                "deck:\"" + deckName + "\"",
                null,
                null);
        if (decksCursor.moveToFirst()) {

            do {

                long mid = decksCursor.getLong(decksCursor.getColumnIndex(FlashCardsContract.Note.MID));
                String fields = decksCursor.getString(decksCursor.getColumnIndex(FlashCardsContract.Note.FLDS));
                String[] flds = fields.split(_DELIMITER);

                ModelInfo m = _models.get(mid);

                NoteInfo n = new NoteInfo(
                        getCleanWord(flds[m.getQuestFieldNum()]),
                        getCleanWord(flds[m.getAnswerFieldNum()])
                );

                _notes.put(pos++, n);

            } while (decksCursor.moveToNext());
        }

        decksCursor.close();
    }

    private static String getCleanWord(String s){
        String result =  Jsoup.parse(s).text();

        return result;
    }

    public static ArrayList<NoteInfo> getRandomNotes(int qnt){
        ArrayList<NoteInfo> words = new ArrayList<>();

        int[] rnds = new int[qnt];

        if (qnt < _notes.size()) {
            for (int i = 0; i < qnt; i++) {
                boolean flg;
                do {
                    int r = _rand.nextInt(_notes.size());

                    flg = true;
                    for (int k = 0; k < i; k++)
                        if (rnds[k] == r)
                            flg = false;

                    if (flg)
                        rnds[i] = r;

                } while (!flg);
            }
            for (int i = 0; i < qnt; i++)
                words.add(_notes.get(rnds[i]));
        }
        else
        {
            for (int i = 0; i < _notes.size(); i++)
                words.add(_notes.get(i));
            for (int i = _notes.size(); i< qnt; i++)
                words.add(null);
        }

        return words;
    }

    public static int getCountNotes(){
        return _notes.size();
    }

    public static NoteInfo getNoteByNum(int p){
        return _notes.get(p);
    }

    public static ArrayList<NoteInfo> getAllNotes(){
        ArrayList<NoteInfo> res = new ArrayList<NoteInfo>();
        int qnt = _notes.size() - 1;
        for (int i = 0; i < qnt; i++)
            res.add(_notes.get(i));

        return res;
     }
}
