package com.edulab.e_commerceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edulab.e_commerceapp.Class.Entreprise;
import com.edulab.e_commerceapp.Class.Notif;
import com.edulab.e_commerceapp.NotificationActivity;
import com.edulab.e_commerceapp.R;

import java.util.List;

public class CustomNotificationAdapter extends RecyclerView.Adapter<CustomNotificationAdapter.CustomViewHolder>{

    private Context context;
    private List<Notif> items;

    public CustomNotificationAdapter(Context context, List<Notif> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomNotificationAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new CustomNotificationAdapter.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.items_notif,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomNotificationAdapter.CustomViewHolder holder, int position) {
        holder.textdate.setText(items.get(position).dateNotif);
        holder.textTitle.setText(items.get(position).TitleNotif);
        holder.textDescription.setText(items.get(position).DescriptionNotif);
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

    public void setOnDataChangeListener(CustomNotificationAdapter.OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }


    public interface OnDataChangeListener {

        public void showDetail(Object object);
    }
}
