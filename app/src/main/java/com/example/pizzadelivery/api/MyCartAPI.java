package com.example.pizzadelivery.api;


import com.example.pizzadelivery.model.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface MyCartAPI {
   @GET("cart")
   Call<List<Cart>> getcart();
  @GET("cart")
    Call<Cart>getImage(@Header("Authorization") String token);
}
