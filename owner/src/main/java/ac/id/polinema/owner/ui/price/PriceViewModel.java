package ac.id.polinema.owner.ui.price;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ac.id.polinema.owner.model.PriceModel;
import ac.id.polinema.owner.repository.PriceRepository;
import ac.id.polinema.owner.repository.Repository;

public class PriceViewModel extends AndroidViewModel {

    private PriceRepository priceRepository;

    LiveData<List<PriceModel>> liveData;

    public PriceViewModel(@NonNull Application application) {
        super(application);
        priceRepository = new PriceRepository(application);
        liveData = new MutableLiveData<>();
    }

    void loadData() {
        liveData = priceRepository.getPrice();
    }

    void save(PriceModel price, Repository.RunWhenHaveDone<Boolean> runWhenHaveDone) {
        priceRepository.save(price, runWhenHaveDone);
    }

    void update(PriceModel price, Repository.RunWhenHaveDone<Boolean> runWhenHaveDone) {
        priceRepository.update(price, runWhenHaveDone);
    }
}
