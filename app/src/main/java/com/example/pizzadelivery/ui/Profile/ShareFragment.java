package com.example.pizzadelivery.ui.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pizzadelivery.R;
import com.example.pizzadelivery.api.UserAPI;
import com.example.pizzadelivery.model.User;
import com.example.pizzadelivery.url.Url;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

;


public class ShareFragment extends Fragment {
    private TextView etFirst,etLast,etPhone,etUser;
    private CircleImageView UpdateImage;
    private Button btnUpdate;
    User user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        UpdateImage=root.findViewById(R.id.UpdateImage);
        etFirst=root.findViewById(R.id.etFirst);
        etLast=root.findViewById(R.id.etLast);
        etPhone=root.findViewById(R.id.etPhone);
        etUser=root.findViewById(R.id.etUser);
        btnUpdate=root.findViewById(R.id.btnUpdate);
        user=new User();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), UpdateActivity.class);
                intent.putExtra("User",user);
                startActivity(intent);
            }
        });
        loaduser();
        return root;
    }




    public  void loaduser(){
        UserAPI userAPI= Url.getInstance().create(UserAPI.class);


        Call<User> userCall = userAPI.getUserDetails(Url.token);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getContext(), "Code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                user = response.body();

                if(response.body()!=null)
                {
                    String imgPath= null;
                    imgPath= Url.imagepath + response.body().getImage();
                    Picasso.get().load(imgPath).into(UpdateImage);
                }

                etFirst.setText(response.body().getFirstName());
                etLast.setText(response.body().getLastName());
                etPhone.setText(response.body().getPhoneNumber());
                etUser.setText(response.body().getUsername());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}


