package com.edulab.e_commerceapp.Entreprise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.edulab.e_commerceapp.ClaimActivity;
import com.edulab.e_commerceapp.Class.Claim;
import com.edulab.e_commerceapp.Home_Activity;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class ClaimEntrepriseActivity extends AppCompatActivity {

    DatabaseReference claimref;
    private ImageView imagclaim;
    private EditText edtsubject, edtMessage,edtphone;
    private final int GALLERY_REQ_CODE = 1000;
    private String valuebas64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_entreprise);
        claimref= FirebaseDatabase.getInstance().getReference().child("Claim");
        edtsubject=findViewById(R.id.edt_subject);
        edtMessage=findViewById(R.id.edt_message);
        edtphone=findViewById(R.id.edt_phone);
        imagclaim=findViewById(R.id.photo_prod);
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
                imagclaim.setImageURI(data.getData());
                Uri uri = data.getData();
                try {
                    Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),
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

    public void addclaim(View view) {

        String valuesujet = edtsubject.getText().toString();
        String valuemessage=edtMessage.getText().toString();
        String valuephone=edtphone.getText().toString();
        Claim claim=new Claim(new Date(),valuesujet,valuemessage,valuephone,valuebas64);
        claimref.push().setValue(claim);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage("لقد تم تسجيل طلبكم سيتم الإتصال بك لإيجاد حل توافقي لمشكلتكم")
                .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(ClaimEntrepriseActivity.this,HomeEntrepriseActivity.class));
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        startActivity(new Intent(ClaimEntrepriseActivity.this,HomeEntrepriseActivity.class));

                    }
                })
                .show();


    }

    public void backhome(View view) {
        startActivity(new Intent(ClaimEntrepriseActivity.this,HomeEntrepriseActivity.class));
    }
}