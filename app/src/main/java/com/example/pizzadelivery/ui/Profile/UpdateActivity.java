package com.example.pizzadelivery.ui.Profile;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.example.pizzadelivery.MainActivity;
import com.example.pizzadelivery.R;
import com.example.pizzadelivery.StrictModeClass.StrictModeClass;
import com.example.pizzadelivery.api.UserAPI;
import com.example.pizzadelivery.model.Update;
import com.example.pizzadelivery.model.User;
import com.example.pizzadelivery.serverresponse.ImageResponse;
import com.example.pizzadelivery.url.Url;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    private EditText ettFirst,ettLast,ettPhone,ettUser;
    private Button btnUpdate;
    CircleImageView UpdateImage;
    String imagePath;
    private String imageName = "";
   User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ettFirst=findViewById(R.id.ettFirst);
        ettLast=findViewById(R.id.ettLast);
        ettPhone=findViewById(R.id.ettPhone);
       ettUser=findViewById(R.id.ettUser);
        btnUpdate=findViewById(R.id.btnUpdate);
        UpdateImage=findViewById(R.id.UpdateImage);
        user=new User();
        user=(User) getIntent().getSerializableExtra("User");

        ettFirst.setText(user.getFirstName());
        ettLast.setText(user.getLastName());
        ettPhone.setText(user.getPhoneNumber());
       ettUser.setText(user.getUsername());

        final String imgPath= Url.imagepath + user.getImage();
        try{
            URL url = new URL(imgPath);
            UpdateImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        }

        catch (IOException e){
            e.printStackTrace();
        }

        UpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveImageOnly();
                Update();

            }
        });
    }

    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
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
        UpdateImage.setImageURI(uri);
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

    private void Update(){
        String fname=ettFirst.getText().toString();
        String lname=ettLast.getText().toString();
        String phone=ettPhone.getText().toString();
        String user=ettUser.getText().toString();

        Update update= new Update(fname,lname,phone,user,imageName);

        UserAPI userApi= Url.getInstance().create(UserAPI.class);
        Call<Update> updateProfileCall=userApi.getUpdate(Url.token,update);

        updateProfileCall.enqueue(new Callback<Update>() {
            @Override
            public void onResponse(Call<Update> call, Response<Update> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(UpdateActivity.this, "Code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Update> call, Throwable t) {

                Toast.makeText(UpdateActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
