package rock.ankigames.Preferences;

import android.content.SharedPreferences;

public class PreferenceValue {
    String m_valueName;
    SharedPreferences m_settings;

    String m_defaultValueStr;
    int m_defaultValueNumInt;
    long m_defaultValueNumLong;
    boolean m_defaultValueBool;

    String m_currentValueStr;
    int m_currentValueNumInt;
    long m_currentValueNumLong;
    boolean m_currentValueBool;

    boolean m_isNoActivate = true;

    public PreferenceValue(SharedPreferences settings, String valueName, String defaultValue){
        m_settings = settings;
        m_valueName = valueName;
        m_defaultValueStr = defaultValue;
    }
    public PreferenceValue(SharedPreferences settings, String valueName, int defaultValue){
        m_settings = settings;
        m_valueName = valueName;
        m_defaultValueNumInt = defaultValue;
    }
    public PreferenceValue(SharedPreferences settings, String valueName, long defaultValue){
        m_settings = settings;
        m_valueName = valueName;
        m_defaultValueNumLong = defaultValue;
    }
    public PreferenceValue(SharedPreferences settings, String valueName, boolean defaultValue){
        m_settings = settings;
        m_valueName = valueName;
        m_defaultValueBool = defaultValue;
    }

    //region str
    public String getStr(){
        if (m_isNoActivate){
            try {
                m_currentValueStr = m_settings.getString(m_valueName, m_defaultValueStr);
                m_isNoActivate = false;
            }
            catch (Exception e)
            {
                m_currentValueStr = m_defaultValueStr;
            }
        }
        return m_currentValueStr;
    }

    public void setStr(String val){
        if(!val.equals(m_currentValueStr)) {
            SharedPreferences.Editor editor = m_settings.edit();
            editor.putString(m_valueName, val);
            editor.apply();
            m_currentValueStr = val;
            m_isNoActivate = false;
        }
    }
    //endregion str

    //region int
    public int getInt(){
        if (m_isNoActivate){
            try {
                m_currentValueNumInt = m_settings.getInt(m_valueName, m_defaultValueNumInt);
                m_isNoActivate = false;
            }
            catch (Exception e)
            {
                m_currentValueNumInt = m_defaultValueNumInt;
            }
        }
        return m_currentValueNumInt;
    }

    public void setInt(int val){
        if(m_currentValueNumInt != val) {
            SharedPreferences.Editor editor = m_settings.edit();
            editor.putInt(m_valueName, val);
            editor.apply();
            m_currentValueNumInt = val;
            m_isNoActivate = false;
        }
    }
    //endregion int

    //region long
    public long getLong(){
        if (m_isNoActivate){
            try {
                m_currentValueNumLong = m_settings.getLong(m_valueName, m_defaultValueNumLong);
                m_isNoActivate = false;
            }
            catch (Exception e)
            {
                m_currentValueNumLong = m_defaultValueNumLong;
            }
        }
        return m_currentValueNumLong;
    }

    public void setLong(long val){
        if(m_currentValueNumLong != val) {
            SharedPreferences.Editor editor = m_settings.edit();
            editor.putLong(m_valueName, val);
            editor.apply();
            m_currentValueNumLong = val;
            m_isNoActivate = false;
        }
    }
    //endregion long

    //region bool
    public boolean getBool(){
        if (m_isNoActivate){
            try {
                m_currentValueBool = m_settings.getBoolean(m_valueName, m_defaultValueBool);
                m_isNoActivate = false;
            }
            catch (Exception e)
            {
                m_currentValueBool = m_defaultValueBool;
            }
        }
        return m_currentValueBool;
    }

    public void setBool(boolean val){
        if(m_currentValueBool != val) {
            SharedPreferences.Editor editor = m_settings.edit();
            editor.putBoolean(m_valueName, val);
            editor.apply();
            m_currentValueBool= val;
            m_isNoActivate = false;
        }
    }
    //endregion bool

}
