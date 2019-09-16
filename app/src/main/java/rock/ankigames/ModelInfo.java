package rock.ankigames;

public class ModelInfo {

    public ModelInfo(long id, String name, String[] fileds ){
        _id = id;
        _name = name;
        _fields = fileds;
    }

    private long _id;
    public long getId(){
        return  _id;
    }


    private  String _name;
    public String getName(){
        return _name;
    }



    private String[] _fields;

}
