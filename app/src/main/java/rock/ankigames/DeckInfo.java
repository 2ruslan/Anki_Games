package rock.ankigames;

public class DeckInfo {

    public DeckInfo(String n, long id){
        _name = n;
        _id = id;
       // _questFiled = questField;
       // _answerFiled = answerField;
    }


    private long _id;
    public long getId(){
        return  _id;
    }


    private  String _name;
    public String getName(){
        return _name;
    }


    private String _questFiled;


    private String _answerFiled;


}
