package ac.id.polinema.owner.ui.histroy;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import ac.id.polinema.owner.model.PriceModel;
import ac.id.polinema.owner.model.TransactionModel;
import ac.id.polinema.owner.repository.PriceRepository;
import ac.id.polinema.owner.repository.TransactionRepository;

public class HistoryViewModel extends AndroidViewModel {

    private TransactionRepository transactionRepository;
    private PriceRepository priceRepository;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
        priceRepository = new PriceRepository(application);
    }

    public void observeDataHistory(LifecycleOwner owner,
                                   Observer<List<TransactionModel>> observer) {
        transactionRepository.getHistory().observe(owner, observer);
    }

    public LiveData<List<PriceModel>> fetchDataPrices() {
        return priceRepository.getPrice();
    }
}