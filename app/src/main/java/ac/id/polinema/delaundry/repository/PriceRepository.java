package ac.id.polinema.delaundry.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executors;

import ac.id.polinema.delaundry.api.ApiService;
import ac.id.polinema.delaundry.database.AppDatabase;
import ac.id.polinema.delaundry.database.PriceDao;
import ac.id.polinema.delaundry.helper.ApiHelper;
import ac.id.polinema.delaundry.helper.DbHelper;
import ac.id.polinema.delaundry.model.PriceModel;

import static ac.id.polinema.delaundry.Utils.isConnected;
import static ac.id.polinema.delaundry.Utils.showMessage;

public class PriceRepository {

    private String TAG = PriceRepository.class.getSimpleName();

    private Context context;
    private ApiService api;
    private AppDatabase database;

    private MutableLiveData<List<PriceModel>> livePrices;

    public PriceRepository(Context context) {
        this.context = context;
        api = ApiHelper.getInstance();
        database = DbHelper.instance(context);

        livePrices = new MutableLiveData<>();
    }

    public LiveData<List<PriceModel>> loadPrices(boolean update) {
        PriceDao dao = database.priceDao();

        if (isConnected(context)) {
            api.getPrices().enqueue(new ApiHelper.EnQueue<>((response) -> {
                // Saving data price from web service to room
                List<PriceModel> prices = (List<PriceModel>) response.getData();
                Executors.newSingleThreadExecutor().submit(() -> {
                    dao.removeAll();
                    for (PriceModel price: prices) {
                        dao.insert(price);
                        Log.d(this.getClass().getSimpleName(), "loadPrices: "+ price.getIdHarga());
                    }
                });

                livePrices.postValue(prices);
            }));
        } else {
            if (update) {
                showMessage(context, "Please Check Internet Connection.");
                return null;
            } else {
                Executors.newSingleThreadExecutor().submit(() -> {
                    List<PriceModel> prices = dao.getAll();
                    livePrices.postValue(prices);

                    Log.i(TAG, "loadPrices: size prices="+ prices.size());
                });
            }
        }

        return livePrices;
    }

}
