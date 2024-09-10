package com.edulab.e_commerceapp.Fragment.Blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edulab.e_commerceapp.Class.CommandeClient;
import com.edulab.e_commerceapp.Class.Livreur;
import com.edulab.e_commerceapp.R;

public class Detail_Livreur extends DialogFragment {

    private Context context;
    private Object object;
    private ViewGroup root;
    private TextView nomcomplet,mail,phone,ville;
    private ImageView img;
    private String codedata;

    public Detail_Livreur() {
    }


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object=object;
    }


    public static Detail_Livreur newInstance() {
        Detail_Livreur fragment = new Detail_Livreur();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.EtsyBlurDialogTheme);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail__livreur, container, false);
        findView(view);
        return view;
    }
    private void findView(View view) {
        context = getContext();
        root = view.findViewById(R.id.lay_root);
        nomcomplet=view.findViewById(R.id.text_nom_liv);
        mail=view.findViewById(R.id.text_email_liv);
        phone=view.findViewById(R.id.text_phone_liv);
        ville=view.findViewById(R.id.text_ville_live);
        img=view.findViewById(R.id.phot_liv);
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
    }
    private void initData() {
        if (object == null) return;
        try {
            Livreur livreur=(Livreur) object;
            nomcomplet.setText(livreur.prenom+" "+livreur.nom);
            mail.setText(livreur.email);
            phone.setText(livreur.phonewhatsapp);
            ville.setText(livreur.ville);
            codedata=livreur.photopersonnel.replace("data:image/jpeg;base64,","");
            byte[] code = Base64.decode(codedata, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
            img.setImageBitmap(bitmap);
        } catch (Exception E) {

        }
    }


}