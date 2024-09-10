package com.edulab.e_commerceapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edulab.e_commerceapp.Adapter.CustomAdapterEntreprise;
import com.edulab.e_commerceapp.Adapter.CustomListCommandeAdapter;
import com.edulab.e_commerceapp.Adapter.TotalAdapter;
import com.edulab.e_commerceapp.Class.CommandeClient;
import com.edulab.e_commerceapp.Class.Entreprise;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Class.totaluser;
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



public class HomeFragment extends Fragment {

    private ViewPager Totalviewpager;
    private TotalAdapter mAdapter;

    public ImageView imglogout;
    private FirebaseAuth mFirebaseAuth;
    DatabaseReference allcommande, Entrepriseref;
    List<CommandeClient> allcommandearray  = new ArrayList<>();

    private final ArrayList<totaluser> marraylist = new ArrayList();

    List<CommandeClient> allcommandearray2  = new ArrayList<>();
    List<Entreprise> EntrepriseArray  = new ArrayList<>();
    RecyclerView recyclerView, recyclerViewEntreprise;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private String Emailuser;
    private TextView txtnoprod;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        Entrepriseref= FirebaseDatabase.getInstance().getReference().child("Entreprise");

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null){
                    Emailuser=userProfile.email;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        findView(view);


        return view;
    }




    private void findView(View view) {
        txtnoprod=view.findViewById(R.id.noproduit);
        txtnoprod.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerViewEntreprise=view.findViewById(R.id.recycler_viewEntreprise);
        getlistcommande();
        getallEntreprise();
        mFirebaseAuth = FirebaseAuth.getInstance();
        imglogout=view.findViewById(R.id.btn_logout);
        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
    }

    private void initpager() {
        totaluser t1 = new totaluser("2"," مجموع المبيعات");
        totaluser t2 = new totaluser("0","الأرباح الكلية");
        totaluser t3 = new totaluser("0","المبيعات المشحنة بنجاح");
        totaluser t4 = new totaluser("1","المبيعات الملغاة");

        marraylist.add(t1);
        marraylist.add(t2);
        marraylist.add(t3);
        marraylist.add(t4);


      mAdapter = new TotalAdapter(getContext(),marraylist);
      Totalviewpager.setAdapter(mAdapter);
    }

    private void getlistcommande() {

        allcommandearray.clear();
        allcommandearray2.clear();
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null){
                    Emailuser=userProfile.email;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        allcommande= FirebaseDatabase.getInstance().getReference().child("Commande Client");
        allcommande.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    allcommandearray.add(postSnapshot.getValue(CommandeClient.class));
                }

                for (int i = 0; i < allcommandearray.size(); i++) {
                    if(Emailuser == null){
                        allcommandearray2.add(allcommandearray.get(i));
                    }else
                    if(allcommandearray.get(i).idClient.equals(Emailuser)){
                        allcommandearray2.add(allcommandearray.get(i));
                    }
                }

                Log.d("onDataChange", "onDataChange: "+allcommandearray2.size()+" "+Emailuser);

                if(allcommandearray.size()==0){
                    txtnoprod.setVisibility(View.VISIBLE);
                }else
                {
                    txtnoprod.setVisibility(View.GONE);
                    initializerecycler(allcommandearray2);
                }



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getallEntreprise() {
        EntrepriseArray.clear();
        Entrepriseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    EntrepriseArray.add(postSnapshot.getValue(Entreprise.class));
                }
                initializerecyclerEntreprise(EntrepriseArray);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializerecyclerEntreprise(List<Entreprise> entrepriseArray) {
        CustomAdapterEntreprise customAdapterEntreprise = new CustomAdapterEntreprise(getContext(),EntrepriseArray);
        recyclerViewEntreprise.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerViewEntreprise.setAdapter(customAdapterEntreprise);
        customAdapterEntreprise.notifyDataSetChanged();
        recyclerViewEntreprise.setClickable(false);
    }

    private void initializerecycler(List<CommandeClient> allcommandearray) {
        CustomListCommandeAdapter adapters=new CustomListCommandeAdapter(getContext(),allcommandearray);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapters);
        adapters.notifyDataSetChanged();
        adapters.setOnDataChangeListener(new CustomListCommandeAdapter.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {
                FragmentManager fragmentManager = getChildFragmentManager();
                Detail_commande detail_commande= Detail_commande.newInstance();
                detail_commande.setObject(object);
                detail_commande.showNow(fragmentManager, "OperationsAvenir");
            }
        });
    }


}