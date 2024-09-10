package com.edulab.e_commerceapp.Entreprise.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edulab.e_commerceapp.Adapter.CustomAdapter;
import com.edulab.e_commerceapp.Adapter.CustomAdapterHmiza;
import com.edulab.e_commerceapp.Adapter.CustomAdapterListLivreur;
import com.edulab.e_commerceapp.Adapter.CustomListProduitAdapter;
import com.edulab.e_commerceapp.Adapter.CustomListVilleAdapter;
import com.edulab.e_commerceapp.Class.Livreur;
import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Class.Villestates;
import com.edulab.e_commerceapp.Class.hmiza;
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

public class HomeEntrepriseFragment extends Fragment {
    public RecyclerView recyclerViewProduit,recyclerViewHmizate;
    public String name;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    DatabaseReference Produiref;
    DatabaseReference HmizaRef,useref,villestate;

    List<Livreur> usersArray  = new ArrayList<>();
    List<Livreur> FilterusersArray  = new ArrayList<>();
    List<Villestates> VilleArray  = new ArrayList<>();

    List<Produit> ProduitArray  = new ArrayList<>();
    List<Produit>itemsallValueproduit=new ArrayList<>();
    private TextView txtnopr,txtnohmiza;
    List<hmiza> listhmizate = new ArrayList<>();

    private ImageView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_home_entreprise, container, false);
        Produiref=FirebaseDatabase.getInstance().getReference().child("Produit");
        HmizaRef= FirebaseDatabase.getInstance().getReference().child("Hmizate");
        useref= FirebaseDatabase.getInstance().getReference().child("Users");
        villestate=FirebaseDatabase.getInstance().getReference().child("VilleState");

        recyclerViewProduit=view.findViewById(R.id.recycler_view_produit);
        recyclerViewHmizate=view.findViewById(R.id.recycler_view_hmizate);
        txtnopr=view.findViewById(R.id.noproduit);
        txtnohmiza = view.findViewById(R.id.nohmiza);
        txtnopr.setVisibility(View.GONE);
        txtnohmiza.setVisibility(View.GONE);
        logout=view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        getusername();

        getallproduct();
        getallville();
        getallUsers();
        //getallhmizate();
        return view;
    }

    private void getallUsers() {
        usersArray.clear();
        FilterusersArray.clear();
        useref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    usersArray.add(postSnapshot.getValue(Livreur.class));
                }

                for (int i = 0; i < usersArray.size(); i++) {
                    if (usersArray.get(i).type.equals("2")){
                        FilterusersArray.add(usersArray.get(i));
                    }
                }

                if(FilterusersArray.size()==0){
                    txtnohmiza.setVisibility(View.VISIBLE);
                    return;
                }else {
                    txtnohmiza.setVisibility(View.GONE);
                    initialiseRecycler(FilterusersArray);
                }


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

    private void getallville() {
        VilleArray.clear();

        villestate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    VilleArray.add(postSnapshot.getValue(Villestates.class));
                }


                if(VilleArray.size()==0){
                    txtnohmiza.setVisibility(View.VISIBLE);
                }else{
                    txtnohmiza.setVisibility(View.GONE);
                    initrecyclerville(VilleArray);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void getallproduct() {
        ProduitArray.clear();
        itemsallValueproduit.clear();
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
                if(itemsallValueproduit.size()==0){
                    txtnopr.setVisibility(View.VISIBLE);
                }else{
                    txtnopr.setVisibility(View.GONE);
                    initrecycler(itemsallValueproduit);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getallhmizate() {
        listhmizate.clear();
        HmizaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    listhmizate.add(postSnapshot.getValue(hmiza.class));
                }
                if (listhmizate.size() == 0) {
                    txtnohmiza.setVisibility(View.VISIBLE);
                    return;
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialiseRecycler(List<Livreur> livreurList) {
        CustomAdapterListLivreur adapterListLivreur=new CustomAdapterListLivreur(getContext(),livreurList);
        recyclerViewHmizate.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerViewHmizate.setAdapter(adapterListLivreur);
        adapterListLivreur.notifyDataSetChanged();
        adapterListLivreur.setOnDataChangeListener(new CustomAdapterListLivreur.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {

            }
        });

    }

    private void initrecycler(List<Produit> itemsallValueproduit) {
        CustomListProduitAdapter adapters=new CustomListProduitAdapter(getContext(),itemsallValueproduit);
        recyclerViewProduit.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewProduit.setAdapter(adapters);
        adapters.notifyDataSetChanged();
    }

    private void initrecyclerville(List<Villestates> villeArray) {
        CustomListVilleAdapter adapters=new CustomListVilleAdapter(getContext(),villeArray);
        recyclerViewHmizate.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHmizate.setAdapter(adapters);
        adapters.notifyDataSetChanged();
    }
}