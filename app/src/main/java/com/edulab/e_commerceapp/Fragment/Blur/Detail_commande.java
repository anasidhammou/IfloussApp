package com.edulab.e_commerceapp.Fragment.Blur;

import android.app.Activity;
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
import com.edulab.e_commerceapp.Fragment.ListCommandeFragment;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;


public class Detail_commande extends DialogFragment {
    private Context context;
    private Object object;
    private ViewGroup root;
    public TextView nompr, nomentr, total, qte, nomclient, phoneclient, adresseclient, motif, state,typepaiement;
    public ImageView imagepr,imgback;
    private String codedata;

    public Detail_commande() {
    }


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object=object;
    }


    public static Detail_commande newInstance() {
        Detail_commande fragment = new Detail_commande();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.EtsyBlurDialogTheme);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail_commande, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        context= getContext();
        root = view.findViewById(R.id.lay_root);
        nompr=view.findViewById(R.id.text_nom_prod);
        nomentr=view.findViewById(R.id.text_nom_entre);
        total=view.findViewById(R.id.text_total);
        qte=view.findViewById(R.id.text_qte);
        nomclient=view.findViewById(R.id.text_nom_client);
        phoneclient=view.findViewById(R.id.text_phone_client);
        adresseclient=view.findViewById(R.id.text_adresse_client);
        motif=view.findViewById(R.id.text_motif);
        imagepr=view.findViewById(R.id.phot_prod);
        state = view.findViewById(R.id.text_etat);
        typepaiement=view.findViewById(R.id.text_typepaiement);
        imgback=view.findViewById(R.id.phot_back);

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

    }

    private void initData(){
        if (object == null) return;
        try {
            CommandeClient commandeClient=(CommandeClient) object;
            nompr.setText(commandeClient.Nom_Produitn);
            nomentr.setText(commandeClient.Nom_Entreprise);
            total.setText(commandeClient.Total+" "+"درهم");
            qte.setText(commandeClient.qte);
            nomclient.setText(commandeClient.Nom_Client);
            phoneclient.setText(commandeClient.Phone_Client);
            adresseclient.setText(commandeClient.Adresse_Client);
            motif.setText(commandeClient.Comment);
            codedata=commandeClient.Photo_Produit.replace("data:image/jpeg;base64,","");
            byte[] code = Base64.decode(codedata, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
            imagepr.setImageBitmap(bitmap);
            if(commandeClient.etat.equals("0")){
                state.setText("في الانتظار");
            }else
            if (commandeClient.etat.equals("1")){
                state.setText("تم تأكيد الطلبية");
            }else
            if (commandeClient.etat.equals("2")){
                state.setText("تم الارسال");
            }else
            if (commandeClient.etat.equals("3")){
                state.setText("تم الشحن بنجاح");
            }else
            if (commandeClient.etat.equals("4")){
                state.setText("الغيت");
            }else
            if (commandeClient.etat.equals("5")){
                state.setText("لايجيب");
            }
            typepaiement.setText(commandeClient.typepaiement);

    }catch (Exception e)
        {

        }
    }

}