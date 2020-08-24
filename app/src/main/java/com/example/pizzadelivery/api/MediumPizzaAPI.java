package com.example.pizzadelivery.api;
import com.example.pizzadelivery.model.MediumPizza;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MediumPizzaAPI {
    @GET("mediumpizza")
    Call<List<MediumPizza>> getmediumpizza();
    @Multipart
    @POST("upload")
    Call<MediumPizza> uploadImage(@Part MultipartBody.Part img);
    @GET("mediumpizza")
    Call<MediumPizza> getImage(@Header("Authorization") String id);
}
