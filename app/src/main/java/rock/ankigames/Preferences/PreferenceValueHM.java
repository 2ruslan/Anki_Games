package rock.ankigames.Preferences;


import android.content.SharedPreferences;

public class PreferenceValueHM {
    PreferenceValue _hh;
    PreferenceValue _mm;
    boolean _isNoActivate = true;
    HM _currentValueHM;


    public PreferenceValueHM(SharedPreferences settings, String valueName ){
        _hh = new PreferenceValue(settings, valueName + "_HH", HM._NO_VALUE);
        _mm = new PreferenceValue(settings, valueName + "_MM", HM._NO_VALUE);
    }

    public HM getHM(){
        if (_isNoActivate){
            try {
                _currentValueHM = new HM(_hh.getInt(), _mm.getInt());
                _isNoActivate = false;
            }
            catch (Exception e)
            {
                _currentValueHM = new HM();
            }
        }
        return _currentValueHM;
    }

    public void setHM(HM val){
        _hh.setInt(val.HH);
        _mm.setInt(val.MM);
        _currentValueHM = val;
        _isNoActivate = false;
    }
}
