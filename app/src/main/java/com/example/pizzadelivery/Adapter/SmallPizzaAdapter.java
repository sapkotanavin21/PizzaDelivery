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
import com.example.pizzadelivery.model.Small;
import com.example.pizzadelivery.url.Url;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class SmallPizzaAdapter extends RecyclerView.Adapter<SmallPizzaAdapter.SmallViewHolder> {
    Context mContext;
    List<Small> smallPizzaList;

    public SmallPizzaAdapter(Context mContext, List<Small> smallPizzaList) {
        this.mContext = mContext;
        this.smallPizzaList = smallPizzaList;
    }

    @NonNull
    @Override
    public SmallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_smallpizza, parent, false);
        return new SmallPizzaAdapter.SmallViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SmallViewHolder SmallViewHolder, int i) {
//
        final Small small = (Small) smallPizzaList.get(i);
        String imgpath = Url.imagepath + small.getImage();

        SmallViewHolder.tvName.setText(small.getName());
        SmallViewHolder.tvlocation.setText(small.getLocation());
        SmallViewHolder.tvPrice.setText(small.getPrice());
        SmallViewHolder.tvfood.setText(small.getFood());
        StrictModeClass.StrictMode();
        try {

            URL url = new URL(imgpath);
            SmallViewHolder.card1.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        SmallViewHolder.card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SmallPizzaActivity.class);
                intent.putExtra("image", small.getImage());
                intent.putExtra("name", small.getName());
                intent.putExtra("location", small.getLocation());
                intent.putExtra("price", small.getPrice());
                mContext.startActivity(intent);
            }
        });

    }
//


    @Override
    public int getItemCount() {
        return smallPizzaList.size();
    }

    public class SmallViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProfile, card1;
        TextView tvName, tvlocation, tvPrice, tvfood;


        public SmallViewHolder(@NonNull View itemView) {
            super(itemView);

            card1 = itemView.findViewById(R.id.card1);
            tvName = itemView.findViewById(R.id.tvName);
            tvlocation = itemView.findViewById(R.id.tvlocation);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvfood = itemView.findViewById(R.id.tvfood);


        }

    }
}


