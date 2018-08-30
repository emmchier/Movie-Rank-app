package com.integrador.grupo2android.proyectointegrador.Vista.Adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AdapterViewPagerOnBoarding extends PagerAdapter {
    private LayoutInflater inflater;
    private int[] layout;
    private Context context;

    public AdapterViewPagerOnBoarding(int[] layout, Context context) {
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return layout.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    //INFLAMOS LAS VISTAS DE LOS LAYOUTS Y LE PASAMOS UNA POSICIÓN EN EL CONTAINER
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout[position], container, false);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
