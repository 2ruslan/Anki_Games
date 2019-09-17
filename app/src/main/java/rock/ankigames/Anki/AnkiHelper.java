package rock.ankigames.Anki;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.ichi2.anki.FlashCardsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

 class AnkiHelper {

    private Context mContext;

    private static ArrayList<DeckInfo> _decks;
    private static Map<Long, ModelInfo> _models;
    private static Map<Integer, NoteInfo> _notes;
    private Random _rand = new Random();

    private static final String _DELIMITER = "\u001F";

    public AnkiHelper(Context c){
        mContext = c;
        initDecks();
        initModels();

        initNotes("English with Mary");

        getRandomNotes(7);
    }

    public void OnDestroy(){
        _decks = null;
        _models = null;
    }

    private void initDecks() {
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
                long deckID = decksCursor.getLong(decksCursor.getColumnIndex(FlashCardsContract.Deck.DECK_ID));
                String deckName = decksCursor.getString(decksCursor.getColumnIndex(FlashCardsContract.Deck.DECK_NAME));

                _decks.add(new DeckInfo(deckName, deckID));

            } while (decksCursor.moveToNext());
        }

        decksCursor.close();

    }

    public void initModels() {
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

    private void setAQ2model(ModelInfo m) {
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

            int pos = a.indexOf("{{FrontSide}}");
            if (pos >= 0)
                pos += 13;
            pos = a.indexOf("{{", pos);
            int pose = a.indexOf("}}", pos);
            m.setAnswerField(a.substring(pos + 2, pose));


            pos = q.indexOf("{{");
            pose = q.indexOf("}}", pos);
            m.setQuestField(q.substring(pos + 2, pose));

        }

        decksCursor.close();
    }

    private void initNotes(String deckName) {
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

    private String getCleanWord(String s){
        return s
                .replace("<b>", "")
                .replace("</b>", "");
    }

    public ArrayList<NoteInfo> getRandomNotes(int qnt){
        ArrayList<NoteInfo> words = new ArrayList<>();

        int[] rnds = new int[qnt];
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

            }while (!flg);
        }

        for (int i = 0; i < qnt; i++)
            words.add(_notes.get(rnds[i]));

        return words;
    }

}
