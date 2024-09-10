package com.edulab.e_commerceapp.Entreprise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Class.userInfo;
import com.edulab.e_commerceapp.Home_Activity;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.ProfilActvity;
import com.edulab.e_commerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfilEntrepriseActivity extends AppCompatActivity {

    private TextView completename, mail, whatsapp;
    private FirebaseUser user;
    private DatabaseReference reference;
    private DatabaseReference userInforef;
    List<userInfo> userinfoArray  = new ArrayList<>();
    private String userId;
    private final int GALLERY_REQ_CODE = 1000;
    private String valuebase64;
    private ImageView imagprofil;
    private EditText edtrib;
    private String type;
    private EditText edtnewpass,edtconfir;
    String mailvie;
    String codedata;
    private Spinner spinnerVille;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_entreprise);
        userInforef= FirebaseDatabase.getInstance().getReference().child("UserInfo");
        reference= FirebaseDatabase.getInstance().getReference("Users");
        user= FirebaseAuth.getInstance().getCurrentUser();
        userId=user.getUid();
        completename=findViewById(R.id.txt_complet);
        mail=findViewById(R.id.txt_Mail);
        whatsapp=findViewById(R.id.text_phoneW);
        imagprofil=findViewById(R.id.image);
        edtrib=findViewById(R.id.edt_rib);
        edtnewpass=findViewById(R.id.edt_newpass);
        edtconfir=findViewById(R.id.edt_confpass);
        spinnerVille=findViewById(R.id.spinner_ville);
        initSpinnerOperateurs();
        valueprofil();
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
                            edtrib.setText(userinfoArray.get(i).rib);
                            codedata=userinfoArray.get(i).photo.replace("data:image/jpeg;base64,","");
                            byte[] code = Base64.decode(codedata, Base64.DEFAULT);
                            Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
                            imagprofil.setImageBitmap(bitmap);
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initSpinnerOperateurs() {
        String[] items = getResources().getStringArray(R.array.bank);
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), R.layout.item_spinner_dropdown, items);
        spinnerVille.setAdapter(adapter);
        spinnerVille.callOnClick();
    }

    private void valueprofil() {

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null){
                    String Nom=userProfile.nom;
                    String Prenom = userProfile.prenom;


                    completename.setText(Prenom+" "+Nom);
                    mail.setText(userProfile.email);
                    whatsapp.setText(userProfile.phonewhatsapp);
                    type=userProfile.type;
                    Log.d("valeur", "onDataChangesd: "+" "+" "+userProfile.email);
                    getalluser(userProfile.email);





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

    public void validate(View view) {
        userInfo userss = new userInfo(valuebase64,completename.getText().toString(),mail.getText().toString()
                ,whatsapp.getText().toString(),edtrib.getText().toString(),spinnerVille.getSelectedItem().toString());
        userInforef.push().setValue(userss);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage("تمت معالجة طلبك بواسطة نظامنا")
                .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(ProfilEntrepriseActivity.this, HomeEntrepriseActivity.class));
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        startActivity(new Intent(ProfilEntrepriseActivity.this,HomeEntrepriseActivity.class));
                    }
                })
                .show();
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
                imagprofil.setImageURI(data.getData());

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

    public void backhome(View view) {
        startActivity(new Intent(ProfilEntrepriseActivity.this,HomeEntrepriseActivity.class));
    }

}