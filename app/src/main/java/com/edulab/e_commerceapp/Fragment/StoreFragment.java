package com.edulab.e_commerceapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edulab.e_commerceapp.Adapter.CustomAdapter;
import com.edulab.e_commerceapp.Adapter.CustomAdapterProduit;
import com.edulab.e_commerceapp.Class.Entreprise;
import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_claim;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_produitFragment;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class StoreFragment extends Fragment {
    public ImageView imglogout;
    List<Entreprise> EntrepriseArray  = new ArrayList<>();
    DatabaseReference Entrepriseref, Produitref;
    RecyclerView recyclerView, recyclerViewProduit;
    List<Produit> ProduitArray  = new ArrayList<>();
    List<Produit>itemsFiltredproduit=new ArrayList<>();
    private TextView nodata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_store, container, false);
        Entrepriseref= FirebaseDatabase.getInstance().getReference().child("Entreprise");
        Produitref= FirebaseDatabase.getInstance().getReference().child("Produit");
        getValueEntreprise();
        nodata=view.findViewById(R.id.noproduit);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerViewProduit=view.findViewById(R.id.recycler_view_produit);
        imglogout=view.findViewById(R.id.btn_logout);
        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        return view;
    }

    private void getValueEntreprise() {
        EntrepriseArray.clear();
        Entrepriseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    EntrepriseArray.add(postSnapshot.getValue(Entreprise.class));
                }
                initialiserecycler(EntrepriseArray);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialiserecycler(List<Entreprise> entrepriseArray) {
        CustomAdapter adapter=new CustomAdapter(getContext(),EntrepriseArray);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnDataChangeListener(new CustomAdapter.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {
                nodata.setVisibility(View.GONE);
                ProduitArray.clear();
                itemsFiltredproduit.clear();
                Produitref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            ProduitArray.add(postSnapshot.getValue(Produit.class));
                        }
                        Log.e("Foods found:",""+ProduitArray.get(0).Nom_Entreprise);


                        for(int i= 0;i<ProduitArray.size();i++){
                            if(ProduitArray.get(i).Nom_Entreprise.equals(object)){
                                itemsFiltredproduit.add(ProduitArray.get(i));
                            }


                        }
                        if(itemsFiltredproduit.size()==0){
                            nodata.setText("هذه الشركة لا تملك منتجات");
                            nodata.setVisibility(View.VISIBLE);
                        }else{
                            nodata.setVisibility(View.GONE);
                            CustomAdapterProduit adapterProduit=new CustomAdapterProduit(getContext(),itemsFiltredproduit);
                            recyclerViewProduit.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            recyclerViewProduit.setAdapter(adapterProduit);
                            adapterProduit.notifyDataSetChanged();
                            adapterProduit.setOnDataChangeListener(new CustomAdapterProduit.OnDataChangeListener() {
                                @Override
                                public void showDetail(Object object) {
                                    FragmentManager fragmentManager = getChildFragmentManager();
                                    Detail_produitFragment detail_produitFragment= Detail_produitFragment.newInstance();
                                    detail_produitFragment.setObject(object);
                                    detail_produitFragment.showNow(fragmentManager, "OperationsAvenir");
                                }
                            });
                        }



                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

}