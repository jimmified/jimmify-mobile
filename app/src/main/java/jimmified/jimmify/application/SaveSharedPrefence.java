package jimmified.jimmify.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import jimmified.jimmify.fragment.QueryListFragment;
import jimmified.jimmify.fragment.QueueFragment;

public class SaveSharedPrefence {
    static final public String DB_ANSWER_VIEW_TYPE_FIELD = "answerViewType";
    static final public String DB_USE_TEST_QUERIES_FIELD = "useTestQueries";


    private static Context ctx = JimmifyApplication.getAppContext();

    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    static SharedPreferences getSharedPreferences() {
        return getSharedPreferences(ctx);
    }

    public static QueueFragment.AnswerViewType getAnswerViewType() {
        return QueueFragment.AnswerViewType.decodeFromString(
                getSharedPreferences().getString(DB_ANSWER_VIEW_TYPE_FIELD, "Expandable Card"));
    }

    public static boolean getUseTestQueries() {
        return getSharedPreferences().getBoolean(DB_USE_TEST_QUERIES_FIELD, false);
    }
}
