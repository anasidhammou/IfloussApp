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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Home_Activity;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;
import com.edulab.e_commerceapp.Tools.Dialogs;
import com.edulab.e_commerceapp.inscription_type;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AjouternouveauProduitActivity extends AppCompatActivity {

    private ImageView imagprod;
    private final int GALLERY_REQ_CODE = 1000;
    private String valuebas64;
    private EditText edtnom, edtprixU, edtqtestok,edtcomission;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private String Nomentre;
    DatabaseReference Produiref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouternouveau_produit);
        imagprod = findViewById(R.id.photo_prod);
        edtnom = findViewById(R.id.edt_nom_prod);
        edtprixU=findViewById(R.id.edt_prix_unitaire);
        edtqtestok=findViewById(R.id.edt_qte_stock);

        edtcomission=findViewById(R.id.edt_qte_commision);


        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null){
                 Nomentre=userProfile.nom;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void logout(View view) {
        startActivity(new Intent(AjouternouveauProduitActivity.this, MainActivity.class));
        finish();
    }

    public void onupload(View view) {
        Intent iGallery=new Intent(Intent.ACTION_PICK);
        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iGallery,GALLERY_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== RESULT_OK){
            if(requestCode == GALLERY_REQ_CODE){
                imagprod.setImageURI(data.getData());
                Uri uri = data.getData();
                try {
                    Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),
                            uri);
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

                    byte[] bytes = stream.toByteArray();

                    valuebas64="data:image/jpeg;base64,"+Base64.encodeToString(bytes,Base64.DEFAULT);

                }catch (Exception e){

                }

            }
        }

    }


    @Override
    public void onBackPressed() {
        onBackClicked(null);
    }

    public void onBackClicked(View view) {
        Intent i =new Intent(AjouternouveauProduitActivity.this,HomeEntrepriseActivity.class);
        startActivity(i);
        finish();
    }

    public void createproduit(View view) {
        Produiref=FirebaseDatabase.getInstance().getReference().child("Produit");
        String valuenompr=edtnom.getText().toString();
        String valueqtestock=edtqtestok.getText().toString();
        String valueprixu=edtprixU.getText().toString();
            String valuecom=edtcomission.getText().toString();
        Produit produit=new Produit(Nomentre,valuenompr,valueqtestock,valueprixu,valuebas64,valuecom);
        Produiref.push().setValue(produit);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage("لقد تم تسجيل طلبكم")
                .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(AjouternouveauProduitActivity.this,HomeEntrepriseActivity.class));
                        finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        startActivity(new Intent(AjouternouveauProduitActivity.this,HomeEntrepriseActivity.class));
                        finish();
                    }
                })
                .show();

    }

    public void backhome(View view) {
        startActivity(new Intent(AjouternouveauProduitActivity.this,HomeEntrepriseActivity.class));
        finish();
    }
}