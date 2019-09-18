package rock.ankigames.Preferences;

public class HM {

    public static final int _NO_VALUE = -1;

    public int HH = _NO_VALUE;
    public int MM = _NO_VALUE;

    public HM(){
    }

    public HM(int hh, int mm){
        HH = hh;
        MM = mm;
    }

    public HM(String hm){

        if (hm != null){
            String[] lines = hm.split(":");

            if (lines.length == 2){
                try {
                    HH = Integer.valueOf(lines[0]);
                    if (HH < 0 || HH > 23)
                        HH = _NO_VALUE;
                }catch (Exception e){}

                try {
                    MM = Integer.valueOf(lines[1]);
                    if (MM < 0 || MM > 59)
                        MM = _NO_VALUE;
                }catch (Exception e){}
            }

        }
    }

    public String GetStr(){
        if (HH != _NO_VALUE && MM != _NO_VALUE) {
            String hh = HH < 10 ? "0" + String.valueOf(HH) : String.valueOf(HH);
            String mm = MM < 10 ? "0" + String.valueOf(MM) : String.valueOf(MM);
            return String.format("%s:%s", hh, mm);
        }
        else
            return "-";
    }
}
