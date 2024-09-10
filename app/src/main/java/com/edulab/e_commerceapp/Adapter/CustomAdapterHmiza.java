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
import com.edulab.e_commerceapp.Class.hmiza;
import com.edulab.e_commerceapp.R;

import java.util.List;

public class CustomAdapterHmiza extends RecyclerView.Adapter<CustomAdapterHmiza.CustomViewHolder>{
    private Context context;
    private List<hmiza> items;
    private String codedata;

    public CustomAdapterHmiza(Context context, List<hmiza> items) {
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomAdapterHmiza.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hmiza,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.txttitle.setText(items.get(position).Nom);
        holder.txtprice.setText(items.get(position).Prix+" "+"درهم");
        codedata=items.get(position).Photo.replace("data:image/jpeg;base64,","");
        byte[] code = Base64.decode(codedata, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
        holder.imag.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

    public class CustomViewHolder extends RecyclerView.ViewHolder  {
        private ImageView imag;
        private TextView txttitle, txtprice;
        private View viewhmiza;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.viewhmiza=itemView;
            imag=itemView.findViewById(R.id.item_image);
            txttitle=itemView.findViewById(R.id.item_title);
            txtprice=itemView.findViewById(R.id.item_prix);
        }
    }

    OnDataChangeListener onDataChangeListener;

    public void setOnDataChangeListener(CustomAdapterHmiza.OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }


    public interface OnDataChangeListener {

         void showDetail(Object object);
    }
}
