package com.example.pizzadelivery.Mycart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pizzadelivery.Adapter.CartAdapter;
import com.example.pizzadelivery.R;
import com.example.pizzadelivery.model.Cart;
import com.example.pizzadelivery.url.Url;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyCartFragment extends Fragment {


    RecyclerView recyclerView4;
    List<Cart> cartList;
    com.example.pizzadelivery.Adapter.CartAdapter CartAdapter;

    public static MyCartFragment newInstance() {
        return new MyCartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_cart_fragment, container, false);
        cart();
        recyclerView4 = view.findViewById(R.id.recycleview4);
        return view;
    }

    private void cart() {
        cartList = new ArrayList<>();
        MyCartAPI myCartAPI = Url.getInstance().create(MyCartAPI.class);
        Call<List<Cart>> listCall = myCartAPI.getcart();
        listCall.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Error" + response.code(), Toast.LENGTH_SHORT).show();
                }
                List<Cart> cartList1 = response.body();
                CartAdapter = new CartAdapter(getContext(), cartList1);
                recyclerView4.setAdapter(CartAdapter);
                recyclerView4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView4.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Log.d("Error Message", "Error" + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


