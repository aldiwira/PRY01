package ac.id.polinema.owner.repository;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ac.id.polinema.owner.api.ApiService;
import ac.id.polinema.owner.database.AppDatabase;
import ac.id.polinema.owner.helper.ApiHelper;
import ac.id.polinema.owner.helper.DbHelper;

import static ac.id.polinema.owner.helper.Helper.isConnected;
import static ac.id.polinema.owner.helper.Helper.showMessage;

public class Repository{

    protected Context context;
    protected ApiService service;
    protected AppDatabase database;
    protected ExecutorService executorService;

    protected static final String TAG = Repository.class.getSimpleName();

    protected Repository(Context context) {
        this.context = context;
        this.service = ApiHelper.getInstance();
        this.database = DbHelper.instance(context);
        this.executorService = Executors.newSingleThreadExecutor();
    }

    protected boolean isOnline() {
        if (!isConnected(context)) {
            showMessage(context,"Please Check Internet Connection.");
            return false;
        } else {
            return true;
        }
    }

    public interface RunWhenHaveDone<T> {
        void run(T t);
    }
}
