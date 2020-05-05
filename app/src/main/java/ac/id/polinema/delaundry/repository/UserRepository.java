package ac.id.polinema.delaundry.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ac.id.polinema.delaundry.App;
import ac.id.polinema.delaundry.api.Response;
import ac.id.polinema.delaundry.database.UserDao;
import ac.id.polinema.delaundry.helper.ApiHelper;
import ac.id.polinema.delaundry.model.UserModel;

import static ac.id.polinema.delaundry.Utils.showMessage;

public class UserRepository extends Repository {

    private UserDao dao;

    public UserRepository(Context context) {
        super(context);
        dao = database.userDao();
    }

    public void loadUserActive() {
        executorService.submit(() -> {
            UserModel user = dao.get().get(0);
            App.setUserModel(user);
        });
    }

    public LiveData<Boolean> register(String noHandphone) {
        MutableLiveData<Boolean> liveResult = new MutableLiveData<>();
        service.register(noHandphone).enqueue(new ApiHelper.EnQueue<>(response -> {
            showMessage(context, response.getMessage());
            liveResult.postValue(response.getStatus() == 200);
        }));

        return liveResult;
    }

    public LiveData<Boolean> createAccount(UserModel model) {
        MutableLiveData<Boolean> liveResult = new MutableLiveData<>();
        service.createAccount(model).enqueue(new ApiHelper.EnQueue<>(response -> {
            success(response);
            liveResult.postValue(response.getStatus() == 200);
        }));
        return liveResult;
    }

    public LiveData<Boolean> login(String noHandphone, String password) {
        MutableLiveData<Boolean> liveResult = new MutableLiveData<>();
        service.login(noHandphone, password).enqueue(new ApiHelper.EnQueue<>(response -> {
            success(response);
            liveResult.postValue(response.getStatus() == 200);
        }));
        return liveResult;
    }

    private void success(Response response) {
        Log.d(TAG, "success: "+ response.getStatus());
        Log.d(TAG, "success: "+ response.getMessage());

        UserModel user = (UserModel) response.getData();
        App.setSharedPreferences(App.IS_FIRST_TIME_LAUNCH, false);
        App.setSharedPreferences(App.NO_HANDPHONE, user.getNoHp());
        App.setSharedPreferences(App.KEY_ID_USER, user.getIdUser());
        executorService.submit(() -> {
            dao.remove();
            dao.save(user);
        });
    }
}
