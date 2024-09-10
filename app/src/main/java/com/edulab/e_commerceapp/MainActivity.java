package com.edulab.e_commerceapp;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.edulab.e_commerceapp.Admin.PrincipalAdminActivity;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Entreprise.GestionEntrepriseActivity;
import com.edulab.e_commerceapp.Entreprise.HomeEntrepriseActivity;
import com.edulab.e_commerceapp.Livreur.ListcommandepervilleActivity;
import com.edulab.e_commerceapp.Tools.AppSession;
import com.edulab.e_commerceapp.Tools.AppTools;
import com.edulab.e_commerceapp.Tools.Dialogs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
public EditText edtemail, edtpass;
public String valuemail, valuepass;
    private FirebaseAuth mAuth;
    private CheckBox checksouvenir;
    public AppSession session;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        session=new AppSession(this);
        mAuth=FirebaseAuth.getInstance();


    }

    private void findView() {
        edtemail=findViewById(R.id.edt_email);
        edtpass=findViewById(R.id.etPassword);
        checksouvenir=findViewById(R.id.check_Sesouvenirdemoi);
    }

    public void goinscription(View view) {
        Intent i =new Intent(MainActivity.this,inscription_type.class);
        startActivity(i);
        finish();
    }

    public void logiin(View view) {
        if (!AppTools.isOnline(this)) {
            Dialogs.alertDialog(this, "يرجى الاتصال باتصال بالإنترنت ، أو أن تكون أقرب عبر wifii أو اتصالك المحمول");
            return;
        }else{
            valuemail=edtemail.getText().toString();
            valuepass=edtpass.getText().toString();
            Log.d("login", "logiin: "+" "+valuemail+" "+valuepass);
            session.setClientID(valuemail);
            session.setPassword(valuepass);
            Log.d("login", "logiins: "+" "+session.getPassword()+" "+session.getClientID());
            if(valuemail.isEmpty()){
                Toast.makeText(getApplicationContext(),"المرجو إدخال البريد الإلكتروني",Toast.LENGTH_LONG).show();
                return;
            }else
            if(valuepass.isEmpty()){
                Toast.makeText(getApplicationContext(),"المرجو إدخال كلمة سر",Toast.LENGTH_LONG).show();
                return;
            }else{
                mAuth.signInWithEmailAndPassword(valuemail,valuepass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        user= FirebaseAuth.getInstance().getCurrentUser();
                                        reference= FirebaseDatabase.getInstance().getReference("Users");
                                        userId=user.getUid();
                                        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                User userProfile=snapshot.getValue(User.class);
                                                if(userProfile != null){
                                                    if(userProfile.type.equals("2")){
                                                        if(checksouvenir.isChecked()){

                                                            startActivity(new Intent(MainActivity.this, ListcommandepervilleActivity.class));
                                                        }else{
                                                            startActivity(new Intent(MainActivity.this, ListcommandepervilleActivity.class));
                                                        }

                                                    }else
                                                   if(userProfile.type.equals("1")){
                                                       if(checksouvenir.isChecked()){
                                                           startActivity(new Intent(MainActivity.this,Home_Activity.class));
                                                       }else{
                                                           startActivity(new Intent(MainActivity.this,Home_Activity.class));
                                                       }

                                                   }else
                                                   if(userProfile.type.equals("0"))
                                                   {
                                                       if(checksouvenir.isChecked()){

                                                           startActivity(new Intent(MainActivity.this, HomeEntrepriseActivity.class));
                                                       }else{
                                                           startActivity(new Intent(MainActivity.this, HomeEntrepriseActivity.class));
                                                       }

                                                   }
                                                   else
                                                       if(userProfile.type.equals("3")){
                                                           if(checksouvenir.isChecked()){

                                                               startActivity(new Intent(MainActivity.this, PrincipalAdminActivity.class));
                                                           }else{
                                                               startActivity(new Intent(MainActivity.this, PrincipalAdminActivity.class));
                                                           }

                                                       }

                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                            }
                                        });

                                }else{
                                    Toast.makeText(getApplicationContext(),"المرجو التأكد من المعلومات المكتوبة",Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        });


            }
        }
    }

    public void goforget(View view) {
        startActivity(new Intent(this,RecoverPasswordActivity.class));
        finish();
    }
}