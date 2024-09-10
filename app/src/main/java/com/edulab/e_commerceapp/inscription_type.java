package com.edulab.e_commerceapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.edulab.e_commerceapp.Entreprise.CreateEntrepriseActivity;

public class inscription_type extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_type);
    }

    public void admincreation(View view) {
        Intent i =new Intent(this,Admin_inscription.class);
        startActivity(i);
        finish();
    }

    public void livreurcreation(View view) {
        Intent i =new Intent(inscription_type.this,Livreur_inscription.class);
        startActivity(i);
        finish();
    }
    @Override
    public void onBackPressed() {
        onBackClicked(null);
    }

    public void onBackClicked(View view) {
        Intent i =new Intent(inscription_type.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void Entreprisecreation(View view) {
        Intent i =new Intent(inscription_type.this, CreateEntrepriseActivity.class);
        startActivity(i);
        finish();
    }


}