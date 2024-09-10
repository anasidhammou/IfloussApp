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
import com.edulab.e_commerceapp.Class.hmiza;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_hmizaFragment;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ListHmizateFragment extends Fragment {
    DatabaseReference HmizaRef;
    public RecyclerView recyclerView;
    List<hmiza> listhmizate = new ArrayList<>();
    private TextView txtnohmiza;
    private ImageView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list_hmizate, container, false);
        HmizaRef= FirebaseDatabase.getInstance().getReference().child("Hmizate");
        findView(view);
        return view;
    }

    private void findView(View view) {
        recyclerView=view.findViewById(R.id.recycler_view_filtred);
        txtnohmiza = view.findViewById(R.id.nohmiza);
        txtnohmiza.setVisibility(View.GONE);
        getallhmizate();
        initialiseRecycler();
        logout=view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
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
                    txtnohmiza.setVisibility(View.GONE);
                    initialiseRecycler();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialiseRecycler() {
        CustomAdapterHmiza adapterHmiza=new CustomAdapterHmiza(getContext(),listhmizate);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapterHmiza);
        adapterHmiza.notifyDataSetChanged();
        adapterHmiza.setOnDataChangeListener(new CustomAdapterHmiza.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {
                FragmentManager fragmentManager = getChildFragmentManager();
                Detail_hmizaFragment detail_commande= Detail_hmizaFragment.newInstance();
                detail_commande.setObject(object);
                detail_commande.showNow(fragmentManager, "OperationsAvenir");
            }
        });
    }


}