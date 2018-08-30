package com.integrador.grupo2android.proyectointegrador.Vista.Adapters;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;
import com.integrador.grupo2android.proyectointegrador.Vista.Activitys.ActivityPrincipal;
import com.integrador.grupo2android.proyectointegrador.Vista.Fragments.FragmentCasa;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerPrincipalAdapterCasa extends FragmentStatePagerAdapter {
    private List<Fragment> listaFragmentos = new ArrayList<>();

    public ViewPagerPrincipalAdapterCasa(FragmentManager fm) {
        super(fm);
        //CONFIGURAMOS LOS FRAGMENTOS DEL VIEWPAGER PRINCIPAL SEGÚN PELÍCULAS O SERIES
        if (ActivityPrincipal.modoSeleccionado.equals(Constantes.MODO_CASA)) {
            listaFragmentos.add(FragmentCasa.crearFragmentoCasa(Constantes.PELICULAS));
            listaFragmentos.add(FragmentCasa.crearFragmentoCasa(Constantes.SERIES));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return this.listaFragmentos.get(position);
    }

    @Override
    public int getCount() {
        return this.listaFragmentos.size();
    }
}