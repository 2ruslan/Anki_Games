package rock.ankigames;

public class CardInfo {

    public CardInfo(long id, String quest, String answer){
        _id = id;
        _questFiled = quest;
        _answerFiled = answer;
    }

    private long _id;
    public long getId(){
        return  _id;
    }



    private String _questFiled;


    private String _answerFiled;
}
