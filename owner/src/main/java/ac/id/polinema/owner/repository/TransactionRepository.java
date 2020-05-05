package ac.id.polinema.owner.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ac.id.polinema.owner.api.BodyRequest;
import ac.id.polinema.owner.database.UserDao;
import ac.id.polinema.owner.helper.ApiHelper;
import ac.id.polinema.owner.model.TransactionModel;

public class TransactionRepository extends Repository {

    private UserDao userDao;

    private static final int CODE_NEW_ORDER = 1;

    public TransactionRepository(Context context) {
        super(context);
        userDao = database.userDao();
    }

    public void updatePerItemDetailTransaction(int id, RunWhenHaveDone<Boolean> runnable) {
        service.update(id).enqueue(new ApiHelper.EnQueue<>(response -> {
            Log.i(TAG, "updatePerItemDetailTransaction: "+ response.getStatus());
            Log.i(TAG, "updatePerItemDetailTransaction: "+ response.getMessage());
            runnable.run((Boolean) response.getData());
        }));
    }

    public void update(BodyRequest.Order order, RunWhenHaveDone<Boolean> runnable) {
        service.update(order).enqueue(new ApiHelper.EnQueue<>(response -> {
            Log.i(TAG, "update: "+ response.getStatus());
            Log.i(TAG, "update: "+ response.getMessage());

            runnable.run((Boolean) response.getData());
        }));
    }

    public void changeStatus(String noNota, String status, Runnable runWhenHaveDone) {
        service.update(noNota, status).enqueue(new ApiHelper.EnQueue<>(response -> {
            Log.d(TAG, "changeStatus: "+ response.getStatus());
            Log.d(TAG, "changeStatus: "+ response.getMessage());
            if (runWhenHaveDone != null) runWhenHaveDone.run();
        }));
    }

    public MutableLiveData<List<TransactionModel>> getNewOrder() {
        MutableLiveData<List<TransactionModel>> liveData = new MutableLiveData<>();
        service.getNewOrder().enqueue(new ApiHelper.EnQueue<>(response -> {
            Log.d(TAG, "getNewOrder: "+ response.getStatus());
            Log.d(TAG, "getNewOrder: "+ response.getMessage());

            if (response.getStatus() == 200) {
                List<TransactionModel> list = (List<TransactionModel>) response.getData();
                liveData.postValue(list);
            }
        }));

        return liveData;
    }

    public LiveData<List<TransactionModel>> getOnProggress() {
        MutableLiveData<List<TransactionModel>> liveData = new MutableLiveData<>();
        service.getOrderOnProggress().enqueue(new ApiHelper.EnQueue<>(response -> {
            Log.d(TAG, "getNewOrder: "+ response.getStatus());
            Log.d(TAG, "getNewOrder: "+ response.getMessage());

            if (response.getStatus() == 200) {
                List<TransactionModel> list = (List<TransactionModel>) response.getData();
                liveData.postValue(list);
            }
        }));

        return liveData;
    }

    public LiveData<List<TransactionModel>> getHistory() {
        MutableLiveData<List<TransactionModel>> liveData = new MutableLiveData<>();
        service.getOrderHistory().enqueue(new ApiHelper.EnQueue<>(response -> {
            Log.d(TAG, "getNewOrder: "+ response.getStatus());
            Log.d(TAG, "getNewOrder: "+ response.getMessage());

            if (response.getStatus() == 200) {
                List<TransactionModel> list = (List<TransactionModel>) response.getData();
                liveData.postValue(list);
            }
        }));

        return liveData;
    }
}