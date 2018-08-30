package com.integrador.grupo2android.proyectointegrador.Vista.Adapters;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;
import com.integrador.grupo2android.proyectointegrador.Vista.Fragments.FragmentDetalle;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerDetalleAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> listaFragmentos = new ArrayList<>();

    public ViewPagerDetalleAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return this.listaFragmentos.get(position);
    }

    @Override
    public int getCount() {
        return this.listaFragmentos.size();
    }

    public void actualizoListaPeliculas(List<Movie> listaDePeliculas) {
        listaFragmentos.clear();
        for (Movie movie : listaDePeliculas) {
            listaFragmentos.add(FragmentDetalle.dameUnFragment(movie));
        }
        notifyDataSetChanged();
    }

    public void actualizoListaSeries(List<Tv> listaDeTv) {
        listaFragmentos.clear();
        for (Tv tv : listaDeTv) {
            listaFragmentos.add(FragmentDetalle.dameUnFragment(tv));
        }
        notifyDataSetChanged();
    }
}
