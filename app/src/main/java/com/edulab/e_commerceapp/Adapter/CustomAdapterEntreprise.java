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
import com.edulab.e_commerceapp.R;

import java.util.List;

public class CustomAdapterEntreprise extends RecyclerView.Adapter<CustomAdapterEntreprise.CustomViewHolder> {


    private Context context;
    private List<Entreprise> items;
    private String codedata;

    public CustomAdapterEntreprise(Context context, List<Entreprise> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomAdapterEntreprise.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.itemTitle.setText(items.get(position).Nom_Entreprise);
        codedata=items.get(position).Logo.replace("data:image/jpeg;base64,","");
        byte[] code = Base64.decode(codedata, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
        holder.imageentr.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView itemTitle;
        private View itemViewes;
        private ImageView imageentr;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemViewes=itemView;
            itemTitle = itemView.findViewById(R.id.item_title);
            imageentr=itemView.findViewById(R.id.item_image);
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
