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

import com.edulab.e_commerceapp.Class.Entreprise;
import com.edulab.e_commerceapp.Class.Produit;
import com.edulab.e_commerceapp.R;


import java.util.List;

public class CustomAdapterProduit extends RecyclerView.Adapter<CustomAdapterProduit.CustomViewHolder> {
    private Context context;
    private List<Produit> items;
    private String codedata;


    public CustomAdapterProduit(Context context, List<Produit> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomAdapterProduit.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomAdapterProduit.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_produit,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterProduit.CustomViewHolder holder, int position) {

        holder.itemTitle.setText(items.get(position).Nom_Produit);
        holder.itemPrix.setText(items.get(position).PrixU_Produit+" "+"درهم");
        codedata=items.get(position).Photo_Produit.replace("data:image/jpeg;base64,","");
        byte[] code = Base64.decode(codedata, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
        holder.imageViewPr.setImageBitmap(bitmap);
        holder.imageViewPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDataChangeListener.showDetail(items.get(position));
            }
        });

        holder.itemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDataChangeListener.showDetail(items.get(position));
            }
        });

        holder.itemPrix.setOnClickListener(new View.OnClickListener() {
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
        private TextView itemTitle, itemPrix;
        private ImageView imageViewPr;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle=itemView.findViewById(R.id.name);
            itemPrix=itemView.findViewById(R.id.prix);
            imageViewPr=itemView.findViewById(R.id.image);
        }
    }

    OnDataChangeListener onDataChangeListener;

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }


    public interface OnDataChangeListener {

        public void showDetail(Object object);
    }
}
