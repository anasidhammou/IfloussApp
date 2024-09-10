package com.edulab.e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.edulab.e_commerceapp.Adapter.CustomListStatusAdapter;
import com.edulab.e_commerceapp.Adapter.CustomNotificationAdapter;
import com.edulab.e_commerceapp.Class.CommandeClient;
import com.edulab.e_commerceapp.Class.Notif;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_commande;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    DatabaseReference Notifref;
    private RecyclerView recyclerView;
    List<Notif> Allnotif  = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Notifref= FirebaseDatabase.getInstance().getReference().child("Notification");
        Allnotif.clear();
        findView();


    }

    @Override
    public void onBackPressed() {
        onBackClicked(null);
    }

    public void onBackClicked(View view) {
        Intent i =new Intent(this,Home_Activity.class);
        startActivity(i);
        finish();
    }

    private void getallnotif() {
        Allnotif.clear();
        Notifref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Allnotif.add(postSnapshot.getValue(Notif.class));
                }
                initrecycler();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void findView() {
        recyclerView=findViewById(R.id.recyclernotif);
        Allnotif.clear();
        getallnotif();

    }


    private void initrecycler() {
        CustomNotificationAdapter adapter=new CustomNotificationAdapter(this,Allnotif);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnDataChangeListener(new CustomNotificationAdapter.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Detail_notification detail_notification= Detail_notification.newInstance();
                detail_notification.setObject(object);
                detail_notification.showNow(fragmentManager, "OperationsAvenir");
            }
        });
    }

    public void logout(View view) {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}