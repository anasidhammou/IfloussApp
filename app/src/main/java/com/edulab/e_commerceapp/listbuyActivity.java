package com.edulab.e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edulab.e_commerceapp.Class.CommandeClient;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Class.hmiza;
import com.edulab.e_commerceapp.Entreprise.HomeEntrepriseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class listbuyActivity extends AppCompatActivity {

    private ImageView imgprod;
    private TextView txtentr,txttitle,txtprice;
    public String Hmiza;
    private String codedata;
    private DatabaseReference CommandeClientref;
    hmiza value;
    private Spinner spinnerVille;
    private EditText edtnomclient,edtphoneclient,edtadressclient,edtmotif;
    public ImageView imgback;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private String Emailuser;
    private String valuchoseradio;
    private RadioButton rdbuttonPl,rdbuttonVir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbuy);
        CommandeClientref= FirebaseDatabase.getInstance().getReference().child("Commande Client");

        if(getIntent().getExtras() != null) {
             Hmiza = getIntent().getStringExtra("commande");
             Gson gsonc= new Gson();
            value=gsonc.fromJson(Hmiza,hmiza.class);
        }
        findView();
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
    }

    private void findView() {
        imgprod = findViewById(R.id.phot_prod);
        txtentr=findViewById(R.id.text_entreprise);
        txttitle=findViewById(R.id.text_nomprod);
        txtprice=findViewById(R.id.text_prixprod);
        spinnerVille=findViewById(R.id.spinner_ville);
        edtnomclient=findViewById(R.id.edt_nom_client);
        edtphoneclient=findViewById(R.id.edt_phone_client);
        edtadressclient=findViewById(R.id.edt_adresse_client);
        edtmotif=findViewById(R.id.edt_motif);
        rdbuttonPl=findViewById(R.id.radioButton_Pl);
        rdbuttonVir=findViewById(R.id.radioButton_virement);
        initdata();
        initSpinnerOperateurs();
        imgback=findViewById(R.id.phot_back);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(listbuyActivity.this,Home_Activity.class);
                startActivity(i);
            }
        });
        rdbuttonVir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typepaiement("الدفع عن طريق التحويل المصرفي");
            }
        });

        rdbuttonPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typepaiement("الدفع عند الاستلام");
            }
        });

    }


    private void typepaiement(String value) {
        valuchoseradio=value;
    }

    private void initdata() {
        codedata=value.Photo.replace("data:image/jpeg;base64,","");
        byte[] code = Base64.decode(codedata, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
        imgprod.setImageBitmap(bitmap);

        txtentr.setText(value.Nomentre);
        txttitle.setText(value.Description);
        txtprice.setText(value.Prix+" "+"DH");
    }

    private void initSpinnerOperateurs() {
        String[] items = getResources().getStringArray(R.array.Ville);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.item_spinner_dropdown, items);
        spinnerVille.setAdapter(adapter);
        spinnerVille.callOnClick();
    }


    public void onlogout(View view) {
        startActivity(new Intent(listbuyActivity.this,MainActivity.class));
        finish();
    }

    public void buyit(View view) {
        if(edtnomclient.getText().toString().trim().isEmpty() || edtnomclient.getText().toString().trim().equals("")) {
            Toast.makeText(this,"المرجو إدخال إسم الزبون",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtphoneclient.getText().toString().trim().isEmpty() || edtphoneclient.getText().toString().trim().equals("")){
            Toast.makeText(this,"المرجو إدخال هاتف الزبون",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtphoneclient.getText().toString().trim().length()<10){
            Toast.makeText(this,"المرجو إدخال رقم هاتف صحيح يتكون من 10 أرقام",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtadressclient.getText().toString().trim().isEmpty() || edtadressclient.getText().toString().trim().equals(""))
        {
            Toast.makeText(this,"المرجو إدخال عنوان الزبون",Toast.LENGTH_LONG).show();
            return;
        }else if(spinnerVille.getSelectedItem().toString().equals("إختر المدينة")){
            Toast.makeText(this,"الرجاء اختيار مدينة التسليم الخاصة بك",Toast.LENGTH_LONG).show();
            return;
        }else
        if(valuchoseradio==null  || valuchoseradio.equals("")){
            Toast.makeText(this,"شكرا لرغبتك في اختيار نوع الدفع الخاص بك",Toast.LENGTH_LONG).show();
            return;
        }else{
            CommandeClient commandeClient=new CommandeClient(value.Nomentre, value.Nom,value.Photo,value.Prix,"",edtnomclient.getText().toString(),edtphoneclient.getText().toString(),edtadressclient.getText().toString(),edtmotif.getText().toString(),spinnerVille.getSelectedItem().toString(),"0",Emailuser,valuchoseradio);
           CommandeClientref.push().setValue(commandeClient);
           if(valuchoseradio.equals("الدفع عند الاستلام")){
               AlertDialog.Builder builder = new AlertDialog.Builder(this);
               builder
                       .setMessage("تمت معالجة طلبك بواسطة نظامنا ، يرجى تذكر دفع ثمن طلبك عند التسليم")
                       .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                               startActivity(new Intent(listbuyActivity.this, Home_Activity.class));
                           }
                       })
                       .setOnCancelListener(new DialogInterface.OnCancelListener() {
                           @Override
                           public void onCancel(DialogInterface dialog) {
                               startActivity(new Intent(listbuyActivity.this,Home_Activity.class));
                           }
                       })
                       .show();
           }else {
               AlertDialog.Builder builder = new AlertDialog.Builder(this);
               builder
                       .setMessage("تمت معالجة طلبك بواسطة نظامنا\n" +
                               "لقبول طلبك ، يرجى إجراء التحويل في أسرع وقت ممكن إلى رقم الحساب التالي XXXXXXXXXXXX.\n" +
                               "وأرسل لنا الإيصال على الرقم التالي: 06.XX.XX.XX.XX")
                       .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                               startActivity(new Intent(listbuyActivity.this, Home_Activity.class));
                           }
                       })
                       .setOnCancelListener(new DialogInterface.OnCancelListener() {
                           @Override
                           public void onCancel(DialogInterface dialog) {
                               startActivity(new Intent(listbuyActivity.this,HomeEntrepriseActivity.class));
                           }
                       })
                       .show();
           }


        }
    }

}