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

                //String qs = decksCursor.getString(decksCursor.getColumnIndex(FlashCardsContract.Deck.));

                d.add(new DeckInfo(deckName, deckID));

            } while (decksCursor.moveToNext());
        }

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



                m.add(new ModelInfo(modelId, modelName, filds.split(_DELIMITER ) ));

            } while (decksCursor.moveToNext());
        }

        return m;
    }

    public ArrayList<CardInfo> GetCardlList() {
        ContentResolver mResolver = mContext.getContentResolver();
        ArrayList c = new ArrayList<CardInfo>();

        Cursor decksCursor = mResolver.query(
                FlashCardsContract.CardTemplate.CONTENT_TYPE,
                null,
                null,
                null,
                null);
        if (decksCursor.moveToFirst()) {

            do {
                long modelId = decksCursor.getLong(decksCursor.getColumnIndex(FlashCardsContract.Model._ID));
                String filds = decksCursor.getString(decksCursor.getColumnIndex(FlashCardsContract.Model.FIELD_NAMES));



                c.add(new ModelInfo(modelId, modelName, filds.split(_DELIMITER ) ));

            } while (decksCursor.moveToNext());
        }

        return c;
    }



}
