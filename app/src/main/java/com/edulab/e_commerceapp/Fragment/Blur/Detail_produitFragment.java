package com.edulab.e_commerceapp.Fragment.Blur;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.Class.hmiza;
import com.edulab.e_commerceapp.R;
import com.edulab.e_commerceapp.listbuyActivity;
import com.edulab.e_commerceapp.listbuyproductActivity;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;


public class Detail_produitFragment extends DialogFragment {
    private Context context;
    private Object object;
    private ViewGroup root;

    public Button btn,btnby;
    public ImageView imagepr,imgback;
    private String codedata;
    private TextView nomentr, nomprod, prixprod,descrprod,doonneprod,mokprod;
    private String nomproduc;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object=object;
    }

    public Detail_produitFragment() {
    }

    public static Detail_produitFragment newInstance() {
        Detail_produitFragment fragment = new Detail_produitFragment();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.EtsyBlurDialogTheme);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail_produit, container, false);
        findView(view);
        return view;
    }
    private void findView(View view) {
        context=getContext();
        imagepr=view.findViewById(R.id.phot_prod);
        nomentr = view.findViewById(R.id.text_entreprise);
        nomprod = view.findViewById(R.id.text_nom_prod);
        prixprod = view.findViewById(R.id.text_price);
        descrprod = view.findViewById(R.id.text_description);
        doonneprod = view.findViewById(R.id.price);
        mokprod = view.findViewById(R.id.text_donne);
        imgback=view.findViewById(R.id.phot_back);
        btn=view.findViewById(R.id.buttondown);
        btnby = view.findViewById(R.id.buttonbuy);



        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        initData();
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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFileFromBase64();
            }
        });
    }

    private void initData() {
        if (object == null) return;
        try {

            Produit produit = (Produit) object;
            nomentr.setText(produit.Nom_Entreprise);
            nomprod .setText(produit.Nom_Produit);
            nomproduc=produit.Nom_Produit;
            prixprod.setText(produit.PrixU_Produit+" "+"درهم");
            descrprod.setText("Description");
            doonneprod.setText(produit.commision+" "+"درهم");
            mokprod .setText("20 نقطة");
            codedata=produit.Photo_Produit.replace("data:image/jpeg;base64,","");
            byte[] code = Base64.decode(codedata, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
            imagepr.setImageBitmap(bitmap);

            btnby.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context, listbuyproductActivity.class);
                    Produit pr=new Produit(produit.Nom_Entreprise,produit.Nom_Produit,"",produit.PrixU_Produit,"","");
                   // hmiza hm = new hmiza(produit.Nom_Produit,produit.Nom_Produit,produit.PrixU_Produit,"",produit.Nom_Entreprise);
                   // Log.d("TAG", "onClick: "+" "+hm);
                    Gson gson=new Gson();
                    String value=gson.toJson(pr);
                    i.putExtra("commandes", value);
                    startActivity(i);
                }
            });
        } catch (Exception e) {

        }
    }



    private void downloadFileFromBase64() {
        try {
            File file = new File(Environment.getExternalStorageDirectory()+"/com/myApp/img/");
            if(!file.exists()){
                file.mkdirs();
                    String attachment = codedata;
                    byte[] byteArr = Base64.decode(attachment, Base64.DEFAULT);
                   // File f = new File(file.getAbsolutePath(),"sample.jpg");
                    File f2= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), nomproduc+".jpg");

                    f2.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f2);
                    fo.write(byteArr);
                    fo.close();
                    Toast.makeText(getContext(), "لقد تم تحميل صورتك جيدا", Toast.LENGTH_LONG).show();
                    dismiss();


            }}catch (Exception e){
            e.printStackTrace();
        }
    }


}