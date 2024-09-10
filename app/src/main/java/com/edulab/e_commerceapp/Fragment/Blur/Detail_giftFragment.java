package com.edulab.e_commerceapp.Fragment.Blur;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.edulab.e_commerceapp.Class.GiftUser;
import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.R;

public class Detail_giftFragment extends DialogFragment {
    private Context context;
    private Object object;
    private ViewGroup root;

    public ImageView imagepr,imgback;
    private String codedata;
    private TextView nomentr, nomprod, prixprod,descrprod,doonneprod,mokprod;
    private Button buyit;

    public Detail_giftFragment() {
    }


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object=object;
    }



    public static Detail_giftFragment newInstance() {
        Detail_giftFragment fragment = new Detail_giftFragment();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.EtsyBlurDialogTheme);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail_gift, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        context= getContext();
        root = view.findViewById(R.id.lay_root);
        imagepr=view.findViewById(R.id.phot_prod);
        nomentr = view.findViewById(R.id.text_entreprise);
        nomprod = view.findViewById(R.id.text_nom_prod);
        prixprod = view.findViewById(R.id.text_price);
        imgback=view.findViewById(R.id.phot_back);
        buyit=view.findViewById(R.id.buttonbuy);
        initData();



        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
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

    private void initData() {
        if (object == null) return;
        try {
            GiftUser giftUser = (GiftUser) object;
            nomentr.setText(giftUser.namegigt);
            nomprod .setText(giftUser.pointgift);
            codedata=giftUser.photogift.replace("data:image/jpeg;base64,","");
            byte[] code = Base64.decode(codedata, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
            imagepr.setImageBitmap(bitmap);
            prixprod.setText(giftUser.pointgift);

            buyit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder
                            .setMessage("تمت معالجة طلبك بواسطة نظامنا")
                            .setPositiveButton(getContext().getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    getActivity().onBackPressed();


                                }
                            })
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    getActivity().onBackPressed();
                                }
                            })
                            .show();
                }
            });

        } catch (Exception e) {

        }
    }
}