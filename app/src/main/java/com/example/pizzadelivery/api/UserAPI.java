package com.example.pizzadelivery.api;



import com.example.pizzadelivery.model.User;
import com.example.pizzadelivery.model.username;
import com.example.pizzadelivery.serverresponse.ImageResponse;
import com.example.pizzadelivery.serverresponse.SignUpResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface UserAPI {

    @POST("users/signup")
    Call<SignUpResponse> registerUser(@Body User users);
    @POST("users/login")
    Call<SignUpResponse> checklogin(@Body username Username);

    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @GET("users/me")
    Call<User> getUserDetails(@Header("Authorization") String token);

//    @PUT("users/me")
//    Call<Update> getUpdate(@Header("Authorization") String token, @Body Update update);
}
