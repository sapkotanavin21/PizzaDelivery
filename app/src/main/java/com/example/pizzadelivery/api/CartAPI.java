package com.example.pizzadelivery.api;



import com.example.pizzadelivery.model.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CartAPI {
    @GET("cart")
    Call<List<Cart>> getCart();

    @GET("cart")
    Call<Cart> getImage(@Header("Authorization") String token);
    @POST("cart")
    Call<Void>addtoitem(@Body Cart cart);

}
