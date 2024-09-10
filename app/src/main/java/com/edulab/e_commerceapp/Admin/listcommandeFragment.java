package com.edulab.e_commerceapp.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edulab.e_commerceapp.Adapter.CustomListCommandeAdapter;
import com.edulab.e_commerceapp.Class.CommandeClient;
import com.edulab.e_commerceapp.Fragment.Blur.DetailCommandeAdmin;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_commande;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class listcommandeFragment extends Fragment {
    DatabaseReference allcommande;
    private ImageView logout;
    List<CommandeClient> allcommandearray  = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView txtnoprod;
    SwipeRefreshLayout swipLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_listcommande, container, false);
        allcommande= FirebaseDatabase.getInstance().getReference().child("Commande Client");
        recyclerView=view.findViewById(R.id.recycle_commande);
        txtnoprod=view.findViewById(R.id.nocommande);
        txtnoprod.setVisibility(View.GONE);
        swipLayout = view.findViewById(R.id.swipe_container);
        swipLayout.setRefreshing(true);
        getlistcommande();
        logout=view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        swipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getlistcommande();
            }
        });

        return view;
    }

    private void getlistcommande() {
        allcommandearray.clear();
        allcommande.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    allcommandearray.add(postSnapshot.getValue(CommandeClient.class));
                }
                if(allcommandearray.isEmpty()){
                    txtnoprod.setVisibility(View.VISIBLE);
                    swipLayout.setRefreshing(false);
                }else
                {
                    swipLayout.setRefreshing(false);
                    txtnoprod.setVisibility(View.GONE);
                    initializerecycler(allcommandearray);


                }



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                DetailCommandeAdmin detail_commande= DetailCommandeAdmin.newInstance();
                detail_commande.setObject(object);
                detail_commande.showNow(fragmentManager, "OperationsAvenir");
            }
        });
    }

}