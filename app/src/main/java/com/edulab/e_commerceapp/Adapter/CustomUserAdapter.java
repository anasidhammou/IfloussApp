package com.edulab.e_commerceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edulab.e_commerceapp.Class.Livreur;
import com.edulab.e_commerceapp.Class.User;
import com.edulab.e_commerceapp.R;

import java.util.List;

public class CustomUserAdapter extends RecyclerView.Adapter<CustomUserAdapter.CustomViewHolder> {
    private Context context;
    private List<User> items;
    private String codedata;

    public CustomUserAdapter(Context context, List<User> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public CustomUserAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomUserAdapter.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomUserAdapter.CustomViewHolder holder, int position) {

        holder.txtabr.setText(items.get(position).prenom.charAt(0)+" "+items.get(position).nom.charAt(0));
        holder.txtname.setText(items.get(position).prenom+" "+items.get(position).nom);
        holder.itemsview.setOnClickListener(new View.OnClickListener() {
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
        private TextView txtabr, txtname;
        private View itemsview;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemsview=itemView;
            txtabr=itemView.findViewById(R.id.txt_name);
            txtname=itemView.findViewById(R.id.item_name);
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
