package com.edulab.e_commerceapp.Fragment.Blur;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edulab.e_commerceapp.Admin.PrincipalAdminActivity;
import com.edulab.e_commerceapp.Admin.modifycommande;
import com.edulab.e_commerceapp.Class.CommandeClient;
import com.edulab.e_commerceapp.Class.hmiza;
import com.edulab.e_commerceapp.R;
import com.edulab.e_commerceapp.listbuyActivity;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashMap;

public class DetailCommandeAdmin extends DialogFragment {
    private Context context;
    private Object object;
    private ViewGroup root;
    public TextView nompr, nomentr, total, qte, nomclient, phoneclient, adresseclient, motif, state,txtphone2;
    public ImageView imagepr;
    private String codedata;

    private LinearLayout btnCall,btnmodify;
    DatabaseReference allcommande;


    public DetailCommandeAdmin() {
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object=object;
    }

    public static DetailCommandeAdmin newInstance() {
        DetailCommandeAdmin fragment = new DetailCommandeAdmin();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.EtsyBlurDialogTheme);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail_commande_admin, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        context= getContext();
        txtphone2=view.findViewById(R.id.text_phone_com2);
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
        btnCall=view.findViewById(R.id.btncall);
        btnmodify=view.findViewById(R.id.btnmodify);
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
            txtphone2.setText(commandeClient.Phone_Client);
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callPhoneNumber(commandeClient.Phone_Client);
                }
            });

            btnmodify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    // builder.setTitle(context.getString(R.string.log_out_alert_title));
                    builder.setMessage("هل تأكدت من صحة رقم الهاتف المسجل في الطلبية ؟");
                    builder.setPositiveButton(context.getString(R.string.button_ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {
                                        allcommande= FirebaseDatabase.getInstance().getReference().child("Commande Client");
                                        Query pendingTasks = allcommande.orderByChild("Nom_Entreprise").equalTo(commandeClient.Nom_Entreprise);
                                        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot tasksSnapshot) {
                                                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                                                    snapshot.getRef().child("etat").setValue("1");
                                                }
                                                Toast.makeText(context, "لقد تم تغيير حالة هذه الطلبية إلى \" تم تأكيد الطلبية \" بنجاح", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                startActivity(new Intent(getContext(), PrincipalAdminActivity.class));

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }

                                        });

                                    } catch (Exception e) {

                                    }
                                }
                            });

                    builder.setNegativeButton(context.getString(R.string.button_no),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Toast.makeText(context, "المرجو الإتصال بالرقم للتأكد من صحته", Toast.LENGTH_SHORT).show();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }catch (Exception e)
        {

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