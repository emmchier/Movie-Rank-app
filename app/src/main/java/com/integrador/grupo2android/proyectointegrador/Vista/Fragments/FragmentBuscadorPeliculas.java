package com.integrador.grupo2android.proyectointegrador.Vista.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorPeliculas;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Util.ResultListener;
import com.integrador.grupo2android.proyectointegrador.Util.TMDBHelper;
import com.integrador.grupo2android.proyectointegrador.Vista.Adapters.AdapterRecyclerBuscadorPeliculas;
import java.util.List;

public class FragmentBuscadorPeliculas extends Fragment implements AdapterRecyclerBuscadorPeliculas.EscuchadorRecyclerPeliculas {
    private ImageButton imageButtonSearch;
    private EditText editTextSearch;
    private RecyclerView recyclerView;
    private AdapterRecyclerBuscadorPeliculas adapterMovieListado;
    private EscuchadorFragmentoBuscadorPelicula escuchadorFragmentoBuscadorPelicula;

    private String editTextBuscarTexto;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.escuchadorFragmentoBuscadorPelicula = (EscuchadorFragmentoBuscadorPelicula) context;
    }

    public FragmentBuscadorPeliculas() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscador_peliculas, container, false);
        imageButtonSearch = view.findViewById(R.id.imageButtonSearch);
        editTextSearch = view.findViewById(R.id.editTextSearch);
        recyclerView = view.findViewById(R.id.recyclerViewListadoBuscadorPeliculas);

        //SETEAMOS EL BOTÓN DE BÚSQUEDA
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextBuscarTexto = editTextSearch.getText().toString();

                //PARA UNA BÚSQUEDA VÁLIDA, DEBE CONTENER 3 CARACTÉRES MÍNIMO
                if (editTextBuscarTexto.length() < 3) {
                    Toast.makeText(getActivity(), "Tu busqueda debe contener al menos 3 caracteres", Toast.LENGTH_SHORT).show();
                } else {
                    adapterMovieListado.notifyDataSetChanged();
                    ControladorPeliculas controladorPeliculas = new ControladorPeliculas(getContext());
                    ResultListener<List<Movie>> escuchadorVista = new ResultListener<List<Movie>>() {
                        @Override
                        public void finish(List<Movie> resultado) {
                            adapterMovieListado.setMovies(resultado);
                        }
                    };
                    //SI LA BÚSQUEDA COINCIDE CON LA INFORMACIÓN DE LA API, DEVUELVE RESULTADOS
                    controladorPeliculas.searchPeliculasTexto(escuchadorVista, TMDBHelper.language_SPANISH, editTextBuscarTexto, 1);
                }
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
            }
        });

        //RECYCLERVIEW DE RESULTADOS
        adapterMovieListado = new AdapterRecyclerBuscadorPeliculas(this);
        recyclerView.setAdapter(adapterMovieListado);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void seleccionaronPelicula(Integer posicion) {
        escuchadorFragmentoBuscadorPelicula.seleccionaronPelicula(posicion, editTextBuscarTexto);
    }

    public interface EscuchadorFragmentoBuscadorPelicula {
        void seleccionaronPelicula(Integer posicion, String texto);
    }
}
