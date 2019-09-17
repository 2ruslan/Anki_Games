package rock.ankigames.Anki;

import android.content.Context;

import rock.ankigames.Anki.AnkiHelper;

public  class  StaticHolder {
    private  static AnkiHelper _AnkiHelper;
    private  static  Context _Context;


    public static void init(Context c){
        _Context = c;
        _AnkiHelper = new AnkiHelper(c);
    }

    public void OnDestroy(){
        _AnkiHelper.OnDestroy();
        _AnkiHelper = null;
    }

}
