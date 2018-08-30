package com.integrador.grupo2android.proyectointegrador.Vista.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorPeliculas;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;
import com.integrador.grupo2android.proyectointegrador.Util.ResultListener;
import com.integrador.grupo2android.proyectointegrador.Util.TMDBHelper;
import com.integrador.grupo2android.proyectointegrador.Vista.Adapters.AdapterRecyclerViewPagerCine;
import java.util.List;

public class FragmentCine extends Fragment implements AdapterRecyclerViewPagerCine.EscuchadorAdapterCine {
    private AdapterRecyclerViewPagerCine adapterRecyclerViewCine;
    private RecyclerView recyclerView;
    private String modoInterno;
    private EscuchadorFragmentCine escuchadorFragmentCine;

    public FragmentCine() {
    }
    //CONSTRUCTOR DEL FRAGMENTO CINE PARA UTILIZARLO EN VIEWPAGER
    public static FragmentCine crearFragmentoCine(String modo) {
        FragmentCine fragmentCine = new FragmentCine();
        Bundle bundle = new Bundle();
        bundle.putString("modo", modo);
        fragmentCine.setArguments(bundle);
        return fragmentCine;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.escuchadorFragmentCine = (EscuchadorFragmentCine) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cine, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewContenedorFragmentCine);
        ControladorPeliculas controladorPeliculas = new ControladorPeliculas(getActivity().getApplicationContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            modoInterno = bundle.getString("modo");
        }

        //RECYCLERVIEW
        adapterRecyclerViewCine = new AdapterRecyclerViewPagerCine(getContext(), modoInterno, this);
        recyclerView.setAdapter(adapterRecyclerViewCine);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        pidoListas(modoInterno, controladorPeliculas);
        return view;
    }

    //PEDIMOS LISTAS AL CARGAR LA APP, DE CARTELERA COMO DE PRÓXIMOS ESTRENOS
    private void pidoListas(String modoInterno, ControladorPeliculas controladorPeliculas) {
        ResultListener<List<Movie>> escuchadorDeLaVista = new ResultListener<List<Movie>>() {
            @Override
            public void finish(List<Movie> resultado) {
                adapterRecyclerViewCine.agregarPeliculas(resultado);
            }
        };
        switch (modoInterno) {
            case Constantes.LISTA_EN_CARTELERA:
                controladorPeliculas.obtenerPeliculasEnCartelera(escuchadorDeLaVista, TMDBHelper.language_SPANISH, 1);
                break;
            case Constantes.LISTA_PROXIMOS_ESTRENOS:
                controladorPeliculas.obtenerProximasPeliculas(escuchadorDeLaVista, TMDBHelper.language_SPANISH, 1);
                break;
            default:
        }
    }

    @Override
    public void peliculaSeleccionada(Integer posicion, String modoInterno) {
        escuchadorFragmentCine.peliculaSeleccionadaFragment(posicion, modoInterno);
    }

    public interface EscuchadorFragmentCine {
        void peliculaSeleccionadaFragment(Integer posicion, String modoInterno);
    }
}
