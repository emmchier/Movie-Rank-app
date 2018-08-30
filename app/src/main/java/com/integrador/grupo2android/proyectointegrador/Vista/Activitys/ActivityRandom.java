package com.integrador.grupo2android.proyectointegrador.Vista.Activitys;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorPeliculas;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorSeries;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Genero;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;
import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;
import com.integrador.grupo2android.proyectointegrador.Util.ResultListener;
import com.integrador.grupo2android.proyectointegrador.Util.TMDBHelper;
import com.integrador.grupo2android.proyectointegrador.Vista.Fragments.FragmentDetalle;
import java.util.List;
import java.util.Random;
import static com.integrador.grupo2android.proyectointegrador.Util.Constantes.PELICULAS;
import static com.integrador.grupo2android.proyectointegrador.Util.Constantes.SERIES;

public class ActivityRandom extends AppCompatActivity implements FragmentDetalle.EscuchadorFragmentDetalle {

    private ImageView backButtonRandom;
    private FloatingActionButton botonRandom;
    private CardView contenedorDelRandom;
    private ImageView dado;
    long animationDuration = 1000;

    private static final String TITULO = "titulo";
    private static final String FOTO = "foto";
    private static final String RESENA = "RESENA";
    private static final String VIDEO = "VIDEO";
    private static final String ID_LLAVE = "ID";
    private static final String RANKING = "RANKING";

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        backButtonRandom = findViewById(R.id.backButtonRandom);
        botonRandom = findViewById(R.id.botonRandom);
        contenedorDelRandom = findViewById(R.id.contenedorDelRandom);
        dado = findViewById(R.id.dado);

        fragmentManager = getSupportFragmentManager();

        //TODA LA FUNCIONALIDAD PRINCIPAL DEL RANDOM
        botonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dado.setVisibility(View.VISIBLE);
                contenedorDelRandom.setVisibility(View.GONE);
                tirarElDado();
                obtenerGenerosRandom();
            }
        });

        backButtonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    //ANIMACIÓN DEL DADO
    private void tirarElDado() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(dado, "y",0f, 700f);
        animatorX.setDuration(animationDuration);
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(dado, "rotation", 10f, 10000f);
        rotateAnimator.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, rotateAnimator);
        animatorSet.start();
    }

    //EFECTUAR RANDOM
    private int generarNumeroAleatorio(Integer maximo) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        Integer numeroRandom = random.nextInt(maximo);
        return numeroRandom;
    }

    //OBTENEMOS LA LISTA DE GÉNEROS DE PELÍCULAS O SERIES AL HACER RANDOM
    public void obtenerGenerosRandom() {
        int peliSerie = generarNumeroAleatorio(2);
        if (peliSerie == 0) {
            ControladorPeliculas controladorPeliculas = new ControladorPeliculas(getApplicationContext());
            ResultListener<List<Genero>> escuchadorVista = new ResultListener<List<Genero>>() {
                @Override
                public void finish(List<Genero> resultado) {
                    obtenerDatosRandom(resultado, PELICULAS);
                }
            };
            controladorPeliculas.obtenerListadoDeGenerosPelicula(escuchadorVista, TMDBHelper.language_SPANISH);
        } else {
            ControladorSeries controladorSeries = new ControladorSeries(getApplicationContext());
            ResultListener<List<Genero>> escuchadorVista = new ResultListener<List<Genero>>() {
                @Override
                public void finish(List<Genero> resultado) {
                    obtenerDatosRandom(resultado, SERIES);
                }
            };
            controladorSeries.obtenerListadoDeGenerosSeries(escuchadorVista, TMDBHelper.language_SPANISH);
        }

    }

    private void obtenerDatosRandom(List<Genero> resultado, String modo) {
        Integer posicionGenero = generarNumeroAleatorio(resultado.size());
        final Integer posicionLista = generarNumeroAleatorio(20);
        Integer pagina = generarNumeroAleatorio(2) + 1;
        Integer idGenero = resultado.get(posicionGenero).getId();

        if (modo.equals(PELICULAS)) {
            ControladorPeliculas controladorPeliculas = new ControladorPeliculas(getApplicationContext());
            ResultListener<List<Movie>> escuchadorVista = new ResultListener<List<Movie>>() {
                @Override
                public void finish(List<Movie> resultado) {
                    cargarFragmentDetalle(resultado.get(posicionLista));
                }
            };
            controladorPeliculas.obtenerPeliculasFiltradas(escuchadorVista, idGenero, 8, null, TMDBHelper.language_SPANISH, pagina);
        } else {
            ControladorSeries controladorSeries = new ControladorSeries(getApplicationContext());
            ResultListener<List<Tv>> escuchadorVista = new ResultListener<List<Tv>>() {
                @Override
                public void finish(List<Tv> resultado) {
                    cargarFragmentDetalle(resultado.get(posicionLista));
                }
            };
            controladorSeries.obtenerSeriesFiltradas(escuchadorVista, idGenero, 8, TMDBHelper.language_SPANISH, pagina);
        }

    }

    //CARGAMOS UNA PELÍCULA EN EL FRAGMENTO DETALLE
    public void cargarFragmentDetalle(Movie movie) {
        dado.setVisibility(View.INVISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorDelRandom, FragmentDetalle.dameUnFragment(movie)).commit();
        contenedorDelRandom.setVisibility(View.VISIBLE);
    }

    //CARGAMOS UNA SERIE EN EL FRAGMENTO DETALLE
    public void cargarFragmentDetalle(Tv tv) {
        dado.setVisibility(View.INVISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorDelRandom, FragmentDetalle.dameUnFragment(tv)).commit();
        contenedorDelRandom.setVisibility(View.VISIBLE);
    }

    @Override
    public void envioUrl(String keyYoutube) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
        Uri.parse(Constantes.URL_BASE_YOUTUBE + keyYoutube));
        startActivity(webIntent);
    }
}
