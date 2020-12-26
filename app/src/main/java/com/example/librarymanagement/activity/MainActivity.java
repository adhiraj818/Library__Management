package com.example.librarymanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.airbnb.lottie.LottieAnimationView;
import com.example.librarymanagement.R;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView lottie_View;
    
    public static SharedPreferences islogged_in,is_admin;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        islogged_in = getPreferences(MODE_PRIVATE);
        is_admin = getPreferences(MODE_PRIVATE);


        
        lottie_View = (LottieAnimationView) findViewById(R.id.welcome_screen);

        new CountDownTimer(3200, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                if(islogged_in.getBoolean("loggedin_check",false) == false){

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();

                }else{
                    Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        }.start();
        
    }
}