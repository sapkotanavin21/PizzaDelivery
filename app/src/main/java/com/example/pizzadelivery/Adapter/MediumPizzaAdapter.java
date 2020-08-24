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

import com.example.pizzadelivery.CartActivity.SmallPizzaActivity;
import com.example.pizzadelivery.R;
import com.example.pizzadelivery.StrictModeClass.StrictModeClass;
import com.example.pizzadelivery.model.MediumPizza;
import com.example.pizzadelivery.url.Url;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MediumPizzaAdapter extends RecyclerView.Adapter<MediumPizzaAdapter.MediumPizzaViewHolder> {
    Context mContext;
    List<MediumPizza> mediumPizzaList;

    public MediumPizzaAdapter(Context mContext, List<MediumPizza> mediumPizzaList) {
        this.mContext = mContext;
        this.mediumPizzaList = mediumPizzaList;

    }

    @NonNull
    @Override
    public MediumPizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_medium_pizza, parent, false);
        return new MediumPizzaAdapter.MediumPizzaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MediumPizzaViewHolder MediumPizzaViewHolder, int i) {
//
        final MediumPizza mediumPizza = (MediumPizza) mediumPizzaList.get(i);
        String imgpath = Url.imagepath + mediumPizza.getImage();
        ///// chickenViewHolder.imgProfile.setImageResource(chickenPizza.getImage());
        MediumPizzaViewHolder.tvName.setText(mediumPizza.getName());
        MediumPizzaViewHolder.tvlocation.setText(mediumPizza.getLocation());
        MediumPizzaViewHolder.tvPrice.setText(mediumPizza.getPrice());
        StrictModeClass.StrictMode();
        try {

            URL url = new URL(imgpath);
            MediumPizzaViewHolder.imgProfile.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        MediumPizzaViewHolder.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SmallPizzaActivity.class);
                intent.putExtra("image", mediumPizza.getImage());
                intent.putExtra("name", mediumPizza.getName());
                intent.putExtra("location", mediumPizza.getLocation());
                intent.putExtra("price", mediumPizza.getPrice());
                mContext.startActivity(intent);
            }
        });

    }
//


    @Override
    public int getItemCount() {
        return mediumPizzaList.size();
    }

    public class MediumPizzaViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProfile;
        TextView tvName, tvlocation, tvPrice;


        public MediumPizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            tvName = itemView.findViewById(R.id.tvName);
            tvlocation = itemView.findViewById(R.id.tvlocation);
            tvPrice = itemView.findViewById(R.id.tvPrice);


        }

    }
}


