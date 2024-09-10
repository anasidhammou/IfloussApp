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
import android.os.Parcelable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edulab.e_commerceapp.Class.hmiza;
import com.edulab.e_commerceapp.R;
import com.edulab.e_commerceapp.listbuyActivity;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;

public class Detail_hmizaFragment extends DialogFragment {
    private Context context;
    private Object object;
    private ViewGroup root;
    public ImageView imagepr,imgback;
    private String codedata;
    private TextView txtprice, txttitle, txtdescrip;
    private Button addtobuy;
    public Button btn,btnby;
    public String nomproduc;

    public Detail_hmizaFragment() {
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object=object;
    }

    public static Detail_hmizaFragment newInstance() {
        Detail_hmizaFragment fragment = new Detail_hmizaFragment();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.EtsyBlurDialogTheme);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail_hmiza, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        context= getContext();
        txtprice = view.findViewById(R.id.text_price);
        txttitle= view.findViewById(R.id.text_title);
        txtdescrip= view.findViewById(R.id.text_description);
        imagepr = view.findViewById(R.id.phot_prod);
        addtobuy=view.findViewById(R.id.button);

        btn=view.findViewById(R.id.buttondown);
        imgback=view.findViewById(R.id.phot_back);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        addtobuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, listbuyActivity.class);
                hmiza Hmiza = (hmiza) object;
                Gson gson=new Gson();
                String value=gson.toJson(Hmiza);
                i.putExtra("commande", value);
                startActivity(i);
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

    private void initData(){
        if (object == null) return;
        try {

            hmiza Hmiza = (hmiza) object;
            txtprice.setText(Hmiza.Prix);
            txttitle.setText(Hmiza.Nom);
            nomproduc=Hmiza.Nom;
            txtdescrip.setText(Hmiza.Description);
            codedata=Hmiza.Photo.replace("data:image/jpeg;base64,","");
            byte[] code = Base64.decode(codedata, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
            imagepr.setImageBitmap(bitmap);
        }catch (Exception e)
        {

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