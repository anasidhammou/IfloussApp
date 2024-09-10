package com.edulab.e_commerceapp.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.edulab.e_commerceapp.Adapter.CustomAdapter;
import com.edulab.e_commerceapp.Adapter.CustomListCommandeAdapter;
import com.edulab.e_commerceapp.Adapter.CustomListStatusAdapter;
import com.edulab.e_commerceapp.Class.CommandeClient;
import com.edulab.e_commerceapp.Class.Entreprise;
import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.Class.Status;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Fragment.Blur.Detail_commande;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;
import com.edulab.e_commerceapp.Tools.Dialogs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListCommandeFragment extends Fragment {
    List<String> listStatus = new ArrayList<String>();
    RecyclerView recyclerView, recyclerViewFiltred;
    List<Status> liststate = new ArrayList<>();
    List<CommandeClient> allcommandearray = new ArrayList<>();
    List<CommandeClient> allcommandearray2 = new ArrayList<>();
    List<CommandeClient> allcommandearrayfiltred = new ArrayList<>();
    DatabaseReference allcommande;
    private TextView nodata;
    private ScrollView scroolview;
    public ImageView imglogout;
    private CustomListStatusAdapter adapter;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private String Emailuser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_commande, container, false);

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

        liststate.clear();
        allcommande = FirebaseDatabase.getInstance().getReference().child("Commande Client");

        Status status0 = new Status("6", "الجميع");
        Status status1 = new Status("0", "في الانتظار");
        Status status2 = new Status("1", "تم تأكيد الطلبية");
        Status status4 = new Status("3", "تم الشحن بنجاح");
        Status status5 = new Status("4", "الغيت");
        Status status6 = new Status("5", "لايجيب");

        liststate.add(status0);
        liststate.add(status1);
        liststate.add(status2);
        liststate.add(status4);
        liststate.add(status5);
        liststate.add(status6);
        nodata = view.findViewById(R.id.noproduit);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerViewFiltred = view.findViewById(R.id.recycler_view_filtred);
        initialiserecycler();
        getallcommande();

        imglogout = view.findViewById(R.id.btn_logout);
        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        return view;
    }

    private void getallcommande() {
        allcommandearray.clear();
        allcommandearray2.clear();
        allcommande.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    allcommandearray.add(postSnapshot.getValue(CommandeClient.class));
                }
                Log.d("onDataChangess", "onDataChangess: "+allcommandearray.size()+" "+Emailuser);
                if (allcommandearray.size() == 0) {
                    nodata.setVisibility(View.VISIBLE);
                    nodata.setText("لا توجد مبيعات");

                    return;
                } else {
                    for (int i = 0; i < allcommandearray.size(); i++) {
                        if(Emailuser == null){
                            allcommandearray2.add(allcommandearray.get(i));
                        }else
                        if(allcommandearray.get(i).idClient.equals(Emailuser)){
                            allcommandearray2.add(allcommandearray.get(i));
                        }
                    }
                    nodata.setVisibility(View.GONE);
                    initialiserecyclerdfilter(allcommandearray2);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initialiserecycler() {
        adapter = new CustomListStatusAdapter(getContext(), liststate);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        adapter.setOnDataChangeListener(new CustomListStatusAdapter.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {

                if (object.equals("6")) {
                    getallcommande();
                } else {
                    allcommandearrayfiltred.clear();
                    for (int i = 0; i < allcommandearray2.size(); i++) {
                        if (allcommandearray2.get(i).etat.equals(object.toString())) {
                            allcommandearrayfiltred.add(allcommandearray2.get(i));
                        }
                    }

                    if (allcommandearrayfiltred.size() == 0) {
                        nodata.setVisibility(View.VISIBLE);
                        nodata.setText("لا توجد مبيعات");

                    } else {

                        nodata.setVisibility(View.GONE);
                        initialiserecyclerdfilter(allcommandearrayfiltred);
                    }
                }

            }
        });
    }


    private void initialiserecyclerdfilter(List<CommandeClient> allcommandearrayfiltred) {
        CustomListCommandeAdapter adapters = new CustomListCommandeAdapter(getContext(), allcommandearrayfiltred);
        recyclerViewFiltred.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewFiltred.setAdapter(adapters);
        adapters.notifyDataSetChanged();
        adapters.setOnDataChangeListener(new CustomListCommandeAdapter.OnDataChangeListener() {
            @Override
            public void showDetail(Object object) {
                FragmentManager fragmentManager = getChildFragmentManager();
                Detail_commande detail_commande = Detail_commande.newInstance();
                detail_commande.setObject(object);
                detail_commande.showNow(fragmentManager, "OperationsAvenir");
            }
        });


    }
}