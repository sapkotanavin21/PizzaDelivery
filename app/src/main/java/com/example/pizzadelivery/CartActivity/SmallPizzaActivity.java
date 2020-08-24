package com.example.pizzadelivery.CartActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.pizzadelivery.R;
import com.example.pizzadelivery.api.CartAPI;
import com.example.pizzadelivery.model.Cart;
import com.example.pizzadelivery.url.Url;

import java.io.InputStream;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SmallPizzaActivity extends AppCompatActivity {
    ImageView AddChicken;
    TextView tvChicken,tvAddLocation,tvAddPrice;
    Button btnAddChicken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_pizza);
        AddChicken=findViewById(R.id.AddChicken);
        tvChicken=findViewById(R.id.tvChicken);
        tvAddLocation=findViewById(R.id.tvAddLocation);
        tvAddPrice=findViewById(R.id.tvAddPrice);
        btnAddChicken=findViewById(R.id.btnAddChicken);

        tvChicken.setText("Apsara");

        Bundle bundle= getIntent().getExtras();

        if (bundle != null){

            String imagename= bundle.getString("image");

            final String imgPath="http://10.0.2.2:3000/uploads/" + imagename;

            try{
                URL url = new URL(imgPath);
                AddChicken.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

            }

            catch (Exception e)
            {
                e.printStackTrace();
            }

            tvChicken.setText(bundle.getString("name"));
            tvAddLocation.setText(bundle.getString("location"));
            tvAddPrice.setText(bundle.getString("price"));

        }

        btnAddChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(SmallPizzaActivity.this);
                builder.setMessage("Are You Sure")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ChickenAddToCart();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });
    }

    private void ChickenAddToCart(){

        CartAPI myCartApi= Url.getInstance().create(CartAPI.class);
        String user_name= "Nabin";
        String product_name= tvChicken.getText().toString();
        String product_price= tvAddPrice.getText().toString();

        Cart cart = new Cart(user_name,product_name,product_price);

        Call<Void> voidCall=myCartApi.addtoitem(cart);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(SmallPizzaActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(SmallPizzaActivity.this, "Error" + t.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}


