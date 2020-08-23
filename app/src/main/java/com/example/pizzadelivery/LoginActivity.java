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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class LoginActivity extends AppCompatActivity {
    Button btnlogin;
    EditText etUsername, etPassword;
    TextView tvRegister;
    private NotificationManagerCompat notificationManagerCompat;
    private SensorManager sensorManager;
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnlogin = findViewById(R.id.btnlogin);

//        notificationManagerCompat= NotificationManagerCompat.from(this);
//        CreateChannel channel= new CreateChannel(this);
//        channel.createChannel();

       tvRegister=findViewById(R.id.tvRegister);
       sensorGyro();
      tvRegister.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
              startActivity(intent);
          }
      });

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

    private void sensorGyro(){
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        SensorEventListener sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.values[1]<0){
                   // login();
                    finish();
                }
                else if (event.values[1]>0){
                    startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        if (sensor !=null){
            sensorManager.registerListener(sensorEventListener,sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(this,"No sensor found", Toast.LENGTH_LONG).show();
        }
    }

    private void login() {
        notificationManagerCompat = NotificationManagerCompat.from(this);
        CreateChannel channel = new CreateChannel(this);
        channel.createChannel();
        final String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        LoginBLL loginBLL = new LoginBLL();
        com.apsara.pizzadelivery.model.username Username = new username(username, password);
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
                        setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                        .setContentTitle("Login")
                        .setContentText("You are login successfully!!")
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();
                notificationManagerCompat.notify(1, notification);
                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
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
                    setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                    .setContentTitle("Login")
                    .setContentText("You are login successfully!!")
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            notificationManagerCompat.notify(1, notification);
            Toast.makeText(LoginActivity.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("Userlogin", databaseList());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Either username or password is incorrect", Toast.LENGTH_SHORT).show();
            etUsername.requestFocus();
        }
    }

}






