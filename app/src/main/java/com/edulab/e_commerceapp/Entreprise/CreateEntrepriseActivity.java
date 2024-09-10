package com.edulab.e_commerceapp.Entreprise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.edulab.e_commerceapp.Admin_inscription;
import com.edulab.e_commerceapp.Class.Entreprise;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;
import com.edulab.e_commerceapp.Tools.Dialogs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class CreateEntrepriseActivity extends AppCompatActivity {

    private Spinner spinnerVille;
    private EditText edtnom, edtmail, edtcategory, edtadress, edtville, edtpass, edtconfir, edtphone;
    private FirebaseAuth mAuth;
    private DatabaseReference entrepriseRef;
    private String valuebase64;
    private ImageView imagEntreprise;
    private final int GALLERY_REQ_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entreprise);
        findView();
        mAuth=FirebaseAuth.getInstance();
        entrepriseRef = FirebaseDatabase.getInstance().getReference().child("Entreprise");

    }

    private void findView() {
        edtnom=findViewById(R.id.edt_nom_entre);
        edtmail=findViewById(R.id.edt_email);
        edtcategory=findViewById(R.id.edt_catégorise);
        edtadress=findViewById(R.id.edt_adresse);
        spinnerVille=findViewById(R.id.spinner_ville);
        edtpass=findViewById(R.id.edt_pass);
        edtconfir=findViewById(R.id.edt_confir_pass);
        edtphone=findViewById(R.id.edt_phone);
        imagEntreprise=findViewById(R.id.photo_entreprise);
        initSpinnerOperateurs();
    }

    private void initSpinnerOperateurs() {
        String[] items = getResources().getStringArray(R.array.Ville);
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), R.layout.item_spinner_dropdown, items);
        spinnerVille.setAdapter(adapter);
        spinnerVille.callOnClick();
    }

    public void createaccount(View view) {

        String valuenom=edtnom.getText().toString();
        String valueemail=edtmail.getText().toString();
        String valueCategory=edtcategory.getText().toString();
        String valueAdresse=edtadress.getText().toString();
        String valueVille=spinnerVille.getSelectedItem().toString();
        String valuePass=edtpass.getText().toString();
        String valueConfir=edtconfir.getText().toString();
        String valuePhone=edtphone.getText().toString();



        if(valuenom.isEmpty()){
            Toast.makeText(getApplicationContext(),"المرجو إدخال الإسم الكامل للشركة",Toast.LENGTH_LONG).show();
            return;
        }else
            if(valueemail.isEmpty()){
                Toast.makeText(getApplicationContext(),"المرجو إدخال البريد الإلكتروني",Toast.LENGTH_LONG).show();
                return;
            }else
                if(!Patterns.EMAIL_ADDRESS.matcher(valueemail).matches()){
                    Toast.makeText(getApplicationContext(),"المرجو إدخال بريد الإلكتروني صحيح",Toast.LENGTH_LONG).show();
                    return;
                }else
                    if(valueCategory.isEmpty()){
                        Toast.makeText(getApplicationContext(),"المرجو إدخال الفئة",Toast.LENGTH_LONG).show();
                        return;
                    }else
                        if(valueAdresse.isEmpty()){
                            Toast.makeText(getApplicationContext(),"المرجو إدخال الإسم العنوان",Toast.LENGTH_LONG).show();
                            return;
                        }else
                            if(valueVille.equals("إختر المدينة")){
                                Toast.makeText(getApplicationContext(),"المرجو اختيار المدينة",Toast.LENGTH_LONG).show();
                                return;
                            }else
                                if(valuePass.isEmpty()){
                                    Toast.makeText(getApplicationContext(),"المرجو إدخال كلمة سر",Toast.LENGTH_LONG).show();
                                    return;
                                }else
                                    if(valuePass.length()<6){
                                        Toast.makeText(getApplicationContext(),"المرجو إدخال كلمة سر تتكون من أكتر من 6 أحرف",Toast.LENGTH_LONG).show();
                                        return;
                                    }else
                                        if(valueConfir.isEmpty()){
                                            Toast.makeText(getApplicationContext(),"المرجو إدخال تأكيد كلمة سر",Toast.LENGTH_LONG).show();
                                            return;
                                        }else
                                            if(!valuePass.equals(valueConfir)){
                                                Toast.makeText(getApplicationContext(),"المرجو إدخال تأكيد كلمة سر تطابق كلمة السر",Toast.LENGTH_LONG).show();
                                                return;
                                            }else
                                                if(valuePhone.isEmpty()){
                                                    Toast.makeText(getApplicationContext(),"المرجو إدخال رقم هاتف الواتساب",Toast.LENGTH_LONG).show();
                                                    return;
                                                }else
                                                    if(valuePhone.length()<10){
                                                        Toast.makeText(getApplicationContext(),"المرجو إدخال رقم هاتف الواتساب صحيح يتكون من 10 أرقام",Toast.LENGTH_LONG).show();
                                                        return;
                                                    }else {
                                                            mAuth.createUserWithEmailAndPassword(valueemail,valuePass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                    if(task.isSuccessful()){
                                                                        User user = new User(valuenom, valuenom, valueemail, valuePass, valueConfir, valuePhone,
                                                                                "0");
                                                                        FirebaseDatabase.getInstance().getReference("Users")
                                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                                                                setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if(task.isSuccessful()){
                                                                                            mAuth.getCurrentUser().sendEmailVerification()
                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                            if(task.isSuccessful()){
                                                                                                                Entreprise entreprise = new Entreprise("",valuenom,valuebase64);
                                                                                                                entrepriseRef.push().setValue(entreprise);
                                                                                                                Toast.makeText(CreateEntrepriseActivity.this, "لقد تم تسجيلك بنجاح", Toast.LENGTH_SHORT).show();
                                                                                                                edtnom.setText("");
                                                                                                                edtmail.setText("");
                                                                                                                edtcategory.setText("");
                                                                                                                edtadress.setText("");
                                                                                                                edtpass.setText("");
                                                                                                                edtconfir .setText("");
                                                                                                                edtphone.setText("");
                                                                                                                startActivity(new Intent(CreateEntrepriseActivity.this, MainActivity.class));
                                                                                                                finish();
                                                                                                            }else {
                                                                                                                Toast.makeText(CreateEntrepriseActivity.this, "عطب تقني", Toast.LENGTH_SHORT).show();
                                                                                                            }
                                                                                                        }
                                                                                                    });
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

                    valuebase64="data:image/jpeg;base64,"+Base64.encodeToString(bytes,Base64.DEFAULT);

                }catch (Exception e){

                }
            }
        }
    }
}