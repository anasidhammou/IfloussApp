package com.edulab.e_commerceapp.Entreprise.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edulab.e_commerceapp.Adapter.CustomAdapterHmiza;
import com.edulab.e_commerceapp.Adapter.CustomListProduitAdapterEntreprise;
import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Class.hmiza;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_hmizaFragment;
import com.edulab.e_commerceapp.Fragment.Blur.ProduitFragment;
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


public class ListProductFragment extends Fragment {
    private TextView txtnoprod,txtnohmiza;
    DatabaseReference Produiref;
    DatabaseReference HmizaRef;
    List<Produit> ProduitArray  = new ArrayList<>();
    List<Produit>itemsallValueproduit=new ArrayList<>();

    List<hmiza> HmizaArray  = new ArrayList<>();
    List<hmiza>itemsallValueHmiza=new ArrayList<>();

    public String name;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private RecyclerView recycleProd, recycleHmiza;
    private ImageView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list_product, container, false);
        Produiref= FirebaseDatabase.getInstance().getReference().child("Produit");
        HmizaRef= FirebaseDatabase.getInstance().getReference().child("Hmizate");
        txtnoprod=view.findViewById(R.id.noproduit);
        txtnohmiza=view.findViewById(R.id.nohmizaentre);
        recycleProd=view.findViewById(R.id.recycler_view_produit);
        recycleHmiza=view.findViewById(R.id.recycler_view_hmiza);
        txtnoprod.setVisibility(View.GONE);
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
        getallhmizate();
        return view;
    }

    private void getallhmizate() {
        HmizaArray.clear();
        itemsallValueHmiza.clear();
        HmizaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    HmizaArray.add(postSnapshot.getValue(hmiza.class));
                }

                for (int i = 0; i < HmizaArray.size(); i++) {
                    if(HmizaArray.get(i).Nomentre.equals(name)){
                        itemsallValueHmiza.add(HmizaArray.get(i));
                    }
                }
                if(itemsallValueHmiza.size()==0){
                    txtnoprod.setVisibility(View.VISIBLE);
                }else{

                    txtnoprod.setVisibility(View.GONE);
                    initrecyclehmiza(itemsallValueHmiza);
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
                    txtnoprod.setVisibility(View.VISIBLE);
                }else{

                    txtnoprod.setVisibility(View.GONE);
                    initrecycle(itemsallValueproduit);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initrecycle(List<Produit> itemsallValueproduit) {
        CustomListProduitAdapterEntreprise adapterHmiza=new CustomListProduitAdapterEntreprise(getContext(),itemsallValueproduit);
        recycleProd.setLayoutManager(new GridLayoutManager(getContext(),2));
        recycleProd.setAdapter(adapterHmiza);
        adapterHmiza.notifyDataSetChanged();
        adapterHmiza.setOnDataChangeListener(new CustomListProduitAdapterEntreprise.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {
                FragmentManager fragmentManager = getChildFragmentManager();
                ProduitFragment produitFragment= ProduitFragment.newInstance();
                produitFragment.setObject(object);
                produitFragment.showNow(fragmentManager, "OperationsAvenir");
            }
        });
    }

    private void initrecyclehmiza(List<hmiza> itemsallValueHmiza) {
        CustomAdapterHmiza adapterHmiza=new CustomAdapterHmiza(getContext(),itemsallValueHmiza);
        recycleHmiza.setLayoutManager(new GridLayoutManager(getContext(),2));
        recycleHmiza.setAdapter(adapterHmiza);
        adapterHmiza.notifyDataSetChanged();
        adapterHmiza.setOnDataChangeListener(new CustomAdapterHmiza.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {
                FragmentManager fragmentManager = getChildFragmentManager();
                HmizaFragmentDetail hmizaFragmentDetail = HmizaFragmentDetail.newInstance();
                hmizaFragmentDetail.setObject(object);
                hmizaFragmentDetail.showNow(fragmentManager, "OperationsAvenir");
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
}