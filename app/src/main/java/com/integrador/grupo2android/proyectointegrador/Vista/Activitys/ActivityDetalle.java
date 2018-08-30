package com.integrador.grupo2android.proyectointegrador.Vista.Activitys;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import com.google.firebase.auth.FirebaseUser;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorPeliculas;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorSeries;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;
import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;
import com.integrador.grupo2android.proyectointegrador.Util.ResultListener;
import com.integrador.grupo2android.proyectointegrador.Util.TMDBHelper;
import com.integrador.grupo2android.proyectointegrador.Vista.Adapters.ViewPagerDetalleAdapter;
import com.integrador.grupo2android.proyectointegrador.Vista.Fragments.FragmentDetalle;
import java.util.List;
import butterknife.BindView;

public class ActivityDetalle extends AppCompatActivity implements FragmentDetalle.EscuchadorFragmentDetalle {

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    private String tipoDeLista;
    private Integer posicionLista;
    private ViewPager viewPager;

    private FragmentManager fragmentManager;
    private ViewPagerDetalleAdapter viewPagerDetalleAdapter;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //VIEWPAGER
        viewPager = findViewById(R.id.viewPagerDetalle);

        //SI EL BUNDLE NO ES NULO, RECIBE UN TIPO DE LISTA Y UNA POSICIÓN EN EL VIEWPAGER
        if (bundle != null) {
            tipoDeLista = bundle.getString(Constantes.TIPO_DE_LISTA);
            posicionLista = bundle.getInt(Constantes.POSICION_LISTA);
        }

            viewPagerDetalleAdapter = new ViewPagerDetalleAdapter(fragmentManager);

