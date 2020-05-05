package ac.id.polinema.delaundry;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;

import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ac.id.polinema.delaundry.helper.ApiHelper;

import static androidx.navigation.Navigation.findNavController;

public class Utils {

    private static String TAG = Utils.class.getSimpleName();

    public static void safeNavigate(View view, NavDirections directions) {
        int resId = directions.getActionId();
        NavController navController = findNavController(view);
        NavDestination currentDestination = navController.getCurrentDestination();
        NavAction action = (currentDestination != null) ?
                currentDestination.getAction(resId) : navController.getGraph().getAction(resId);
        if (action != null) {
            if (currentDestination.getId() != action.getDestinationId()) {
                navController.navigate(directions);
            }
        }
    }

    public static boolean isConnected(Context context) {
        try {
            Future<Boolean> newThread =  Executors.newSingleThreadExecutor().submit(() -> {
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                URL myUrl = new URL(ApiHelper.URL);

                connectivityManager.getActiveNetworkInfo();
                URLConnection connection = myUrl.openConnection();
                connection.setConnectTimeout(3000);
                connection.connect();
                Log.i(TAG, "isConnected: status=TRUE");
                return true;
            });

            return newThread.get();
        } catch (ExecutionException | InterruptedException | NullPointerException e) {
            Log.i(TAG, "isConnected: status=FALSE");
            return false;
        }
    }

    public static void showMessage(Context context, String message) {
        View view = ((Activity) context).findViewById(android.R.id.content);
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .show();
    }

    public static String getDateTimeReadable(String timestamps, Integer index) {
        String[] split = timestamps.split("T");
        String date = split[0];
        String time = split[1].split("\\.")[0];
        if (index != null) {
            return index == 0 ? date : time;
        }

        return date + " " + time;
    }

    public static String getDateReadable(String timestamp) {
        if (timestamp.isEmpty()) return timestamp;
        return getDateTimeReadable(timestamp, 0);
    }

    public static String getTimeReadable(String timestamp) {
        if (timestamp.isEmpty()) return timestamp;
        return getDateTimeReadable(timestamp, 1);
    }
}
