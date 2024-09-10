package com.edulab.e_commerceapp.Entreprise.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edulab.e_commerceapp.Adapter.CustomAdapterListLivreur;
import com.edulab.e_commerceapp.Class.Livreur;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_Livreur;
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


public class ListLivreurFragment extends Fragment {
    DatabaseReference useref;
    List<Livreur> usersArray  = new ArrayList<>();
    List<Livreur> FilterusersArray  = new ArrayList<>();
    private TextView txtnohmiza;
    private RecyclerView recyclerView;
    private ImageView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list_livreur, container, false);
        useref= FirebaseDatabase.getInstance().getReference().child("Users");
        txtnohmiza=view.findViewById(R.id.nolivreur);
        txtnohmiza.setVisibility(View.GONE);
        recyclerView=view.findViewById(R.id.recycler_view_livreur);
        logout=view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        getallUsers();
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

    private void initialiseRecycler(List<Livreur> filterusersArray) {
        CustomAdapterListLivreur adapterListLivreur=new CustomAdapterListLivreur(getContext(),filterusersArray);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapterListLivreur);
        adapterListLivreur.notifyDataSetChanged();
        adapterListLivreur.setOnDataChangeListener(new CustomAdapterListLivreur.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {
                FragmentManager fragmentManager = getChildFragmentManager();
                Detail_Livreur detail_livreur= Detail_Livreur.newInstance();
                detail_livreur.setObject(object);
                detail_livreur.showNow(fragmentManager, "OperationsAvenir");
            }
        });
    }


}