        //CARGO PELÍCULAS O SERIES AL VIEWPAGER, SEGÚN EL ETIPO DE LISTA QUE RECIBA
        if (tipoDeLista.equals(Constantes.LISTA_EN_CARTELERA) |
                tipoDeLista.equals(Constantes.LISTA_PROXIMOS_ESTRENOS) |
                tipoDeLista.equals(Constantes.LISTA_RANKING_PELICULAS) |
                tipoDeLista.equals(Constantes.LISTA_FAVORITOS_PELICULAS) |
                tipoDeLista.equals(Constantes.LISTA_POR_VER_PELICULAS) |
                tipoDeLista.equals(Constantes.LISTA_FILTRADA_PELICULAS) |
                tipoDeLista.equals(Constantes.LISTA_PORGENERO_PELICULAS)) {
            cargoPeliculas(tipoDeLista, bundle);

        } else {
            cargoSeries(tipoDeLista, bundle);
        }
    }

    private void cargoPeliculas(String tipoDeLista, Bundle bundle) {
        ControladorPeliculas controladorPeliculas = new ControladorPeliculas(getApplicationContext());
        ResultListener<List<Movie>> escuchadorVista = new ResultListener<List<Movie>>() {
            @Override
            public void finish(List<Movie> resultado) {
                viewPagerDetalleAdapter.actualizoListaPeliculas(resultado);
                viewPager.setAdapter(viewPagerDetalleAdapter);
                viewPager.setCurrentItem(posicionLista);
            }
        };
        //CONFIGURO EL TIPO DE RESULTADO QUE OBTENEMOS, SEGÚN EL TIPO DE LISTA RECIBIDO
        switch (tipoDeLista) {
            case Constantes.LISTA_EN_CARTELERA:
                controladorPeliculas.obtenerPeliculasEnCartelera(escuchadorVista, TMDBHelper.language_SPANISH, 1);
                break;
            case Constantes.LISTA_PROXIMOS_ESTRENOS:
                controladorPeliculas.obtenerProximasPeliculas(escuchadorVista, TMDBHelper.language_SPANISH, 1);
                break;
            case Constantes.LISTA_RANKING_PELICULAS:
                controladorPeliculas.obtenerPeliculasRanking(escuchadorVista, TMDBHelper.language_SPANISH, 1);
                break;
            case Constantes.LISTA_FAVORITOS_PELICULAS:
                viewPagerDetalleAdapter
                        .actualizoListaPeliculas(controladorPeliculas
                                .consultoListaPorFavorito());
                viewPager.setAdapter(viewPagerDetalleAdapter);
                viewPager.setCurrentItem(posicionLista);
                break;
            case Constantes.LISTA_POR_VER_PELICULAS:
                viewPagerDetalleAdapter
                        .actualizoListaPeliculas(controladorPeliculas
                                .consultoListaPorVerDespues());
                viewPager.setAdapter(viewPagerDetalleAdapter);
                viewPager.setCurrentItem(posicionLista);
                break;
            case Constantes.LISTA_FILTRADA_PELICULAS:
                String busqueda = bundle.getString(Constantes.TEXTO_BUSQUEDA);
                controladorPeliculas.searchPeliculasTexto(escuchadorVista, TMDBHelper.language_SPANISH, busqueda, 1);
                break;
            case Constantes.LISTA_PORGENERO_PELICULAS:
                Integer genero = bundle.getInt(Constantes.GENERO);
                controladorPeliculas.obtenerPeliculasFiltradas(escuchadorVista, genero, 8, null, TMDBHelper.language_SPANISH, 1);
                break;
        }
    }

    //IGUAL QUE CON LAS PELÍCULAS, CARGAMOS LAS SERIES SEGÚN EL TIPO DE LISTA QUE RECIBIMOS
    private void cargoSeries(String tipoDeLista, Bundle bundle) {
        ControladorSeries controladorSeries = new ControladorSeries(getApplicationContext());
        ResultListener<List<Tv>> escuchadorVista = new ResultListener<List<Tv>>() {
            @Override
            public void finish(List<Tv> resultado) {
                viewPagerDetalleAdapter.actualizoListaSeries(resultado);
                viewPager.setAdapter(viewPagerDetalleAdapter);
                viewPager.setCurrentItem(posicionLista);
            }
        };

        switch (tipoDeLista) {
            case Constantes.LISTA_RANKING_SERIES:
                controladorSeries.obtenerSeriesRanking(escuchadorVista, TMDBHelper.language_SPANISH, 1);
                break;
            case Constantes.LISTA_FAVORITOS_SERIES:
                viewPagerDetalleAdapter
                        .actualizoListaSeries(controladorSeries
                                .consultoListaPorFavorito());
                viewPager.setAdapter(viewPagerDetalleAdapter);
                viewPager.setCurrentItem(posicionLista);
                break;
            case Constantes.LISTA_POR_VER_SERIES:
                viewPagerDetalleAdapter
                        .actualizoListaSeries(controladorSeries
                                .consultoListaPorVerDespues());
                viewPager.setAdapter(viewPagerDetalleAdapter);
                viewPager.setCurrentItem(posicionLista);
                break;
            case Constantes.LISTA_FILTRADA_SERIES:
                String busqueda = bundle.getString(Constantes.TEXTO_BUSQUEDA);
                controladorSeries.searchSeriesText(escuchadorVista, TMDBHelper.language_SPANISH, busqueda, 1);
                break;
            case Constantes.LISTA_PORGENERO_SERIES:
                Integer genero = bundle.getInt(Constantes.GENERO);
                controladorSeries.obtenerSeriesFiltradas(escuchadorVista, genero, null, TMDBHelper.language_SPANISH, 1);
                break;
        }
    }

    //COMPARTIMOS LA URL DE LA PELÍCULA MEDIANTE EL BOTÓN DE SHARE.
    //ADJUNTAMOS LA URL DEL TRAILER QUE SE VERÁ EN YOUTUBE
    @Override
    public void envioUrl(String keyYoutube) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(Constantes.URL_BASE_YOUTUBE + keyYoutube));
        startActivity(webIntent);
    }
}
