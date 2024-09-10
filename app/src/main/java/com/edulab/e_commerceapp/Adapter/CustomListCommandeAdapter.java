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

import com.edulab.e_commerceapp.Class.CommandeClient;
import com.edulab.e_commerceapp.Class.Status;
import com.edulab.e_commerceapp.R;

import java.util.List;

public class CustomListCommandeAdapter extends RecyclerView.Adapter<CustomListCommandeAdapter.CustomViewHolder>{

    private Context context;
    private List<CommandeClient> items;
    private String codedata;

    public CustomListCommandeAdapter(Context context, List<CommandeClient> allcommandearrayfiltred) {
        this.context = context;
        this.items = allcommandearrayfiltred;
    }

    @NonNull
    @Override
    public CustomListCommandeAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomListCommandeAdapter.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_commande,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomListCommandeAdapter.CustomViewHolder holder, int position) {
        holder.itemTitle.setText(items.get(position).Nom_Produitn);
        if(items.get(position).etat.equals("0")){
            holder.itemstate.setText("في الانتظار");
        }else
            if (items.get(position).etat.equals("1")){
                holder.itemstate.setText("تم تأكيد الطلبية");
            }else
                if (items.get(position).etat.equals("2")){
                    holder.itemstate.setText("تم الارسال");
                }else
                if (items.get(position).etat.equals("3")){
                    holder.itemstate.setText("تم الشحن بنجاح");
                }else
                if (items.get(position).etat.equals("4")){
                    holder.itemstate.setText("الغيت");
                }else
                if (items.get(position).etat.equals("5")){
                    holder.itemstate.setText("لايجيب");
                }
        codedata=items.get(position).Photo_Produit.replace("data:image/jpeg;base64,","");
        byte[] code = Base64.decode(codedata, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(code,0,code.length);
        holder.Imageprodu.setImageBitmap(bitmap);
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

        private TextView itemTitle , itemstate;
        private ImageView Imageprodu;
        private View itemViewes;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemViewes=itemView;
            Imageprodu=itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemstate = itemView.findViewById(R.id.item_status);

        }
    }

    OnDataChangeListener onDataChangeListener;

    public void setOnDataChangeListener(CustomListCommandeAdapter.OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }


    public interface OnDataChangeListener {

        public void showDetail(Object object);
    }
}
