package com.edulab.e_commerceapp.Fragment.Blur;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edulab.e_commerceapp.Class.Claim;
import com.edulab.e_commerceapp.Class.Notif;
import com.edulab.e_commerceapp.R;

import java.text.SimpleDateFormat;


public class Detail_claim extends DialogFragment {
    private Context context;
    private Object object;
    private ViewGroup root;
    public TextView datenotif, titlenotif, descriptionnotif;
    private SimpleDateFormat simpleDateFormat;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object=object;
    }

    public Detail_claim() {
    }

    public static Detail_claim newInstance() {
        Detail_claim fragment = new Detail_claim();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.EtsyBlurDialogTheme);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail_claim, container, false);
        findView(view);
   return view;
    }


    private void findView(View view) {
        context=getContext();
        datenotif=view.findViewById(R.id.text_date_notif);
        titlenotif=view.findViewById(R.id.text_title_notif);
        descriptionnotif=view.findViewById(R.id.text_descr_notif);
        root = view.findViewById(R.id.lay_root);

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
            Claim notif=(Claim) object;
            simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String dateTime = simpleDateFormat.format(notif.dateclaim).toString();
            datenotif.setText(dateTime);
            titlenotif.setText(notif.Subject);
            descriptionnotif.setText(notif.Message);
        }catch (Exception e){

        }
    }

}