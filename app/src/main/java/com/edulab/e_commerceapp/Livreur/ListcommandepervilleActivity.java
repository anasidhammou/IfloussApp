package com.edulab.e_commerceapp.Livreur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.edulab.e_commerceapp.Adapter.CustomListCommandeAdapter;
import com.edulab.e_commerceapp.Class.CommandeClient;
import com.edulab.e_commerceapp.Class.Livreur;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Entreprise.AjouternouveauProduitActivity;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_commande;
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

public class ListcommandepervilleActivity extends AppCompatActivity {
    public String Ville;
    DatabaseReference allcommande;
    List<CommandeClient> allcommandearray = new ArrayList<>();
    List<CommandeClient> allcommandearrayfiltred = new ArrayList<>();
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private TextView txlt;
    private TextView txt_noCommande;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcommandeperville);
        allcommande = FirebaseDatabase.getInstance().getReference().child("Commande Client");

        txlt = findViewById(R.id.edtvill);
        txt_noCommande = findViewById(R.id.noCommande);
        recyclerView = findViewById(R.id.recycler_view);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Livreur userProfile = snapshot.getValue(Livreur.class);
                if (userProfile != null) {
                    Ville = userProfile.ville;
                    txlt.setText(Ville);
                    getallcommande();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getallcommande() {
        allcommandearray.clear();
        allcommande.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    allcommandearray.add(postSnapshot.getValue(CommandeClient.class));
                }
                for (int i = 0; i < allcommandearray.size(); i++) {
                    if (allcommandearray.get(i).Ville.equals(Ville)) {
                        if (allcommandearray.get(i).etat.equals("1") || allcommandearray.get(i).etat.equals("0") && allcommandearray.get(i).Ville.equals(Ville)) {
                            allcommandearrayfiltred.add(allcommandearray.get(i));
                        }
                    }
                }

                if (allcommandearrayfiltred.isEmpty()) {
                    txt_noCommande.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    txt_noCommande.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    initialiserecyclerdfilter(allcommandearrayfiltred);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void initialiserecyclerdfilter(List<CommandeClient> allcommandearrayfiltred) {
        CustomListCommandeAdapter adapters = new CustomListCommandeAdapter(this, allcommandearrayfiltred);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapters);
        adapters.notifyDataSetChanged();
        adapters.setOnDataChangeListener(new CustomListCommandeAdapter.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Detail_commande detail_commande = Detail_commande.newInstance();
                detail_commande.setObject(object);
                detail_commande.showNow(fragmentManager, "OperationsAvenir");
            }
        });


    }

    public void logout(View view) {
        startActivity(new Intent(ListcommandepervilleActivity.this, MainActivity.class));
        finish();
    }
}
