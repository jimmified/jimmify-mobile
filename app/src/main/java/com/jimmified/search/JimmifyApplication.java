package com.jimmified.search;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jimmified.search.settings.SaveSharedPreference;
import com.jimmified.search.request.JimmifyAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// TODO: Refactor static members
public class JimmifyApplication extends Application {

    // Context for SharedPreferences
    private static Context context;

    // Toasts
    private static Toast serverConnectionToast;

    // API for retrofit
    private static JimmifyAPI jimmifyAPI;
    private static String jimmifyURL = "https://jimmified.com/api/";

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        serverConnectionToast = Toast.makeText(context, "Could not connect to servers", Toast.LENGTH_LONG);

        setJimmifyURL(jimmifyURL);
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

        OkHttpClient client = null;
        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug")) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        }

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(jimmifyURL)
                .addConverterFactory(GsonConverterFactory.create());

        if (client != null)
            retrofitBuilder.client(client);

        jimmifyAPI = retrofitBuilder.build().create(JimmifyAPI.class);
    }

    public static JimmifyAPI getJimmifyAPI() {
        return JimmifyApplication.jimmifyAPI;
    }

    public static boolean isLoggedIn() { return !("".equals(SaveSharedPreference.getToken())); }
}

