package task.marami.Shubhaprada.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class StoragePrefer {
    private final SharedPreferences mPrefs;

    public StoragePrefer(Context context) {
        this.mPrefs = context.getSharedPreferences("MySprf", Context.MODE_PRIVATE);
    }

    public void sprStoreBoolean(String KeyName,boolean value)
    {
        mPrefs.edit().putBoolean(KeyName, value).commit();
    }
    public boolean getSprBoolean(String KeyName)
    {
        return  mPrefs.getBoolean(KeyName, false);
    }

    public void sprStoreString(String KeyName,String value)
    {
       mPrefs.edit().putString(KeyName, value).commit();
    }
    public String getSprString(String KeyName)
    {
        return mPrefs.getString(KeyName, "");
    }
}
