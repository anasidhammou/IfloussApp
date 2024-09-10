package com.edulab.e_commerceapp;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.edulab.e_commerceapp.Class.Livreur;
import com.edulab.e_commerceapp.Class.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class Livreur_inscription extends AppCompatActivity {
    private Spinner spinnerVille;
    public EditText edtPrenom, edtNom, edtEmail, edtPass, edtConfirPass, edtPhone;
    private FirebaseAuth mAuth;
    private String valuebase64;
    private ImageView imagEntreprise;
    private final int GALLERY_REQ_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livreur_inscription);
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
        spinnerVille=findViewById(R.id.spinner_ville);
        imagEntreprise=findViewById(R.id.photo_entreprise);
        initSpinnerOperateurs();
    }

    private void initSpinnerOperateurs() {
        String[] items = getResources().getStringArray(R.array.Ville);
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), R.layout.item_spinner_dropdown, items);
        spinnerVille.setAdapter(adapter);
        spinnerVille.callOnClick();
    }

    public void returnlogin(View view) {
        Intent i =new Intent(Livreur_inscription.this,MainActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    public void onBackPressed() {
        onBackClicked(null);
    }

    public void onBackClicked(View view) {
        Intent i =new Intent(Livreur_inscription.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void createlivaccount(View view) {
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
        }
        if(spinnerVille.getSelectedItem().toString().equals("إختر المدينة")){
            Toast.makeText(getApplicationContext(),"الرجاء اختيار مدينة عملك",Toast.LENGTH_LONG).show();
            return;
        }else{
            mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Livreur user = new Livreur(edtPrenom.getText().toString(),edtNom.getText().toString(),
                                        edtEmail.getText().toString(),edtPass.getText().toString(),
                                        edtConfirPass.getText().toString(),edtPhone.getText().toString(),
                                        "2",spinnerVille.getSelectedItem().toString(),valuebase64);
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
                                                                        Toast.makeText(Livreur_inscription.this, "لقد تم تسجيلك بنجاح", Toast.LENGTH_SHORT).show();
                                                                        edtPrenom.setText("");
                                                                        edtNom.setText("");
                                                                        edtEmail.setText("");
                                                                        edtPass.setText("");
                                                                        edtConfirPass.setText("");
                                                                        edtPhone.setText("");
                                                                        spinnerVille.setSelection(0);
                                                                        startActivity(new Intent(Livreur_inscription.this,MainActivity.class));
                                                                        finish();
                                                                    }else {
                                                                        Toast.makeText(Livreur_inscription.this, "عطب تقني", Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(Livreur_inscription.this,MainActivity.class));
                                                                        finish();
                                                                    }
                                                                }
                                                            });
                                                }else {
                                                    Toast.makeText(Livreur_inscription.this, "عطب تقني", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    });

        }


    }

    public void onupload(View view) {
        Intent iGallery=new Intent(Intent.ACTION_PICK);
        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iGallery,GALLERY_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                imagEntreprise.setImageURI(data.getData());

                Uri uri = data.getData();
                try {
                    Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),
                            uri);
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

                    byte[] bytes = stream.toByteArray();

                    valuebase64="data:image/jpeg;base64,"+ Base64.encodeToString(bytes,Base64.DEFAULT);

                }catch (Exception e){

                }
            }
        }
    }
}