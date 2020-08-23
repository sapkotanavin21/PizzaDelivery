package com.example.pizzadelivery.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadelivery.Adapter.SmallPizzaAdapter;
import com.example.pizzadelivery.R;
import com.example.pizzadelivery.api.SmallPizzaAPI;
import com.example.pizzadelivery.model.Small;
import com.example.pizzadelivery.url.Url;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    List<Small> smallList;
    SmallPizzaAdapter smallPizzaAdapter;
    RecyclerView smallrecycleview;
    private HomeViewModel homeViewModel;
    private int[] mImages = new int[]{
            R.drawable.a1, R.drawable.ad3, R.drawable.a1
    };


    CarouselView carouselView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        carouselView = view.findViewById(R.id.caral);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });

        smallrecycleview = view.findViewById(R.id.recycleview);
        SmallPizza();

        return view;
    }

    private void SmallPizza() {
        smallList=new ArrayList<>();
        SmallPizzaAPI smallPizzaAPI= Url.getInstance().create(SmallPizzaAPI.class);
        Call<List<Small>> call = smallPizzaAPI.getsmallpizza();
        call.enqueue(new Callback<List<Small>>() {
            @Override
            public void onResponse(Call<List<Small>> call, Response<List<Small>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getContext(), "err"+response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("homefragent","this is" + response.code());
                    return;
                }
                List<Small> smallList1 = response.body();
                smallPizzaAdapter = new SmallPizzaAdapter(getContext(),smallList1);
                smallrecycleview.setAdapter(smallPizzaAdapter);
                smallrecycleview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
            }

            @Override
            public void onFailure(Call<List<Small>> call, Throwable t) {
                Toast.makeText(getContext(), "err", Toast.LENGTH_SHORT).show();
                Log.d("Small Size Pizza","this is");
            }
        });

    }
}