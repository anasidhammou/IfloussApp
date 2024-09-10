package com.edulab.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private Runnable mRunnable = null;
    private int splashDelayMills = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initRunnable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(mRunnable, splashDelayMills);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        removeCallbacks();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeCallbacks();
    }

    private void removeCallbacks() {
        try {
            handler.removeCallbacks(mRunnable);
        } catch (Exception e) {

        }
    }

    private void initRunnable() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {


                            startLoginActivity();

                }
            }
        };
    }

    private void startLoginActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }


}