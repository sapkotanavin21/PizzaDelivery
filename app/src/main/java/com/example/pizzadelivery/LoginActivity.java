package com.example.pizzadelivery;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pizzadelivery.StrictModeClass.StrictModeClass;
import com.example.pizzadelivery.api.UserAPI;
import com.example.pizzadelivery.bll.LoginBLL;
import com.example.pizzadelivery.model.username;
import com.example.pizzadelivery.serverresponse.SignUpResponse;
import com.example.pizzadelivery.url.Url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    Button btnlogin;
    EditText etUsername, etPassword;
    TextView tvRegister;
    TextView ProximitySensor, data;
    private NotificationManagerCompat notificationManagerCompat;
    SensorManager mySensorManager;
    Sensor myProximitySensor;
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnlogin = findViewById(R.id.btnlogin);

        notificationManagerCompat= NotificationManagerCompat.from(this);
        CreateChannel channel= new CreateChannel(this);
        channel.createChannel();

       tvRegister=findViewById(R.id.tvRegister);

      tvRegister.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
              startActivity(intent);
          }
      });

        //
        ProximitySensor = (TextView) findViewById(R.id.proximitySensor);
        data = (TextView) findViewById(R.id.data);
        mySensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);
        myProximitySensor = mySensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        if (myProximitySensor == null) {
            ProximitySensor.setText("No Proximity Sensor!");
        } else {
            mySensorManager.registerListener(proximitySensorEventListener,
                    myProximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,MainActivity.class);
               login();
                if (TextUtils.isEmpty(etUsername.getText())) {
                    etUsername.setError("please enter user name");
                    Vibrator vibrator=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(400);
                    etUsername.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etPassword.getText())) {
                    etPassword.setError("please enter valid password");
                    etPassword.requestFocus();

                    return;
                }

            }
        });


    }


    private void login() {
        notificationManagerCompat = NotificationManagerCompat.from(this);
        CreateChannel channel = new CreateChannel(this);
        channel.createChannel();
        final String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        LoginBLL loginBLL = new LoginBLL();
     com.example.pizzadelivery.model.username Username = new username(username, password);
//
        UserAPI usersAPI = Url.getInstance().create(UserAPI.class);
        Call<SignUpResponse> usersCall = usersAPI.checklogin(Username);
        StrictModeClass.StrictMode();
        try {
            Response<SignUpResponse> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("Login success!")) {
                SharedPreferences sharedPreferences = getSharedPreferences("Pizza", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", "Bearer " + loginResponse.body().getToken());
                editor.commit();

                Url.token += loginResponse.body().getToken();
                Toast.makeText(LoginActivity.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
                Notification notification = new NotificationCompat.Builder(LoginActivity.this, CreateChannel.CHANNEL_1).
                        setSmallIcon(R.drawable.c5)
                        .setContentTitle("Login")
                        .setContentText("You are login successfully!!")
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();
                notificationManagerCompat.notify(1, notification);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("Userlogin", databaseList());
                startActivity(intent);

                finish();

            } else {

                Toast.makeText(this, "Username or password is incorrect", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();


        }


        if (loginBLL.checkUser(username, password)) {
            Notification notification = new NotificationCompat.Builder(LoginActivity.this, CreateChannel.CHANNEL_1).
                    setSmallIcon(R.drawable.c5)
                    .setContentTitle("Login")
                    .setContentText("You are login successfully!!")
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            notificationManagerCompat.notify(1, notification);
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("Userlogin", databaseList());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Either username or password is incorrect", Toast.LENGTH_SHORT).show();
            etUsername.requestFocus();
        }
    }


    SensorEventListener proximitySensorEventListener
            = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            WindowManager.LayoutParams params = LoginActivity.this.getWindow().getAttributes();
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

                if (event.values[0] == 0) {
                    params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    params.screenBrightness = 0;
                    getWindow().setAttributes(params);
                } else {
                    params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    params.screenBrightness = -1f;
                    getWindow().setAttributes(params);
                }
            }
        }
    };

}






