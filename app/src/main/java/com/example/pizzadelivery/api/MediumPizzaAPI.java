package com.example.pizzadelivery.api;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MediumPizzaAPI {
    @GET("medium")
    Call<List<MediumPizzaAPI>> getmushroompizza();
    @Multipart
    @POST("upload")
    Call<MediumPizzaAPI> uploadImage(@Part MultipartBody.Part img);
    @GET("medium")
    Call<MediumPizzaAPI> getImage(@Header("Authorization") String id);
}
