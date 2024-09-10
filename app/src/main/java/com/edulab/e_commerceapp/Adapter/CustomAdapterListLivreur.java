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

import com.edulab.e_commerceapp.Class.Livreur;
import com.edulab.e_commerceapp.Class.hmiza;
import com.edulab.e_commerceapp.R;

import java.util.List;

public class CustomAdapterListLivreur extends RecyclerView.Adapter<CustomAdapterListLivreur.CustomViewHolder>{
    private Context context;
    private List<Livreur> items;
    private String codedata;

    public CustomAdapterListLivreur(Context context, List<Livreur> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomAdapterListLivreur.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomAdapterListLivreur.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_livreur,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.nomcomlet.setText(items.get(position).prenom+" "+items.get(position).nom);
        if(items.get(position).photopersonnel == null || items.get(position).photopersonnel == ""){
            return;
        }else {
            codedata=items.get(position).photopersonnel.replace("data:image/jpeg;base64,","");
            byte[] code = Base64.decode(codedata, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
            holder.img.setImageBitmap(bitmap);
        }

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
        private View viewhmiza;
        private ImageView img;
        private TextView nomcomlet;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.viewhmiza=itemView;
            img=itemView.findViewById(R.id.item_image);
            nomcomlet=itemView.findViewById(R.id.item_title);

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
