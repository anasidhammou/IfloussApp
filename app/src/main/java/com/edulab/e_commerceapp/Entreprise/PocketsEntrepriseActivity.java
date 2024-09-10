package com.edulab.e_commerceapp.Entreprise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Class.historiquedemande;
import com.edulab.e_commerceapp.Class.userInfo;
import com.edulab.e_commerceapp.HistoriqueretraitActivity;
import com.edulab.e_commerceapp.Home_Activity;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.PocketsActivity;
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

public class PocketsEntrepriseActivity extends AppCompatActivity {

    private RadioButton rdbuttonPl,rdbuttonVir;
    private RadioGroup radioGroupCharacter;
    private TextView textcompteuser, textnameuser;
    private DatabaseReference reference;
    private DatabaseReference userInforef,histoinforef;
    private FirebaseUser user;
    private String userId;
    List<userInfo> userinfoArray  = new ArrayList<>();
    public int numdemande =0 ;
    public TextView amountcumule;
    public String typepayement;
    public String infopayement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pockets_entreprise);
        userInforef= FirebaseDatabase.getInstance().getReference().child("UserInfo");
        reference= FirebaseDatabase.getInstance().getReference("Users");
        histoinforef=FirebaseDatabase.getInstance().getReference("historique");
        user= FirebaseAuth.getInstance().getCurrentUser();
        userId=user.getUid();
        radioGroupCharacter=findViewById(R.id.radioGroup_character);
        rdbuttonPl=findViewById(R.id.radioButton_Pl);
        rdbuttonVir=findViewById(R.id.radioButton_virement);
        textcompteuser=findViewById(R.id.textname);
        textnameuser=findViewById(R.id.textcompte);
        amountcumule=findViewById(R.id.montantcumuler);
        amountcumule.setText("20 000 DH");

        rdbuttonVir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdbuttonPl.setChecked(false);
                typepayement="Wafacash";

                textcompteuser.setVisibility(View.VISIBLE);
                textnameuser.setVisibility(View.GONE);
                infopayement=textcompteuser.getText().toString();
            }
        });

        rdbuttonPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rdbuttonVir.setChecked(false);
                typepayement="virement";
                textnameuser.setVisibility(View.VISIBLE);
                textcompteuser.setVisibility(View.GONE);
                infopayement=textnameuser.getText().toString();
            }
        });
        valueprofil();
    }

    private void valueprofil() {

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null){
                    Log.d("valeur", "onDataChangesd: "+" "+" "+userProfile.email);
                    textcompteuser.setText(userProfile.prenom+" "+userProfile.nom);
                    getalluser(userProfile.email);





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getalluser(String email) {
        userinfoArray.clear();
        userInforef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    userinfoArray.add(postSnapshot.getValue(userInfo.class));
                }

                if(userinfoArray.size()==0){
                    return;
                }else{
                    for (int i = 0; i < userinfoArray.size(); i++) {
                        if(userinfoArray.get(i).emailuser.equals(email)){
                            textnameuser.setText(userinfoArray.get(i).rib+" "+userinfoArray.get(i).banque);

                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        onBackClicked(null);
    }

    public void onBackClicked(View view) {
        Intent i =new Intent(this, HomeEntrepriseActivity.class);
        startActivity(i);
        finish();
    }

    public void logout(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void retirer(View view) {
        LinearLayout linear = findViewById(R.id.retirecard);
        linear.setVisibility(View.VISIBLE);
    }

    public void historique(View view) {
        startActivity(new Intent(this, HistoriqueretraitActivity.class));
        finish();
    }


    public void validehisto(View view) {

        historiquedemande historiquedemande = new historiquedemande(numdemande++,amountcumule.getText().toString(),typepayement,infopayement);
        histoinforef.push().setValue(historiquedemande);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage("تمت معالجة طلبك بواسطة نظامنا")
                .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(PocketsEntrepriseActivity.this, HomeEntrepriseActivity.class));
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        startActivity(new Intent(PocketsEntrepriseActivity.this,HomeEntrepriseActivity.class));
                    }
                })
                .show();


    }

}