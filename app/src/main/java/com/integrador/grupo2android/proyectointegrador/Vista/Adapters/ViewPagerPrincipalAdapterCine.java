package com.integrador.grupo2android.proyectointegrador.Vista.Adapters;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;
import com.integrador.grupo2android.proyectointegrador.Vista.Activitys.ActivityPrincipal;
import com.integrador.grupo2android.proyectointegrador.Vista.Fragments.FragmentCine;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerPrincipalAdapterCine extends FragmentStatePagerAdapter {
    private List<Fragment> listaFragmentos = new ArrayList<>();

    public ViewPagerPrincipalAdapterCine(FragmentManager fm) {
        super(fm);
        //CONFIGURAMOS LOS FRAGMENTOS DEL VIEWPAGER PRINCIPAL SEGÚN CARTELERA O PRÓXIMOS ESTRENOS
        if (ActivityPrincipal.modoSeleccionado.equals(Constantes.MODO_CINE)) {
            listaFragmentos.add(FragmentCine.crearFragmentoCine(Constantes.LISTA_EN_CARTELERA));
            listaFragmentos.add(FragmentCine.crearFragmentoCine(Constantes.LISTA_PROXIMOS_ESTRENOS));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return listaFragmentos.get(position);
    }

    @Override
    public int getCount() {
        return this.listaFragmentos.size();
    }
}
