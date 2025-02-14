package com.edulab.e_commerceapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.edulab.e_commerceapp.Class.Entreprise;
import com.edulab.e_commerceapp.Class.Status;
import com.edulab.e_commerceapp.R;
import com.edulab.e_commerceapp.Tools.Dialogs;

import java.util.ArrayList;
import java.util.List;

public class CustomListStatusAdapter extends RecyclerView.Adapter<CustomListStatusAdapter.CustomViewHolder> {

    private Context context;
    private List<Status> items;
    private int selectedItem = 0;

    public CustomListStatusAdapter(Context context, List<Status> items) {
        this.context = context;
        this.items = items;

    }

    @NonNull
    @Override
    public CustomListStatusAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomListStatusAdapter.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_status,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomListStatusAdapter.CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
       /* if(SelectedItem == position)
        {
            Drawable buttonDrawable = holder.itemViewes.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            //the color is a direct color int and not a color resource
            DrawableCompat.setTint(buttonDrawable, Color.parseColor("#ffc258"));
        }*/
        holder.itemTitle.setText(items.get(position).description);
        holder.itemViewes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItem = position;
                onDataChangeListener.showDetail(items.get(position).idstatus);
                    notifyDataSetChanged();
            }
        });
            if(selectedItem == position){
                Drawable buttonDrawable = holder.itemViewes.getBackground();
                buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                //the color is a direct color int and not a color resource
                DrawableCompat.setTint(buttonDrawable, Color.parseColor("#ffc258"));

            } else {
                Drawable buttonDrawable = holder.itemViewes.getBackground();
                buttonDrawable = DrawableCompat.wrap(buttonDrawable);
                //the color is a direct color int and not a color resource
                DrawableCompat.setTint(buttonDrawable, Color.parseColor("#bdc1c4"));
            }
        //    if(SelectedItem  == position){
        //        Drawable buttonDrawable = holder.itemViewes.getBackground();
        //        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        //        //the color is a direct color int and not a color resource
        //        DrawableCompat.setTint(buttonDrawable, Color.parseColor("#ffc258"));
        //    }else{
        //        SelectedItem=position;
        //        Drawable buttonDrawable = holder.itemViewes.getBackground();
        //        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        //        //the color is a direct color int and not a color resource
        //        DrawableCompat.setTint(buttonDrawable, Color.parseColor("#bdc1c4"));
        //
        //    }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView itemTitle;
        private View itemViewes;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemViewes=itemView;
            itemTitle = itemView.findViewById(R.id.item_title);
            Drawable buttonDrawable = itemView.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            //the color is a direct color int and not a color resource
            DrawableCompat.setTint(buttonDrawable, Color.parseColor("#bdc1c4"));
        }
    }

    CustomListStatusAdapter.OnDataChangeListener onDataChangeListener;

    public void setOnDataChangeListener(CustomListStatusAdapter.OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }


    public interface OnDataChangeListener {

         void showDetail(Object object);
    }


}
