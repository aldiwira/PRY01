package ac.id.polinema.delaundry.api;

import java.util.List;

import ac.id.polinema.delaundry.model.PriceModel;
import ac.id.polinema.delaundry.model.TransactionModel;
import ac.id.polinema.delaundry.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @FormUrlEncoded
    @POST("/user/register")
    Call<Response<Boolean>> register(@Field("noHp") String noHp);

    @POST("/user/register/account")
    Call<Response<UserModel>> createAccount(@Body UserModel userModel);

    @FormUrlEncoded
    @POST("/user/login")
    Call<Response<UserModel>> login(@Field("noHp") String noHp,
                                    @Field("password") String password);

    @POST("/order")
    Call<Response<Boolean>> createTransaction(@Body BodyRequest.Order order);

    @GET("/order/{idUser}/status")
    Call<Response<List<TransactionModel>>> getTransactions(@Path("idUser") String queries);

    @GET("/order/{idUser}/history")
    Call<Response<List<TransactionModel>>> getHistory(@Path("idUser") String idUser);

    @GET("/prices")
    Call<Response<List<PriceModel>>> getPrices();

}
