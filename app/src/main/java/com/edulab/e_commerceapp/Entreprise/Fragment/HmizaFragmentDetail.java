package com.edulab.e_commerceapp.Entreprise.Fragment;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.Class.hmiza;
import com.edulab.e_commerceapp.Entreprise.HomeEntrepriseActivity;
import com.edulab.e_commerceapp.Fragment.Blur.ProduitFragment;
import com.edulab.e_commerceapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class HmizaFragmentDetail extends DialogFragment {
    private Context context;
    private Object object;
    private ViewGroup root;
    public ImageView imagepr,imgback;
    private String codedata;
    public TextView nomhm, deschm,Prixhm;
    private LinearLayout delete;


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object=object;
    }


    public static HmizaFragmentDetail newInstance() {
        HmizaFragmentDetail fragment = new HmizaFragmentDetail();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.EtsyBlurDialogTheme);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hmiza_detail, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        context = getContext();
        imagepr=view.findViewById(R.id.phot_prod);
        nomhm=view.findViewById(R.id.text_nom_hmiza);
        deschm=view.findViewById(R.id.text_desc_hmiza);
        Prixhm=view.findViewById(R.id.text_price_hmiza);
        initData();
        delete=view.findViewById(R.id.btn_supprimer);
        imgback=view.findViewById(R.id.phot_back);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder
                        .setMessage("هل أنت متأكد أنك تريد حذف هذي الهميزة؟")
                        .setPositiveButton(context.getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                supprimer();
                            }
                        })
                        .setNegativeButton(context.getString(R.string.button_no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getView().setBackgroundResource(R.color.transparent);
                            dismiss();
                        }
                    });
                } catch (Exception e) {

                }
            }
        }, 800);


    }

    private void supprimer() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("Hmizate").orderByChild("Nom").equalTo(nomhm.getText().toString());

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder
                        .setMessage("تم حذف الهميزة")
                        .setPositiveButton(getContext().getString(R.string.button_ok), new DialogInterface.OnClickListener() {
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

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    private void initData() {
        if (object == null) return;
        try {
            hmiza hm=(hmiza) object;
            nomhm.setText(hm.Nom);
            Prixhm.setText("درهم"+" "+hm.Prix);
            deschm.setText(hm.Description);
            codedata=hm.Photo.replace("data:image/jpeg;base64,","");
            byte[] code = Base64.decode(codedata, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
            imagepr.setImageBitmap(bitmap);
        }catch (Exception e){

        }
    }

}