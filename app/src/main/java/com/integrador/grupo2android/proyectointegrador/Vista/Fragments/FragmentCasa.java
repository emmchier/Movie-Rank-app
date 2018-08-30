package com.integrador.grupo2android.proyectointegrador.Vista.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorPeliculas;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorSeries;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;
import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;
import com.integrador.grupo2android.proyectointegrador.Util.ResultListener;
import com.integrador.grupo2android.proyectointegrador.Util.TMDBHelper;
import com.integrador.grupo2android.proyectointegrador.Vista.Adapters.AdapterMovie;
import com.integrador.grupo2android.proyectointegrador.Vista.Adapters.AdapterSerie;
import java.util.List;
import static com.integrador.grupo2android.proyectointegrador.Util.Constantes.LISTA_FAVORITOS_PELICULAS;
import static com.integrador.grupo2android.proyectointegrador.Util.Constantes.LISTA_FAVORITOS_SERIES;
import static com.integrador.grupo2android.proyectointegrador.Util.Constantes.LISTA_POR_VER_PELICULAS;
import static com.integrador.grupo2android.proyectointegrador.Util.Constantes.LISTA_POR_VER_SERIES;
import static com.integrador.grupo2android.proyectointegrador.Util.Constantes.LISTA_RANKING_PELICULAS;
import static com.integrador.grupo2android.proyectointegrador.Util.Constantes.LISTA_RANKING_SERIES;

public class FragmentCasa extends Fragment implements AdapterMovie.EscuchadorDelAdapterMovie, AdapterSerie.EscuchadorDelAdapterSerie {
    private Button buttonVerMasTusSeleccion;

    private ImageView imageViewMasVotada1;
    private ImageView imageViewMasVotada2;
    private ImageView imageViewMasVotada3;

    private TextView tituloPeliculaMasVotada1;
    private TextView tituloPeliculaMasVotada2;
    private TextView tituloPeliculaMasVotada3;

    private TextView calificacionMasVotada1;
    private TextView calificacionMasVotada2;
    private TextView calificacionMasVotada3;

    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;

    private RecyclerView recyclerViewFavoritos;
    private RecyclerView recyclerViewVerDespues;

    private AdapterMovie adapterPeliculasFavoritos;
    private AdapterMovie adapterPeliculasVerDespues;

    private AdapterSerie adapterSeriesFavoritos;
    private AdapterSerie adapterSeriesVerDespues;

    private String modoInterno;
    private EscuchadorDelFragmentCasa escuchadorDelFragmentCasa;

    private List<Movie> listaDeMovieFavoritos;
    private List<Movie> listaDeMovieVerDespues;

    private List<Tv> listaDeSerieFavoritos;
    private List<Tv> listaDeSerieVerDespues;

    public FragmentCasa() {
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.escuchadorDelFragmentCasa = (EscuchadorDelFragmentCasa) context;
    }

    //CONSTRUCTOR DEL FRAGMENTO CASA PARA UTILIZARLO EN VIEWPAGER
    public static FragmentCasa crearFragmentoCasa(String modo) {
        FragmentCasa fragmentCasa = new FragmentCasa();
        Bundle bundle = new Bundle();
        bundle.putString("modo", modo);
        fragmentCasa.setArguments(bundle);
        return fragmentCasa;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_casa, container, false);
        initViews(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            modoInterno = bundle.getString("modo");
        }

        //RECURRIMOS A MÉTODOS EN LA PARTE SUPERIOR PARA NO SOBRECARGAR DE LÍNEAS AL CÓDIGO.
        //ES MEJOR MODULARIZAR
        setearListasDeLosAdapters();
        setearLayoutManagerRecyclers();

