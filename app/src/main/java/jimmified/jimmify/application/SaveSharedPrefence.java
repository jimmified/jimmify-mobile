package jimmified.jimmify.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

public class SaveSharedPrefence {

    static Context ctx = JimmifyApplication.getAppContext();

    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    static SharedPreferences getSharedPreferences() {
        return getSharedPreferences(ctx);
    }


}
