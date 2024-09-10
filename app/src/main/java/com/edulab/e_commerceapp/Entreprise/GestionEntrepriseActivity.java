package com.edulab.e_commerceapp.Entreprise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.edulab.e_commerceapp.Adapter.CustomAdapter;
import com.edulab.e_commerceapp.Adapter.CustomListCommandeAdapter;
import com.edulab.e_commerceapp.Adapter.CustomListProduitAdapter;
import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GestionEntrepriseActivity extends AppCompatActivity {

    public RecyclerView recyclerview;
    public String name;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    DatabaseReference Produiref;
    List<Produit> ProduitArray  = new ArrayList<>();
    List<Produit>itemsallValueproduit=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_entreprise);
        recyclerview=findViewById(R.id.recycler_view);
        Produiref=FirebaseDatabase.getInstance().getReference().child("Produit");
        getusername();
        getallproduct();

    }



    private void getallproduct() {
        ProduitArray.clear();
     Produiref.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                 ProduitArray.add(postSnapshot.getValue(Produit.class));
             }

             for (int i = 0; i < ProduitArray.size(); i++) {
                 if(ProduitArray.get(i).Nom_Entreprise.equals(name)){
                     itemsallValueproduit.add(ProduitArray.get(i));
                 }
             }
             initrecycler();
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });
    }

    private void getusername() {
        name="";
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null){
                    name=userProfile.nom;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initrecycler() {
        CustomListProduitAdapter adapters=new CustomListProduitAdapter(this,itemsallValueproduit);
        recyclerview.setLayoutManager(new GridLayoutManager(this,2));
        recyclerview.setAdapter(adapters);
        adapters.notifyDataSetChanged();
    }

    public void logout(View view) {
        startActivity(new Intent(GestionEntrepriseActivity.this, MainActivity.class));
        finish();
    }

    public void addproduct(View view) {
        startActivity(new Intent(GestionEntrepriseActivity.this,AjouternouveauProduitActivity.class));
    }

    public void addHmiza(View view) {
        startActivity(new Intent(GestionEntrepriseActivity.this,AjouterNouvelleHmizaActivity.class));
    }
}