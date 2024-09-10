package com.edulab.e_commerceapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edulab.e_commerceapp.Class.CommandeClient;
import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.Entreprise.GestionEntrepriseActivity;
import com.edulab.e_commerceapp.R;

import org.w3c.dom.Text;

import java.security.interfaces.RSAKey;
import java.util.List;

public class CustomListProduitAdapter extends RecyclerView.Adapter<CustomListProduitAdapter.CustomViewHolder> {

    private Context context;
    private List<Produit> items;
    private String codedata;

    public CustomListProduitAdapter(Context context, List<Produit> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomListProduitAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomListProduitAdapter.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_produit,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomListProduitAdapter.CustomViewHolder holder, int position) {
       holder.txtname.setText(items.get(position).Nom_Produit);
       holder.txtprix.setText(items.get(position).PrixU_Produit+" "+"درهم");
        codedata=items.get(position).Photo_Produit.replace("data:image/jpeg;base64,","");
        byte[] code = Base64.decode(codedata, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
        holder.image.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView txtname, txtprix;
        private ImageView image;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.item_image);
            txtname=itemView.findViewById(R.id.item_nompr);
            txtprix=itemView.findViewById(R.id.item_prixpr);
        }
    }

}
