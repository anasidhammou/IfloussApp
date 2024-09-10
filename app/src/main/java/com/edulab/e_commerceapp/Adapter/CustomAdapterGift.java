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

import com.edulab.e_commerceapp.Class.GiftUser;
import com.edulab.e_commerceapp.Class.hmiza;
import com.edulab.e_commerceapp.R;

import java.util.List;

public class CustomAdapterGift extends RecyclerView.Adapter<CustomAdapterGift.CustomViewHolder> {

    private Context context;
    private List<GiftUser> items;
    private String codedata;

    public CustomAdapterGift(Context context, List<GiftUser> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomAdapterGift.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomAdapterGift.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hmiza,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterGift.CustomViewHolder holder, int position) {
        holder.txttitle.setText(items.get(position).namegigt);
        holder.txtprice.setText(items.get(position).pointgift);
        codedata=items.get(position).photogift.replace("data:image/jpeg;base64,","");
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

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }


    public interface OnDataChangeListener {

        void showDetail(Object object);
    }
}
