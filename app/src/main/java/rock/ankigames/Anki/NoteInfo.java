package rock.ankigames.Anki;

public class NoteInfo {
    public  NoteInfo(String q, String a){
        _question = q;
        _answear = a;
    }

    private String _question;
    public String getQuestion(){
        return _question;
    }

    private String _answear;
    public String getAnswer(){
        return _answear;
    }
}
