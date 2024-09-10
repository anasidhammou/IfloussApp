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

import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.R;

import java.util.List;

public class CustomListProduitAdapterEntreprise extends RecyclerView.Adapter<CustomListProduitAdapterEntreprise.CustomViewHolder> {
    private Context context;
    private List<Produit> items;
    private String codedata;


    public CustomListProduitAdapterEntreprise(Context context, List<Produit> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomListProduitAdapterEntreprise.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomListProduitAdapterEntreprise.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_produit,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomListProduitAdapterEntreprise.CustomViewHolder holder, int position) {
        holder.txtname.setText(items.get(position).Nom_Produit);
        holder.txtprix.setText(items.get(position).PrixU_Produit+" "+"درهم");
        codedata=items.get(position).Photo_Produit.replace("data:image/jpeg;base64,","");
        byte[] code = Base64.decode(codedata, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
        holder.image.setImageBitmap(bitmap);
        holder.itemsV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDataChangeListener.showDetail(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView txtname, txtprix;
        private ImageView image;
        private View itemsV;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemsV=itemView;
            image=itemView.findViewById(R.id.item_image);
            txtname=itemView.findViewById(R.id.item_nompr);
            txtprix=itemView.findViewById(R.id.item_prixpr);
        }
    }

    OnDataChangeListener onDataChangeListener;

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }


    public interface OnDataChangeListener {

        void showDetail(Object object);
    }


}
