package ac.id.polinema.owner.ui.transactions;

import android.app.Application;
import android.widget.Toast;

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

public class TransactionsViewModel extends AndroidViewModel {

    private TransactionRepository transactionRepository;
    private PriceRepository priceRepository;

    public TransactionsViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
        priceRepository = new PriceRepository(application);
    }

    void observeDataOrder(LifecycleOwner owner,
                                 Observer<List<TransactionModel>> observer) {
        transactionRepository.getOnProggress().observe(owner, observer);
    }

    LiveData<List<PriceModel>> fetchDataPrices() {
        return priceRepository.getPrice();
    }

    void updateItem(int id) {
        transactionRepository.updatePerItemDetailTransaction(id, result -> {
            if (!result) {
                Toast.makeText(getApplication(),
                        "Terjadi Kesalahan saat melakukan update data silahkan ulangi kembali",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    void changeStatus(String noNota) {
        final String ON_PROGGRESS = "DONE";
        transactionRepository.changeStatus(noNota, ON_PROGGRESS, null);
    }
}
