package com.example.pizzadelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apsara.pizzadelivery.CartActivity.ChickenPizzaActivity;
import com.apsara.pizzadelivery.R;
import com.apsara.pizzadelivery.StrictModeClass.StrictModeClass;
import com.apsara.pizzadelivery.model.Chicken;
import com.apsara.pizzadelivery.url.Url;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class SmallPizzaAdapter extends RecyclerView.Adapter<SmallPizzaAdapter.ChickenViewHolder> {
    Context mContext;
    List<Small> smallPizzaList;

    public SmallPizzaAdapter(Context mContext, List<Small> smallPizzaList) {
        this.mContext = mContext;
        this.smallPizzaList = smallPizzaList;
    }

    @NonNull
    @Override
    public ChickenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_chickenpizza, parent, false);
        return new SmallPizzaAdapter.ChickenViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChickenViewHolder chickenViewHolder, int i) {
//
        final Chicken chickenPizza= (Chicken) chickenPizzaList.get(i);
        String imgpath=Url.imagepath+chickenPizza.getImage();
     ///// chickenViewHolder.imgProfile.setImageResource(chickenPizza.getImage());
        chickenViewHolder.tvName.setText(chickenPizza.getName());
        chickenViewHolder.tvlocation.setText(chickenPizza.getLocation());
        chickenViewHolder.tvPrice.setText(chickenPizza.getPrice());
        chickenViewHolder.tvfood.setText(chickenPizza.getFood());
        StrictModeClass.StrictMode();
        try{

                URL url = new URL(imgpath);
                chickenViewHolder.card1.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

            } catch (Exception e) {
                e.printStackTrace();
            }
     chickenViewHolder.card1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
Intent intent=new Intent(mContext,ChickenPizzaActivity.class);
intent.putExtra("image",chickenPizza.getImage());
             intent.putExtra("name",chickenPizza.getName());
             intent.putExtra("location",chickenPizza.getLocation());
             intent.putExtra("price",chickenPizza.getPrice());
             mContext.startActivity(intent);
         }
     });

    }
//


    @Override
    public int getItemCount() {
        return chickenPizzaList.size();
    }

    public class ChickenViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProfile, card1;
        TextView tvName, tvlocation, tvPrice,tvfood;


        public ChickenViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            card1=itemView.findViewById(R.id.card1);
            tvName=itemView.findViewById(R.id.tvName);
            tvlocation=itemView.findViewById(R.id.tvlocation);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvfood = itemView.findViewById(R.id.tvfood);


        }

    }
}


