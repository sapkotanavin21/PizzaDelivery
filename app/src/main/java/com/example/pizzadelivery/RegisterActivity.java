package com.example.pizzadelivery;

import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.loader.content.CursorLoader;

import com.example.pizzadelivery.StrictModeClass.StrictModeClass;
import com.example.pizzadelivery.api.UserAPI;
import com.example.pizzadelivery.model.User;
import com.example.pizzadelivery.serverresponse.ImageResponse;
import com.example.pizzadelivery.serverresponse.SignUpResponse;
import com.example.pizzadelivery.url.Url;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {


    private EditText etFirst, etLast, etPhoneNo, etUsername, etPassword, etConfirmPassword;
    private Button btnSignUp;
    TextView txtlog;
    private CircleImageView userimgProfile;
    String imagePath;
    private String imageName = "";
 private NotificationManagerCompat notificationManagerCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        notificationManagerCompat= NotificationManagerCompat.from(this);
        etFirst = findViewById(R.id.etfname);
        etLast = findViewById(R.id.etlname);
        etPhoneNo = findViewById(R.id.etphone);
        etUsername=findViewById(R.id.etusername);
        etPassword = findViewById(R.id.etpassword);
        etConfirmPassword = findViewById(R.id.etcmpassword);

        txtlog = findViewById(R.id.txtlog);
        txtlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp = findViewById(R.id.btnreg);
        userimgProfile=findViewById(R.id.profileimg);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getText().toString().equals((etPassword.getText().toString()))) {
                    saveImageOnly();
                    signup();
                } else {
                    Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }
            }
        });

        userimgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
    }

    private void BrowseImage() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        userimgProfile.setImageURI(uri);
        imagePath = getRealPathFromUri(uri);
    }
    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }
    private void saveImageOnly() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
                file.getName(), requestBody);

        UserAPI usersAPI = Url.getInstance().create(UserAPI.class);
        Call<ImageResponse> responseBodyCall = usersAPI.uploadImage(body);

        StrictModeClass.StrictMode();
        //Synchronous method
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void signup() {

        String firstname = etFirst.getText().toString();
        String lastname = etLast.getText().toString();
        String PhoneNumber= etPhoneNo.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User(firstname,lastname,PhoneNumber,username,password,imageName);
        UserAPI userapi = Url.getInstance().create(UserAPI.class);
        Call<SignUpResponse> signupResponseCall = userapi.registerUser(user);
        signupResponseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Code" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Notification notification=new NotificationCompat.Builder(RegisterActivity.this,CreateChannel.CHANNEL_1)
                        .setSmallIcon(R.drawable.c5)
                        .setContentTitle("Register Sucessful")
                        .setContentText("you have sucessfully registered")
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();
                notificationManagerCompat.notify(1,notification);
                Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


}