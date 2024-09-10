package com.edulab.e_commerceapp;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.edulab.e_commerceapp.Class.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Admin_inscription extends AppCompatActivity {

    public EditText edtPrenom, edtNom, edtEmail, edtPass, edtConfirPass, edtPhone;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inscription);
        findView();
        mAuth=FirebaseAuth.getInstance();
    }

    private void findView() {
        edtPrenom=findViewById(R.id.edt_prenom);
        edtNom   =findViewById(R.id.edt_nom);
        edtEmail =findViewById(R.id.edt_email);
        edtPass  =findViewById(R.id.edt_pass);
        edtConfirPass = findViewById(R.id.edt_confir_pass);
        edtPhone = findViewById(R.id.edt_phone);
    }

    public void returnlogin(View view) {
        Intent i =new Intent(Admin_inscription.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        onBackClicked(null);
    }

    public void onBackClicked(View view){
        Intent i =new Intent(Admin_inscription.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void createaccount(View view) {
       if(edtPrenom.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"المرجو إدخال الإسم الشخصي",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtNom.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"المرجو إدخال الإسم العائلي",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtEmail.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"المرجو إدخال البريد الإلكتروني",Toast.LENGTH_LONG).show();
            return;
        }else
        if(!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()){
            Toast.makeText(getApplicationContext(),"المرجو إدخال بريد الإلكتروني صحيح",Toast.LENGTH_LONG).show();
            return;
        } else
        if(edtPass.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"المرجو إدخال كلمة سر",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtPass.getText().toString().length()<6){
            Toast.makeText(getApplicationContext(),"المرجو إدخال كلمة سر تتكون من أكتر من 6 أحرف",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtConfirPass.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"المرجو إدخال تأكيد كلمة سر",Toast.LENGTH_LONG).show();
            return;
        }else
        if(!edtPass.getText().toString().matches(edtConfirPass.getText().toString())){
            Toast.makeText(getApplicationContext(),"المرجو إدخال تأكيد كلمة سر تطابق كلمة السر",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtPhone.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"المرجو إدخال رقم هاتف الواتساب",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtPhone.getText().toString().length()<10){
            Toast.makeText(getApplicationContext(),"المرجو إدخال رقم هاتف الواتساب صحيح يتكون من 10 أرقام",Toast.LENGTH_LONG).show();
            return;
        }else{
            mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                User user = new User(edtPrenom.getText().toString(),edtNom.getText().toString(),
                                        edtEmail.getText().toString(),edtPass.getText().toString(),
                                        edtConfirPass.getText().toString(),edtPhone.getText().toString(),
                                        "1" +
                                                "");
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                               if(task.isSuccessful()){
                                                   mAuth.getCurrentUser().sendEmailVerification()
                                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                       @Override
                                                                       public void onComplete(@NonNull Task<Void> task) {
                                                                           if(task.isSuccessful()){
                                                                               Toast.makeText(Admin_inscription.this, "لقد تم تسجيلك بنجاح,يرجى التحقق من بريدك الإلكتروني للتحقق من صحة", Toast.LENGTH_SHORT).show();
                                                                               edtPrenom.setText("");
                                                                               edtNom.setText("");
                                                                               edtEmail.setText("");
                                                                               edtPass.setText("");
                                                                               edtConfirPass.setText("");
                                                                               edtPhone.setText("");
                                                                               startActivity(new Intent(Admin_inscription.this,MainActivity.class));
                                                                               finish();
                                                                           }else {
                                                                               Toast.makeText(Admin_inscription.this, "عطب تقني", Toast.LENGTH_SHORT).show();

                                                                           }
                                                                       }
                                                                   });
                                               }else {
                                                   Toast.makeText(Admin_inscription.this, "عطب تقني", Toast.LENGTH_SHORT).show();
                                               }
                                            }
                                        });
                            }
                        }
                    });
        }


    }
}