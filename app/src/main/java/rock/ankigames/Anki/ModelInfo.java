package rock.ankigames.Anki;

public class ModelInfo {

    public ModelInfo(long id, String[] fieleds ){
        _id = id;
        _fields = fieleds;
    }

    private long _id;
    public long getId(){
        return  _id;
    }

    private String[] _fields;

    private String _answerField;
    private int _answerFieldNum;
    public void setAnswerField(String a){
        _answerField = a;

        for (int i = 0; i < _fields.length; i++){
            if(_fields[i].equals(_answerField))
                _answerFieldNum = i;
        }
    }

    public int getAnswerFieldNum(){
        return _answerFieldNum;
    }

    private String _questField;
    private int _questFieldNum;
    public void setQuestField(String q){
        _questField = q;

        for (int i = 0; i < _fields.length; i++){
            if(_fields[i].equals(_questField))
                _questFieldNum = i;
        }
    }

    public int getQuestFieldNum(){
        return _questFieldNum;
    }
}
