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
import rock.ankigames.Helper;
import rock.ankigames.R;

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
            if (!initDecks())
                return;
            if (!initModels())
                return;
        }
    }

    public static void OnDestroy(){
        _decks = null;
        _models = null;
    }

    public static ArrayList<String> getDeckNames(){
        return _decks;
    }

    private static Cursor getDeckCursor(ContentResolver resolver){
        return resolver.query(
                FlashCardsContract.Deck.CONTENT_ALL_URI,
                null,
                null,
                null,
                null);
    }

     private static boolean initDecks() {
        ContentResolver resolver = mContext.getContentResolver();

        _decks = new ArrayList<>();

         if (resolver == null)
             return false;

        Cursor decksCursor = null;
        try {
            decksCursor = getDeckCursor (resolver);
        }
        catch (Exception e0){
            try{
                Thread.sleep(1200);
                decksCursor = getDeckCursor (resolver);
            }
            catch(Exception e1) {
                Helper.showDialog(mContext, R.string.anki_power_mangment);
            }
        }

        if (decksCursor == null)
            return false;

        if (decksCursor.moveToFirst()) {
            do {
                String deckName = decksCursor.getString(decksCursor.getColumnIndex(FlashCardsContract.Deck.DECK_NAME));
                _decks.add(deckName);
            } while (decksCursor.moveToNext());
        }

        decksCursor.close();

        return true;
    }

    private static Cursor getModelCursor(ContentResolver resolver){
        return resolver.query(
                FlashCardsContract.Model.CONTENT_URI,
                new String[0],
                "",
                new String[0],
                "");
    }
    private static boolean initModels() {
        ContentResolver resolver = mContext.getContentResolver();
        _models = new HashMap<>();

        if (resolver == null)
            return false;

        Cursor cursor = null;
        try {
            cursor = getModelCursor(resolver);
        }catch (Exception e){
            try{
                Thread.sleep(1200);
                cursor = getModelCursor(resolver);
            }
            catch (Exception e2) {
                Helper.showDialog(mContext, R.string.anki_power_mangment);
            }
        }

        if (cursor == null)
            return false;

        if (cursor.moveToFirst()) {
            do {
                long modelId = cursor.getLong(cursor.getColumnIndex(FlashCardsContract.Model._ID));
                String fields = cursor.getString(cursor.getColumnIndex(FlashCardsContract.Model.FIELD_NAMES));

                ModelInfo mm = new ModelInfo(modelId, fields.split(_DELIMITER ) );

                setAQ2model(mm);

                _models.put(modelId, mm);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return true;
    }

    private static final String _back = "Back";
     private static final String _front = "Front";

    private static void setAQ2model(ModelInfo m) {
        try {
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

                if (a.contains("{{" + _back + "}}")) {
                    m.setAnswerField(_back);
                } else {
                    pos = a.indexOf("<hr id=answer>");

                    if (pos == -1)
                        pos = a.indexOf("{{FrontSide}}");

                    if (pos >= 0)
                        pos += 13;


                    pos = a.indexOf("{{", pos);
                    pose = a.indexOf("}}", pos);
                    m.setAnswerField(a.substring(pos + 2, pose));

                    if (m.getQuestFieldNum() == m.getAnswerFieldNum()) {
                        pos = a.indexOf("{{", pose);
                        pose = a.indexOf("}}", pos);
                        m.setAnswerField(a.substring(pos + 2, pose));
                    }
                }


                if (q.contains("{{" + _front + "}}")) {
                    m.setQuestField(_front);
                } else {
                    pos = q.indexOf("{{");
                    pose = q.indexOf("}}", pos);
                    m.setQuestField(q.substring(pos + 2, pose));
                }
            }

            decksCursor.close();
        }
        catch (Exception  e){
            Helper.showDialog(mContext, R.string.bad_card_format);
        }
    }

    private static String _deckName = "!@#$%^&";
    private static Cursor getNotesCursor(ContentResolver resolver, String deckName){
        return resolver.query(
                FlashCardsContract.Note.CONTENT_URI,
                new String[0],
                "deck:\"" + deckName + "\"",
                new String[0],
                "");
    }
    public static boolean initNotes(String deckName) {

        if(deckName == null || deckName.equals("") || deckName.equals(Common._NO_VALUE))
            return false;

        if (_deckName.equals(deckName))
            return true;

        ContentResolver resolver = mContext.getContentResolver();

        _notes = new HashMap<>();
        int pos = 0;

        Cursor cursor = null;
        try {
            cursor = getNotesCursor(resolver, deckName);
        }
        catch (Exception e){
            try{
                Thread.sleep(1200);
                cursor = getNotesCursor(resolver, deckName);
            }
            catch (Exception e2) {
                Helper.showDialog(mContext, R.string.anki_power_mangment);
            }
        }

        if (cursor == null)
            return false;

        if (cursor.moveToFirst()) {
            do {
                long mid = cursor.getLong(cursor.getColumnIndex(FlashCardsContract.Note.MID));
                String fields = cursor.getString(cursor.getColumnIndex(FlashCardsContract.Note.FLDS));
                String[] flds = fields.split(_DELIMITER);

                ModelInfo m = _models.get(mid);

                NoteInfo n = new NoteInfo(
                        getCleanWord(flds[m.getQuestFieldNum()]),
                        getCleanWord(flds[m.getAnswerFieldNum()])
                );

                _notes.put(pos++, n);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return true;
    }

    private static String getCleanWord(String s){
        String result =  Jsoup.parse(s).text();

        if (result !=null) {
            int start = result.indexOf("[sound:");
            if (start > 0) {
                result = result.substring(0, start - 1);
            }
        }

        return result;
    }

    public static ArrayList<NoteInfo> getRandomNotes(int qnt){
        ArrayList<NoteInfo> words = new ArrayList<>();

        if (getCountNotes() == 0)
            return words;

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
        return _notes == null ? 0 : _notes.size();
    }

    public static NoteInfo getNoteByNum(int p){
        return _notes.get(p);
    }

    public static ArrayList<NoteInfo> getAllNotes(){
        ArrayList<NoteInfo> res = new ArrayList<NoteInfo>();

        int qnt = getCountNotes() - 1;
        for (int i = 0; i < qnt; i++)
            res.add(_notes.get(i));

        return res;
     }
}
