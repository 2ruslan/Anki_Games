package rock.ankigames;

import android.content.Context;

import java.util.ArrayList;

public  class  StaticHolder {
    private  static AnkiHelper _AnkiHelper;
    private  static  Context _Context;

    private static ArrayList<DeckInfo> _deckList;
    private static ArrayList<ModelInfo> _modelList;



    public static void init(Context c){
        _Context = c;
        _AnkiHelper = new AnkiHelper(c);
        getDeckInfo();
        getModelInfo();
    }


    private static void getDeckInfo(){
        _deckList = _AnkiHelper.GetDeckList();
    }

    private static void getModelInfo(){
        _modelList = _AnkiHelper.GetModelList();
    }

}
