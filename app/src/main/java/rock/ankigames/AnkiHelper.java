package rock.ankigames;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.ichi2.anki.FlashCardsContract;

import java.util.ArrayList;

public class AnkiHelper {

    Context mContext;

    static final String _DELIMITER = "\u001F";

    public AnkiHelper(Context c){
        mContext = c;
    }

    public ArrayList<DeckInfo> GetDeckList() {
        ContentResolver mResolver = mContext.getContentResolver();
        ArrayList d = new ArrayList<DeckInfo>();

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

                d.add(new DeckInfo(deckName, deckID));

            } while (decksCursor.moveToNext());
        }

        decksCursor.close();

        return d;
    }

    public ArrayList<ModelInfo> GetModelList() {
        ContentResolver mResolver = mContext.getContentResolver();
        ArrayList m = new ArrayList<ModelInfo>();

        Cursor decksCursor = mResolver.query(
                FlashCardsContract.Model.CONTENT_URI,
                null,
                null,
                null,
                null);
        if (decksCursor.moveToFirst()) {

            do {
                long modelId = decksCursor.getLong(decksCursor.getColumnIndex(FlashCardsContract.Model._ID));
                String modelName = decksCursor.getString(decksCursor.getColumnIndex(FlashCardsContract.Model.NAME));
                String filds = decksCursor.getString(decksCursor.getColumnIndex(FlashCardsContract.Model.FIELD_NAMES));

                ModelInfo mm = new ModelInfo(modelId, modelName, filds.split(_DELIMITER ) );

                setAQFields(modelId, mm);

                m.add(mm);

            } while (decksCursor.moveToNext());
        }

        decksCursor.close();

        return m;
    }

    public void setAQFields(long id, ModelInfo m) {
        ContentResolver mResolver = mContext.getContentResolver();

        Uri uri = Uri.withAppendedPath(FlashCardsContract.Model.CONTENT_URI, Long.toString(id));
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



}
