package ac.id.polinema.delaundry.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ac.id.polinema.delaundry.api.ApiService;
import ac.id.polinema.delaundry.api.Response;
import ac.id.polinema.delaundry.database.AppDatabase;
import ac.id.polinema.delaundry.helper.ApiHelper;
import ac.id.polinema.delaundry.helper.DbHelper;
import ac.id.polinema.delaundry.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;

public class Repository {

    protected static final String TAG = Repository.class.getSimpleName();

    protected Context context;
    protected ApiService service;
    protected AppDatabase database;
    protected ExecutorService executorService;

    protected Repository(Context ctx) {
        context = ctx;
        service = ApiHelper.getInstance();
        database = DbHelper.instance(context);
        executorService = Executors.newSingleThreadExecutor();
    }
}
