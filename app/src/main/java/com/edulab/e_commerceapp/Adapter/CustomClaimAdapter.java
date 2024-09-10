package com.edulab.e_commerceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edulab.e_commerceapp.Class.Claim;
import com.edulab.e_commerceapp.Class.Notif;
import com.edulab.e_commerceapp.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class CustomClaimAdapter extends RecyclerView.Adapter<CustomClaimAdapter.CustomViewHolder> {
    private Context context;
    private List<Claim> items;
    private SimpleDateFormat simpleDateFormat;

    public CustomClaimAdapter(Context context, List<Claim> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomClaimAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomClaimAdapter.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.items_claim,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomClaimAdapter.CustomViewHolder holder, int position) {
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateTime = simpleDateFormat.format(items.get(position).dateclaim).toString();
        holder.textdate.setText(dateTime);
        holder.textTitle.setText(items.get(position).Subject);
        holder.textDescription.setText(items.get(position).Message);
        holder.itemViewes.setOnClickListener(new View.OnClickListener() {
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
        private TextView textdate, textTitle, textDescription;
        private View itemViewes;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemViewes=itemView;
            textdate=itemView.findViewById(R.id.item_Date);
            textTitle=itemView.findViewById(R.id.title);
            textDescription=itemView.findViewById(R.id.description);
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
