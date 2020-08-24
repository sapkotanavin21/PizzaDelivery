package com.example.pizzadelivery.api;
import com.example.pizzadelivery.model.LargePizza;
import java.util.List;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface LargePizzaAPI {
    @GET("largepizza")
    Call<List<LargePizza>> getlargepizza();
    @Multipart
    @POST("upload")
    Call<LargePizza> uploadImage(@Part MultipartBody.Part img);
    @GET("largepizza")
    Call<LargePizza> getImage(@Header("Authorization") String id);
}
