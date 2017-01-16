package jimmified.jimmify.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import jimmified.jimmify.fragment.QueryListFragment;
import jimmified.jimmify.fragment.QueueFragment;

public class SaveSharedPreference {

    /* Database Keys */

    static final public String DB_ANSWER_VIEW_TYPE_FIELD = "answerViewType";
    static final public String DB_USE_TEST_QUERIES_FIELD = "useTestQueries";
    static final public String DB_TOKEN_FIELD = "token";

    /* Helper Functions */

    private static Context ctx = JimmifyApplication.getAppContext();

    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    static SharedPreferences getSharedPreferences() {
        return getSharedPreferences(ctx);
    }

    /* Get/Set Functions */

    public static void setToken(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(DB_TOKEN_FIELD, token);
        editor.apply();
    }

    public static void setToken(String token) {
        setToken(ctx, token);
    }

    public static String getToken(Context context) {
        return getSharedPreferences(context).getString(DB_TOKEN_FIELD, "");
    }

    public static String getToken() {
        return getToken(ctx);
    }

    /* Developer Options */

    public static QueueFragment.AnswerViewType getAnswerViewType() {
        return QueueFragment.AnswerViewType.decodeFromString(
                getSharedPreferences().getString(DB_ANSWER_VIEW_TYPE_FIELD, "Expandable Card"));
    }

    public static boolean getUseTestQueries() {
        return getSharedPreferences().getBoolean(DB_USE_TEST_QUERIES_FIELD, false);
    }
}
