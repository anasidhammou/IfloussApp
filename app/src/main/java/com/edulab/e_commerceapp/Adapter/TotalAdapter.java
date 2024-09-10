package com.edulab.e_commerceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.edulab.e_commerceapp.Class.totaluser;
import com.edulab.e_commerceapp.R;

import java.util.ArrayList;

public class TotalAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<totaluser> mArrayList;
    private LayoutInflater layoutInflater;
    private int type = 1;

    public TotalAdapter(Context context, ArrayList<totaluser> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;

    }


    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_compte, container, false);

        TextView txttotal, txttitle;
        totaluser totalarray = mArrayList.get(position);
        txttotal = view.findViewById(R.id.totalnum);
        txttitle=view.findViewById(R.id.titlenum);

        txttitle.setText(totalarray.name());
        txttotal.setText(totalarray.total());


        return view;
    }
}
