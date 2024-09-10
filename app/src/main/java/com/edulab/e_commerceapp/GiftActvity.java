package com.edulab.e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.edulab.e_commerceapp.Adapter.CustomAdapterGift;
import com.edulab.e_commerceapp.Adapter.CustomAdapterHmiza;
import com.edulab.e_commerceapp.Class.GiftUser;
import com.edulab.e_commerceapp.Class.hmiza;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_giftFragment;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_hmizaFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GiftActvity extends AppCompatActivity {

    public RecyclerView recyclerView;
    DatabaseReference giftref;
    List<GiftUser> listgift = new ArrayList<>();
    public ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_actvity);
        findView();
    }

    private void findView() {
        giftref= FirebaseDatabase.getInstance().getReference().child("gift");
        recyclerView=findViewById(R.id.recycler_view_gift);
        imgback=findViewById(R.id.phot_back);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GiftActvity.this,Home_Activity.class);
                startActivity(i);
            }
        });
        getallgift();
        initialiseRecycler();
    }

    private void initialiseRecycler() {
        CustomAdapterGift adapterGift = new CustomAdapterGift(getApplicationContext(),listgift);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(adapterGift);
        adapterGift.notifyDataSetChanged();
        adapterGift.setOnDataChangeListener(new CustomAdapterGift.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Detail_giftFragment detail_commande= Detail_giftFragment.newInstance();
                detail_commande.setObject(object);
                detail_commande.showNow(fragmentManager, "OperationsAvenir");
            }
        });
    }

    private void getallgift() {
        listgift.clear();
        giftref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    listgift.add(postSnapshot.getValue(GiftUser.class));
                }
                initialiseRecycler();
                Log.d("gift", "onDataChange: "+" "+listgift.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void logout(View view) {
        Intent i = new Intent(GiftActvity.this,MainActivity.class);
        startActivity(i);
    }


}