package ac.id.polinema.owner.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ac.id.polinema.owner.helper.ApiHelper;
import ac.id.polinema.owner.model.PriceModel;

public class PriceRepository extends Repository {

    private static final String TAG = PriceRepository.class.getSimpleName();

    public PriceRepository(Context context) {
        super(context);
    }

    public void save(PriceModel model, RunWhenHaveDone<Boolean> runWhenHaveDone) {
        Log.i(TAG, "save: price id="+ model.getIdHarga());

        service.savePrice(model).enqueue(new ApiHelper.EnQueue<>(response -> {
            Log.i(TAG, "save: code="+ response.getStatus());
            Log.i(TAG, "save: message="+ response.getMessage());

            boolean status = (boolean) response.getData();
            runWhenHaveDone.run(status);
        }));
    }

    public void update(PriceModel model, RunWhenHaveDone<Boolean> runWhenHaveDone) {
        Log.i(TAG, "update: price id="+ model.getIdHarga());

        service.updatePrice(model.getIdHarga(), model).enqueue(new ApiHelper.EnQueue<>(response -> {
            Log.i(TAG, "update: code="+ response.getStatus());
            Log.i(TAG, "update: message="+ response.getMessage());

            boolean status = (boolean) response.getData();
            runWhenHaveDone.run(status);
        }));
    }

    public LiveData<List<PriceModel>> getPrice() {
        return getPriceGlobally();
    }

    private LiveData<List<PriceModel>> getPriceGlobally() {
        MutableLiveData<List<PriceModel>> liveData = new MutableLiveData<>();
        service.loadPrice().enqueue(new ApiHelper.EnQueue<>(response -> {
            Log.i(TAG, "getPriceGlobally: code="+ response.getStatus());
            Log.i(TAG, "getPriceGlobally: message="+ response.getMessage());

            List<PriceModel> price = (List<PriceModel>) response.getData();
            liveData.postValue(price);
        }));
        return liveData;
    }
}
