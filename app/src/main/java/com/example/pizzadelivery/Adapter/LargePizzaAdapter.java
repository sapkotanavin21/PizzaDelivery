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

import com.example.pizzadelivery.CartActivity.LargePizzaActivity;
import com.example.pizzadelivery.R;
import com.example.pizzadelivery.StrictModeClass.StrictModeClass;
import com.example.pizzadelivery.model.LargePizza;
import com.example.pizzadelivery.url.Url;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class LargePizzaAdapter extends RecyclerView.Adapter<LargePizzaAdapter.LargeViewHolder>{
    Context mContext;
    List<LargePizza> largePizzaList;
    public LargePizzaAdapter(Context mContext, List<LargePizza> largePizzaList) {
        this.mContext = mContext;
        this.largePizzaList = largePizzaList;

}
    @NonNull
    @Override
    public LargeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_largepizza, parent, false);
        return new LargePizzaAdapter.LargeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LargeViewHolder LargeViewHolder, int i) {
//
        final LargePizza cheesePizza= (LargePizza) largePizzaList.get(i);
        String imgpath= Url.imagepath+cheesePizza.getImage();
        ///// chickenViewHolder.imgProfile.setImageResource(chickenPizza.getImage());
        LargeViewHolder.tvName.setText(cheesePizza.getName());
        LargeViewHolder.tvlocation.setText(cheesePizza.getLocation());
        LargeViewHolder.tvPrice.setText(cheesePizza.getPrice());
        LargeViewHolder.tvfood.setText(cheesePizza.getFood());
        StrictModeClass.StrictMode();
        try{
            URL url = new URL(imgpath);
            LargeViewHolder.card2.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LargeViewHolder.card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, LargePizzaActivity.class);
                intent.putExtra("image",cheesePizza.getImage());
                intent.putExtra("name",cheesePizza.getName());
                intent.putExtra("location",cheesePizza.getLocation());
                intent.putExtra("price",cheesePizza.getPrice());
                mContext.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return largePizzaList.size();
    }

    public class LargeViewHolder extends RecyclerView.ViewHolder {

        ImageView card2;
        TextView tvName, tvlocation, tvPrice,tvfood;

        public LargeViewHolder(@NonNull View itemView) {
            super(itemView);
            card2=itemView.findViewById(R.id.card2);
            tvName=itemView.findViewById(R.id.tvName);
            tvlocation=itemView.findViewById(R.id.tvlocation);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvfood = itemView.findViewById(R.id.tvfood);


        }

    }
}


