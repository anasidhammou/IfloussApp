package com.edulab.e_commerceapp.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edulab.e_commerceapp.Adapter.CustomUserAdapter;
import com.edulab.e_commerceapp.Class.Livreur;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Fragment.Blur.DetailUser;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_Livreur;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listuserFragment extends Fragment {

    DatabaseReference useref;
    List<User> usersArray  = new ArrayList<>();
    List<User> FilterusersArray  = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageView logout;
    private TextView nouser;
    SwipeRefreshLayout swipLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_listuser, container, false);
        useref= FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView=view.findViewById(R.id.recycler_view_users);
        logout=view.findViewById(R.id.btn_logout);
        nouser=view.findViewById(R.id.nouser);
        nouser.setVisibility(View.GONE);
        swipLayout = view.findViewById(R.id.swipe_container);
        swipLayout.setRefreshing(true);
        getallUsers();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        swipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getallUsers();
            }
        });


        return view;
    }

    private void getallUsers() {
        usersArray.clear();
        FilterusersArray.clear();
        useref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    usersArray.add(postSnapshot.getValue(User.class));
                }

                for (int i = 0; i < usersArray.size(); i++) {
                    if (usersArray.get(i).type.equals("1")){
                        FilterusersArray.add(usersArray.get(i));
                    }
                }

                if(FilterusersArray.size()==0){
                    swipLayout.setRefreshing(false);
                    nouser.setVisibility(View.VISIBLE);
                    return;
                }else {
                    nouser.setVisibility(View.GONE);
                    initialiseRecycler(FilterusersArray);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialiseRecycler(List<User> filterusersArray) {
        swipLayout.setRefreshing(false);
        CustomUserAdapter customUserAdapter=new CustomUserAdapter(getContext(),filterusersArray);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(customUserAdapter);
        customUserAdapter.notifyDataSetChanged();
        customUserAdapter.setOnDataChangeListener(new CustomUserAdapter.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {
                FragmentManager fragmentManager = getChildFragmentManager();
                DetailUser detail_livreur= DetailUser.newInstance();
                detail_livreur.setObject(object);
                detail_livreur.showNow(fragmentManager, "OperationsAvenir");
            }
        });
    }

}