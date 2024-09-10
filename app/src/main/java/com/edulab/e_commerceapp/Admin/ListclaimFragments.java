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

import com.edulab.e_commerceapp.Adapter.CustomClaimAdapter;
import com.edulab.e_commerceapp.Adapter.CustomNotificationAdapter;
import com.edulab.e_commerceapp.Class.Claim;
import com.edulab.e_commerceapp.Class.Entreprise;
import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_claim;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_notification;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ListclaimFragments extends Fragment {
    public ImageView imglogout;
    private RecyclerView recycleclaim;
    DatabaseReference claimref;
    List<Claim> ClaimArray  = new ArrayList<>();
    SwipeRefreshLayout swipLayout;
    TextView txtNoClaim;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_listclaim_fragments, container, false);
        recycleclaim=view.findViewById(R.id.recyclerclaim);
        txtNoClaim = view.findViewById(R.id.noClaim);
        claimref= FirebaseDatabase.getInstance().getReference().child("Claim");
        imglogout=view.findViewById(R.id.btn_logout);
        swipLayout = view.findViewById(R.id.swipe_container);
        swipLayout.setRefreshing(true);
        getallclaim();
        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        swipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getallclaim();
            }
        });

        return view;
    }

    private void getallclaim() {
        ClaimArray.clear();
        claimref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    ClaimArray.add(postSnapshot.getValue(Claim.class));
                }

                initialiserecycler(ClaimArray);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialiserecycler(List<Claim> claimArray) {

        swipLayout.setRefreshing(false);

        if(claimArray.isEmpty()){
            txtNoClaim.setVisibility(View.VISIBLE);
        }else{
            txtNoClaim.setVisibility(View.GONE);
        }

        CustomClaimAdapter adapter=new CustomClaimAdapter(getContext(),claimArray);
        recycleclaim.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycleclaim.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnDataChangeListener(new CustomClaimAdapter.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {
                FragmentManager fragmentManager = getChildFragmentManager();
                Detail_claim detail_notification= Detail_claim.newInstance();
                detail_notification.setObject(object);
                detail_notification.showNow(fragmentManager, "OperationsAvenir");
            }
        });
    }
}