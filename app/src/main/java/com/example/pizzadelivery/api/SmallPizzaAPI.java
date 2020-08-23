package com.example.pizzadelivery.api;


import com.example.pizzadelivery.model.Small;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SmallPizzaAPI {
    @GET("smallpizza")
    Call<List<Small>> getsmallpizza();
    @Multipart
    @POST("upload")
    Call<Small> uploadImage(@Part MultipartBody.Part img);
    @GET("smallpizza")
    Call<Small> getImage(@Header("Authorization") String id);
}


