package jimmified.jimmify.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import jimmified.jimmify.request.JimmifyAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JimmifyApplication extends Application {

    // Context for SharedPreferences
    private static Context context;

    // Toasts
    private static Toast serverConnectionToast;

    // Api for retrofit
    private static JimmifyAPI jimmifyAPI;
    private static String jimmifyURL = "http://shibboleth.student.rit.edu/";

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        serverConnectionToast = Toast.makeText(context, "Could not connect to servers", Toast.LENGTH_LONG);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(jimmifyURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jimmifyAPI = retrofit.create(JimmifyAPI.class);
    }

    public static Context getAppContext() {
        return JimmifyApplication.context;
    }

    public static void showToast(String string) {
        Toast.makeText(getAppContext(), string, Toast.LENGTH_LONG).show();
    }

    public static void showServerConnectionToast() {
        if (JimmifyApplication.serverConnectionToast.getView().isShown())
            JimmifyApplication.serverConnectionToast.cancel();
        JimmifyApplication.serverConnectionToast.show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getJimmifyURL() {
        return JimmifyApplication.jimmifyURL;
    }

    public static void setJimmifyURL(String url) {
        JimmifyApplication.jimmifyURL = url;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(jimmifyURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jimmifyAPI = retrofit.create(JimmifyAPI.class);
    }

    public static JimmifyAPI getJimmifyAPI() {
        return JimmifyApplication.jimmifyAPI;
    }
}