        switch (modoInterno) {
            case Constantes.PELICULAS:
                setearAdaptersPeliculas();
                cargoRankingPeliculas();
                setearEscuchadoresRankingPeliculas();
                break;
            case Constantes.SERIES:
                setearAdaptersSeries();
                cargoRankingSeries();
                setearEscuchadoresRankingSeries();
                break;
            default:
                Toast.makeText(getContext(), "Fallo el bundle", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    //INICIAMOS TODAS LAS VISTAS TAMBIÉN EN UN MÉTODO
    private void initViews(View view) {
        imageViewMasVotada1 = view.findViewById(R.id.ImageViewMasVotada1);
        imageViewMasVotada2 = view.findViewById(R.id.ImageViewMasVotada2);
        imageViewMasVotada3 = view.findViewById(R.id.ImageViewMasVotada3);
        tituloPeliculaMasVotada1 = view.findViewById(R.id.tituloPeliculaMasVotada1);
        tituloPeliculaMasVotada2 = view.findViewById(R.id.tituloPeliculaMasVotada2);
        tituloPeliculaMasVotada3 = view.findViewById(R.id.tituloPeliculaMasVotada3);
        recyclerViewFavoritos = view.findViewById(R.id.recyclerTuSeleccion);
        recyclerViewVerDespues = view.findViewById(R.id.recyclerProximosEstrenos);
        calificacionMasVotada1 = view.findViewById(R.id.textViewPuntuacion1);
        calificacionMasVotada2 = view.findViewById(R.id.textViewPuntuacion2);
        calificacionMasVotada3 = view.findViewById(R.id.textViewPuntuacion3);
        cardView1 = view.findViewById(R.id.cardView1);
        cardView2 = view.findViewById(R.id.cardView2);
        cardView3 = view.findViewById(R.id.cardView3);
    }

    //EVENTOS AL CLICKEAR SERIES DEL RANKING
    private void setearEscuchadoresRankingSeries() {
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escuchadorDelFragmentCasa.seleccionaronRanking(0, LISTA_RANKING_SERIES);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escuchadorDelFragmentCasa.seleccionaronRanking(1, LISTA_RANKING_SERIES);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escuchadorDelFragmentCasa.seleccionaronRanking(2, LISTA_RANKING_SERIES);
            }
        });
    }

    private void setearAdaptersSeries() {
        adapterSeriesFavoritos = new AdapterSerie(this, listaDeSerieFavoritos, LISTA_FAVORITOS_SERIES);
        adapterSeriesVerDespues = new AdapterSerie(this, listaDeSerieVerDespues, LISTA_POR_VER_SERIES);
        recyclerViewFavoritos.setAdapter(adapterSeriesFavoritos);
        recyclerViewVerDespues.setAdapter(adapterSeriesVerDespues);
    }

    //EVENTOS AL CLICKEAR PELÍCULAS DEL RANKING
    private void setearEscuchadoresRankingPeliculas() {
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escuchadorDelFragmentCasa.seleccionaronRanking(0, LISTA_RANKING_PELICULAS);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escuchadorDelFragmentCasa.seleccionaronRanking(1, LISTA_RANKING_PELICULAS);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escuchadorDelFragmentCasa.seleccionaronRanking(2, LISTA_RANKING_PELICULAS);
            }
        });
    }

    //SETEAMOS LOS ADAPTERS PARA QUE CARGUEN A LOS RECYCLERS DE PELÍCULAS
    private void setearAdaptersPeliculas() {
        adapterPeliculasFavoritos = new AdapterMovie(this, listaDeMovieFavoritos, LISTA_FAVORITOS_PELICULAS);
        adapterPeliculasVerDespues = new AdapterMovie(this, listaDeMovieVerDespues, LISTA_POR_VER_PELICULAS);
        recyclerViewFavoritos.setAdapter(adapterPeliculasFavoritos);
        recyclerViewVerDespues.setAdapter(adapterPeliculasVerDespues);
    }

    //SETEAMOS LA FORMA EN QUE QUEREMOS SE VEAN LAS CELDAS DE LOS RECYCLERS
    private void setearLayoutManagerRecyclers() {
        RecyclerView.LayoutManager layoutManagerVerDespues = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerFavorito = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFavoritos.setLayoutManager(layoutManagerVerDespues);
        recyclerViewVerDespues.setLayoutManager(layoutManagerFavorito);
    }

    //SETEAMOS LAS LISTAS DE FAVORITOS Y VER DESPUÉS, DE SERIES COMO PELÍCULAS
    private void setearListasDeLosAdapters() {
        ControladorPeliculas controladorPeliculas = new ControladorPeliculas(getActivity().getApplicationContext());
        ControladorSeries controladorSeries = new ControladorSeries(getActivity().getApplicationContext());
        listaDeMovieFavoritos = controladorPeliculas.consultoListaPorFavorito();
        listaDeMovieVerDespues = controladorPeliculas.consultoListaPorVerDespues();
        listaDeSerieFavoritos = controladorSeries.consultoListaPorFavorito();
        listaDeSerieVerDespues = controladorSeries.consultoListaPorVerDespues();
    }

    //NI BIEN CARGA LA APP SE LLENA DE RANKING DE PELÍCULAS
    private void cargoRankingPeliculas() {
        ResultListener<List<Movie>> contenedorDePeliculasResultListener = new ResultListener<List<Movie>>() {
            @Override
            public void finish(List<Movie> resultado) {
                Movie masVotada1 = resultado.get(0);
                Movie masVotada2 = resultado.get(1);
                Movie masVotada3 = resultado.get(2);
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/w500/" + masVotada1.getPoster_path()).into(imageViewMasVotada1);
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/w500/" + masVotada2.getPoster_path()).into(imageViewMasVotada2);
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/w500/" + masVotada3.getPoster_path()).into(imageViewMasVotada3);
                tituloPeliculaMasVotada1.setText(masVotada1.getTitle());
                tituloPeliculaMasVotada2.setText(masVotada2.getTitle());
                tituloPeliculaMasVotada3.setText(masVotada3.getTitle());
                calificacionMasVotada1.setText(masVotada1.getVote_average());
                calificacionMasVotada2.setText(masVotada2.getVote_average());
                calificacionMasVotada3.setText(masVotada3.getVote_average());
            }
        };
        ControladorPeliculas controladorPeliculas = new ControladorPeliculas(getActivity().getApplicationContext());
        controladorPeliculas.obtenerPeliculasRanking(contenedorDePeliculasResultListener, TMDBHelper.language_SPANISH, 1);
    }

    //NI BIEN CARGA LA APP SE LLENA DE RANKING DE SERIES
    private void cargoRankingSeries() {
        ResultListener<List<Tv>> contenedorDeTvResultListener = new ResultListener<List<Tv>>() {
            @Override
            public void finish(List<Tv> resultado) {
                Tv masVotada1 = resultado.get(0);
                Tv masVotada2 = resultado.get(1);
                Tv masVotada3 = resultado.get(2);
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/w500/" + masVotada1.getPoster_path()).into(imageViewMasVotada1);
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/w500/" + masVotada2.getPoster_path()).into(imageViewMasVotada2);
                Glide.with(getActivity()).load("https://image.tmdb.org/t/p/w500/" + masVotada3.getPoster_path()).into(imageViewMasVotada3);
                tituloPeliculaMasVotada1.setText(masVotada1.getName());
                tituloPeliculaMasVotada2.setText(masVotada2.getName());
                tituloPeliculaMasVotada3.setText(masVotada3.getName());
                calificacionMasVotada1.setText(masVotada1.getVote_average());
                calificacionMasVotada2.setText(masVotada2.getVote_average());
                calificacionMasVotada3.setText(masVotada3.getVote_average());
            }

        };
        ControladorSeries controladorSeries = new ControladorSeries(getActivity().getApplicationContext());
        controladorSeries.obtenerSeriesRanking(contenedorDeTvResultListener, TMDBHelper.language_SPANISH, 1);
    }

    @Override
    public void seleccionaronSerie(Integer posicion, String tituloDeLaLista) {
        escuchadorDelFragmentCasa.seleccionaronAudiovisual(posicion, tituloDeLaLista);
    }

    @Override
    public void seleccionaronMovie(Integer posicion, String tituloDeLaLista) {
        escuchadorDelFragmentCasa.seleccionaronAudiovisual(posicion, tituloDeLaLista);
    }

    //CARGAMOS TODAS LAS FUNCIONALIDADES AL INICIAR LA APP
    @Override
    public void onStart() {
        super.onStart();
        if (adapterPeliculasVerDespues != null) {
            ControladorPeliculas controladorPeliculas = new ControladorPeliculas(getActivity().getApplicationContext());
            adapterPeliculasVerDespues.setMovies(controladorPeliculas.consultoListaPorVerDespues());
        }

        if (adapterSeriesVerDespues != null) {
            ControladorSeries controladorSeries = new ControladorSeries(getActivity().getApplicationContext());
            adapterSeriesVerDespues.setTvs(controladorSeries.consultoListaPorVerDespues());
        }

        if (adapterPeliculasFavoritos != null) {
            ControladorPeliculas controladorPeliculas = new ControladorPeliculas(getActivity().getApplicationContext());
            adapterPeliculasFavoritos.setMovies(controladorPeliculas.consultoListaPorFavorito());
        }

        if (adapterSeriesFavoritos != null) {
            ControladorSeries controladorSeries = new ControladorSeries(getActivity().getApplicationContext());
            adapterSeriesFavoritos.setTvs(controladorSeries.consultoListaPorFavorito());
        }
    }

    public interface EscuchadorDelFragmentCasa {
        void seleccionaronRanking(Integer posicion, String tipoLista);

        void seleccionaronAudiovisual(Integer posicion, String tituloDeLaLista);
    }

}
