package com.edulab.e_commerceapp.Entreprise.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.Entreprise.AjouternouveauProduitActivity;
import com.edulab.e_commerceapp.Entreprise.GestionEntrepriseActivity;
import com.edulab.e_commerceapp.Entreprise.HomeEntrepriseActivity;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class AddProductFragment extends Fragment {
    private ImageView imagprod;
    private final int GALLERY_REQ_CODE = 1000;
    private String valuebas64;
    private EditText edtnom, edtprixU, edtqtestok,edtcommision;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private String Nomentre;
    DatabaseReference Produiref;
    private Button btncreat;
    private ImageView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        imagprod = view.findViewById(R.id.photo_prod);
        edtnom = view.findViewById(R.id.edt_nom_prod);
        edtprixU=view.findViewById(R.id.edt_prix_unitaire);
        edtqtestok=view.findViewById(R.id.edt_qte_stock);
        btncreat=view.findViewById(R.id.button);

        edtcommision=view.findViewById(R.id.edt_qte_commision);

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
        imagprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onupload();
            }
        });

        btncreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createproduit();
            }
        });

        logout=view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        return view;
    }

    public void onupload() {
        Intent iGallery=new Intent(Intent.ACTION_PICK);
        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iGallery,GALLERY_REQ_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== RESULT_OK){
            if(requestCode == GALLERY_REQ_CODE){
                imagprod.setImageURI(data.getData());
                Uri uri = data.getData();
                try {
                    Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),
                            uri);
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

                    byte[] bytes = stream.toByteArray();

                    valuebas64="data:image/jpeg;base64,"+ Base64.encodeToString(bytes,Base64.DEFAULT);

                }catch (Exception e){

                }

            }
        }

    }


    public void createproduit() {
        Produiref=FirebaseDatabase.getInstance().getReference().child("Produit");
        String valuenompr=edtnom.getText().toString();
        String valueqtestock=edtqtestok.getText().toString();
        String valueprixu=edtprixU.getText().toString();
        String valueComission=edtcommision.getText().toString();
        Produit produit=new Produit(Nomentre,valuenompr,valueqtestock,valueprixu,valuebas64,valueComission);
        Produiref.push().setValue(produit);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setMessage("لقد تم تسجيل طلبكم")
                .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(getContext(), HomeEntrepriseActivity.class));
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        startActivity(new Intent(getContext(), HomeEntrepriseActivity.class));
                    }
                })
                .show();

    }

}