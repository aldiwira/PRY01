package ac.id.polinema.delaundry.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ac.id.polinema.delaundry.App;
import ac.id.polinema.delaundry.api.ApiService;
import ac.id.polinema.delaundry.api.BodyRequest;
import ac.id.polinema.delaundry.database.AppDatabase;
import ac.id.polinema.delaundry.database.PriceDao;
import ac.id.polinema.delaundry.helper.ApiHelper;
import ac.id.polinema.delaundry.helper.DbHelper;
import ac.id.polinema.delaundry.model.TransactionModel;

import static ac.id.polinema.delaundry.Utils.isConnected;

public class TransactionRepository {

    private static String TAG = TransactionRepository.class.getSimpleName();

    private Context context;
    private ApiService api;
    private AppDatabase database;
    private ExecutorService executorService;

    public TransactionRepository(Context context) {
        this.context = context;
        api = ApiHelper.getInstance();
        database = DbHelper.instance(context);
        executorService = Executors.newSingleThreadExecutor();
    }

    public MutableLiveData<Boolean> order(List<String> types, String method) {
        PriceDao dao = database.priceDao();
        BodyRequest.Order order = new BodyRequest.Order();
        MutableLiveData<Boolean> liveStatus = new MutableLiveData<>();

        if (isConnected(context)) {
            executorService.submit(() -> {
                List<Integer> ids = dao.getIdBytype(types);
                order.setIdUser(App.getIdUser());
                order.setIdPrices(ids);
                order.setMethodDelivery(method);
                api.createTransaction(order)
                   .enqueue(new ApiHelper.EnQueue<>(response -> {
                       Log.i("TAG", "order: status code="+ response.getStatus());
                       Log.i("TAG", "order: message="+ response.getMessage());
                       liveStatus.postValue(true);
                }));
            });
        } else {
            liveStatus.postValue(false);
        }

        return liveStatus;
    }

    public LiveData<List<TransactionModel>> getActiveTransactions() {
        MutableLiveData<List<TransactionModel>> liveData = new MutableLiveData<>();

        api.getTransactions(App.getIdUser())
           .enqueue(new ApiHelper.EnQueue<>(response -> {
               List<TransactionModel> transactionList = (List<TransactionModel>) response.getData();
               liveData.postValue(transactionList);
        }));

        return liveData;
    }

    public LiveData<List<TransactionModel>> getDoneTransaction() {
        MutableLiveData<List<TransactionModel>> liveData = new MutableLiveData<>();

        api.getHistory(App.getIdUser())
           .enqueue(new ApiHelper.EnQueue<>(response -> {
               List<TransactionModel> transactionList = (List<TransactionModel>) response.getData();
               liveData.postValue(transactionList);
           }));

        return liveData;
    }
}