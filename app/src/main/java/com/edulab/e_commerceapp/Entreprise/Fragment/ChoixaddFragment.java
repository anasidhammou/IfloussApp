package com.edulab.e_commerceapp.Entreprise.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.edulab.e_commerceapp.Entreprise.AjouterNouvelleHmizaActivity;
import com.edulab.e_commerceapp.Entreprise.AjouternouveauProduitActivity;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;


public class ChoixaddFragment extends Fragment {

    private Button btnprod,btnhmiza;
    private ImageView logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_choixadd, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        btnprod=view.findViewById(R.id.product);
        btnhmiza=view.findViewById(R.id.hmiza);

        btnprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AjouternouveauProduitActivity.class));
            }
        });

        btnhmiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AjouterNouvelleHmizaActivity.class));
            }
        });
        logout=view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

    }
}