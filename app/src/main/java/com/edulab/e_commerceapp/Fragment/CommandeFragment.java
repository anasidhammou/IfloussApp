package com.edulab.e_commerceapp.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.edulab.e_commerceapp.Class.CommandeClient;
import com.edulab.e_commerceapp.Class.Entreprise;
import com.edulab.e_commerceapp.Class.IOnBackPressed;
import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.MainActivity;
import com.edulab.e_commerceapp.R;
import com.edulab.e_commerceapp.Tools.Dialogs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CommandeFragment extends Fragment implements IOnBackPressed {
    private Spinner spinnerproduit;
    private Spinner spinnertypepaye;
    DatabaseReference Entrepriseref , Produitref, CommandeClientref;
    List<Entreprise> EntrepriseArray  = new ArrayList<>();
    List<String>itemsValue=new ArrayList<>();
    public ImageView imglogout;
    private String qterestante;
    private RadioButton rdbuttonPl,rdbuttonVir;

    List<Produit> ProduitArray  = new ArrayList<>();
    List<String>itemsValueproduit=new ArrayList<>();
    List<Produit>itemsallValueproduit=new ArrayList<>();
    public String selectedENtre;
    private EditText edttotal,edtqte,edtnomclient,edtphoneclient,edtadressclient,edtmotif;
    private Button btncommande;
     String total;
     Integer sum;
     private String urlphoto="";
    private Spinner spinnerVille;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private String Emailuser;
    private RadioGroup radioGroupCharacter;
    private String valuchoseradio;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_commande, container, false);
        Entrepriseref= FirebaseDatabase.getInstance().getReference().child("Entreprise");
        Produitref= FirebaseDatabase.getInstance().getReference().child("Produit");
        CommandeClientref=FirebaseDatabase.getInstance().getReference().child("Commande Client");
        findView(view);

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if(userProfile != null){
                    Emailuser=userProfile.email;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinnerproduit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                itemsValueproduit.clear();
                selectedENtre=spinnerproduit.getSelectedItem().toString();
                getvalueprod(spinnerproduit.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinnertypepaye.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setvalueprix(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        return view;
    }



    private void findView(View view) {
        radioGroupCharacter=view.findViewById(R.id.radioGroup_character);
        spinnerproduit=view.findViewById(R.id.spinner_produit);
        spinnertypepaye=view.findViewById(R.id.spinner_type_paye);
        edttotal=view.findViewById(R.id.edt_total);
        edtqte=view.findViewById(R.id.edt_qte);
        edtnomclient=view.findViewById(R.id.edt_nom_client);
        edtphoneclient=view.findViewById(R.id.edt_phone_client);
        edtadressclient=view.findViewById(R.id.edt_adresse_client);
        edtmotif=view.findViewById(R.id.edt_motif);
        btncommande=view.findViewById(R.id.commander);
        imglogout=view.findViewById(R.id.btn_logout);
        spinnerVille=view.findViewById(R.id.spinner_ville);
        initSpinnerOperateurs();
        rdbuttonPl=view.findViewById(R.id.radioButton_Pl);
        rdbuttonVir=view.findViewById(R.id.radioButton_virement);
        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });


        rdbuttonVir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typepaiement("الدفع عن طريق التحويل المصرفي");
            }
        });

        rdbuttonPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typepaiement("الدفع عند الاستلام");
            }
        });

        edtqte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

              if(editable.toString().isEmpty() || editable.toString()==""){
                  edttotal.setText("");
                  edttotal.setText(total);
              }else{
                  sum=Integer.parseInt(total)*Integer.parseInt(editable.toString());
                  edttotal.setText(sum.toString());
              }

            }
        });
        getvaluedrop();

        btncommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              commandeproduct();
            }
        });
    }



    private void typepaiement(String value) {
        valuchoseradio=value;
    }


    private void initSpinnerOperateurs() {
        String[] items = getResources().getStringArray(R.array.Ville);
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), R.layout.item_spinner_dropdown, items);
        spinnerVille.setAdapter(adapter);
        spinnerVille.callOnClick();
    }

    private String geturlphoto(String value) {
        for (int i = 0; i < ProduitArray.size(); i++) {
            if(ProduitArray.get(i).Nom_Produit.equals(value)){
                urlphoto=ProduitArray.get(i).Photo_Produit;
            }
        }
        return urlphoto;
    }


    public void getvaluedrop(){
        itemsValue.clear();
        Entrepriseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    EntrepriseArray.add(postSnapshot.getValue(Entreprise.class));
                }
                Log.e("Foods found:",""+EntrepriseArray.get(0).Nom_Entreprise);


                for(int i= 0;i<EntrepriseArray.size();i++){
                    itemsValue.add(EntrepriseArray.get(i).Nom_Entreprise);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), R.layout.item_spinner_dropdown, itemsValue);
                spinnerproduit.setAdapter(adapter);
                spinnerproduit.callOnClick();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getvalueprod(String value){
        ProduitArray.clear();
        itemsallValueproduit.clear();
        itemsValueproduit.clear();
        Produitref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    ProduitArray.add(postSnapshot.getValue(Produit.class));
                }
                Log.e("Foods found:",""+ProduitArray.get(0).Nom_Entreprise);


                for(int i= 0;i<ProduitArray.size();i++){
                    if(ProduitArray.get(i).Nom_Entreprise.toLowerCase().equals(value.toLowerCase())){
                        itemsValueproduit.add(ProduitArray.get(i).Nom_Produit);
                        itemsallValueproduit.add(ProduitArray.get(i));
                    }
                }


                ArrayAdapter<String> adapter2 = new ArrayAdapter(getContext(), R.layout.item_spinner_dropdown, itemsValueproduit);
                spinnertypepaye.setAdapter(adapter2);
                spinnertypepaye.callOnClick();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setvalueprix(int position) {
        edttotal.setText("");
        edttotal.setText(itemsallValueproduit.get(position).PrixU_Produit);
        total=itemsallValueproduit.get(position).PrixU_Produit;
        qterestante=itemsallValueproduit.get(position).qte_Produit;
    }


    @Override
    public boolean onBackPressed() {
        return true;
    }

    private void commandeproduct() {
        if(edtqte.getText().toString().trim().isEmpty() || edtqte.getText().toString().trim().equals("")){
            Toast.makeText(getContext(),"المرجو إدخال الكمية المطلوبة",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtnomclient.getText().toString().trim().isEmpty() || edtnomclient.getText().toString().trim().equals("")) {
            Toast.makeText(getContext(),"المرجو إدخال إسم الزبون",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtphoneclient.getText().toString().trim().isEmpty() || edtphoneclient.getText().toString().trim().equals("")){
            Toast.makeText(getContext(),"المرجو إدخال هاتف الزبون",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtphoneclient.getText().toString().trim().length()<10){
            Toast.makeText(getContext(),"المرجو إدخال رقم هاتف صحيح يتكون من 10 أرقام",Toast.LENGTH_LONG).show();
            return;
        }else
        if(edtadressclient.getText().toString().trim().isEmpty() || edtadressclient.getText().toString().trim().equals(""))
        {
            Toast.makeText(getContext(),"المرجو إدخال عنوان الزبون",Toast.LENGTH_LONG).show();
            return;
        }else{
            if(Integer.parseInt(edtqte.getText().toString())>Integer.parseInt(qterestante)){
                Toast.makeText(getContext(),"الكمية المطلوبة غير متوفرة في الوقت الحالي ، يرجى المحاولة مرة أخرى لاحقًا أو طلب كمية أقل",Toast.LENGTH_LONG).show();
                return;
            }else
            if(spinnerVille.getSelectedItem().toString().equals("إختر المدينة")){
                Toast.makeText(getContext(),"الرجاء اختيار مدينة التسليم الخاصة بك",Toast.LENGTH_LONG).show();
                return;
            } else if( valuchoseradio==null  || valuchoseradio.equals("")){
                Toast.makeText(getContext(),"شكرا لرغبتك في اختيار نوع الدفع الخاص بك",Toast.LENGTH_LONG).show();
                return;
            }else  {
                geturlphoto(spinnertypepaye.getSelectedItem().toString());
                CommandeClient commandeClient=new CommandeClient(spinnerproduit.getSelectedItem().toString(),spinnertypepaye.getSelectedItem().toString(),urlphoto,edttotal.getText().toString(),edtqte.getText().toString(),edtnomclient.getText().toString(),edtphoneclient.getText().toString(),edtadressclient.getText().toString(),edtmotif.getText().toString(),spinnerVille.getSelectedItem().toString(),"0",Emailuser,valuchoseradio);
                CommandeClientref.push().setValue(commandeClient);
                edtqte.setText("");
                edtnomclient.setText("");
                edtadressclient.setText("");
                edtmotif.setText("");
                edtphoneclient.setText("");
                if(valuchoseradio.equals("الدفع عند الاستلام")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder
                            .setMessage("تمت معالجة طلبك بواسطة نظامنا ، يرجى تذكر دفع ثمن طلبك عند التسليم")
                            .setPositiveButton(getContext().getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    onBackPressed();
                                }
                            })
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    dialog.dismiss();
                                    onBackPressed();
                                }
                            })
                            .show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder
                            .setMessage("تمت معالجة طلبك بواسطة نظامنا\n" +
                                    "لقبول طلبك ، يرجى إجراء التحويل في أسرع وقت ممكن إلى رقم الحساب التالي XXXXXXXXXXXX.\n" +
                                    "وأرسل لنا الإيصال على الرقم التالي: 06.XX.XX.XX.XX")
                            .setPositiveButton(getContext().getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    onBackPressed();
                                }
                            })
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    dialog.dismiss();
                                    onBackPressed();
                                }
                            })
                            .show();
                }

            }

        }
    }
}