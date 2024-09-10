package com.edulab.e_commerceapp.Fragment.Blur;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edulab.e_commerceapp.Class.Livreur;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.R;

public class DetailUser extends DialogFragment {

    private Context context;
    private Object object;
    private ViewGroup root;
    private TextView txtname, txtmail, txtPhone,txtphone2;
    private LinearLayout btnCall;


    public DetailUser() {
    }


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object=object;
    }


    public static DetailUser newInstance() {
        DetailUser fragment = new DetailUser();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.EtsyBlurDialogTheme);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail_user, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        context = getContext();
        root = view.findViewById(R.id.lay_root);
        txtname=view.findViewById(R.id.text_nom_com);
        txtmail=view.findViewById(R.id.text_email_com);
        txtPhone=view.findViewById(R.id.text_phone_com);
        txtphone2=view.findViewById(R.id.text_phone_com2);
        btnCall=view.findViewById(R.id.btncall);

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
            User user=(User) object;
            txtname.setText(user.prenom+" "+user.nom);
            txtmail.setText(user.email);
            txtPhone.setText(user.phonewhatsapp);
            txtphone2.setText(user.phonewhatsapp);
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
              callPhoneNumber(user.phonewhatsapp);
                }
            });
        } catch (Exception e) {

        }
    }

    private void callPhoneNumber(final String phone) {
        int permissionCheckStorage = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE);
        if (permissionCheckStorage == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 0);
            }
            return;
        }

        if(phone == null || phone.isEmpty())return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // builder.setTitle(context.getString(R.string.log_out_alert_title));
        builder.setMessage(R.string.alert_to_confirm_call_phone_number);
        builder.setPositiveButton(context.getString(R.string.button_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + phone));
                            context.startActivity(callIntent);
                        } catch (Exception e) {

                        }
                    }
                });

        builder.setNegativeButton(context.getString(R.string.button_no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }



}