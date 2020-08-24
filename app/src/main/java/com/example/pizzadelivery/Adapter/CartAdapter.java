package com.example.pizzadelivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadelivery.R;
import com.example.pizzadelivery.model.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context mContext;
    List<Cart> cartList;

    public CartAdapter(Context mContext, List<Cart> cartList){
        this.mContext=mContext;
        this.cartList=cartList;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cart, parent, false);
        return new CartAdapter.CartViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
      final Cart cart =cartList.get(position);
      holder.username.setText(cart.user);
      holder.itemname.setText(cart.name);
      holder.price.setText(cart.price);
    }

    @Override
    public int getItemCount() {
        if(cartList==null){
            return 0;
        }else {
            return cartList.size();
        }
    }


   public  class CartViewHolder extends RecyclerView.ViewHolder{
        private TextView username,itemname,price;

        public CartViewHolder(@NonNull View itemView){
            super(itemView);

            username =itemView.findViewById(R.id.cart_username);
            itemname=itemView.findViewById(R.id.cart_itemname);
            price = itemView.findViewById(R.id.cart_price);
    }
}
}
