package ac.id.polinema.delaundry;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.stetho.Stetho;

import ac.id.polinema.delaundry.model.UserModel;

public class App extends Application {

    private static UserModel userModel;
    private static SharedPreferences sharedPreferences;

    public static String IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH";
    public static String NO_HANDPHONE = "NO_HANDPHONE";
    public static String KEY_ID_USER = "KEY_ID_USER";

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public static String getIdUser() {
        return sharedPreferences.getString(KEY_ID_USER, "");
    }

    public static String noHandphone() {
        return sharedPreferences.getString(NO_HANDPHONE, "");
    }

    public static Boolean isLogIn() {
        String noHandphone = sharedPreferences.getString(NO_HANDPHONE, "");
        return !noHandphone.isEmpty();
    }

    public static Boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public static void setSharedPreferences(String key, Object obj) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj).apply();
        } else if (obj instanceof String) {
            editor.putString(key, (String) obj).apply();
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj).apply();
        }
    }

    public static void setUserModel(UserModel userModel) {
        App.userModel = userModel;
    }

    public static UserModel getUserModel() {
        return userModel;
    }
}
