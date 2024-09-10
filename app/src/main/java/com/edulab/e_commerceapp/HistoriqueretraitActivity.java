package com.edulab.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HistoriqueretraitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historiqueretrait);
    }

    @Override
    public void onBackPressed() {
        onBackClicked(null);
    }

    public void onBackClicked(View view) {
        Intent i =new Intent(this,Home_Activity.class);
        startActivity(i);
        finish();
    }

    public void logout(View view) {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}