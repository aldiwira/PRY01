package ac.id.polinema.owner.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ac.id.polinema.owner.api.BodyRequest;
import ac.id.polinema.owner.model.PriceModel;
import ac.id.polinema.owner.model.TransactionModel;
import ac.id.polinema.owner.repository.PriceRepository;
import ac.id.polinema.owner.repository.Repository;
import ac.id.polinema.owner.repository.TransactionRepository;

public class HomeViewModel extends AndroidViewModel {

    private TransactionRepository transactionRepository;
    private PriceRepository priceRepository;

    MutableLiveData<List<TransactionModel>> liveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
        transactionRepository = new TransactionRepository(application);
        priceRepository = new PriceRepository(application);
        liveData = transactionRepository.getNewOrder();
    }

    void acceptOrder(String idBill, List<BodyRequest.Weight> weights,
                     Repository.RunWhenHaveDone<Boolean> runnable) {
        BodyRequest.Order data = new BodyRequest.Order(idBill, weights);
        transactionRepository.update(data, runnable);
    }

    LiveData<List<PriceModel>> loadDataPrice() {
        return priceRepository.getPrice();
    }
}