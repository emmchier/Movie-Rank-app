package com.integrador.grupo2android.proyectointegrador.Vista.Fragments;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorFavoritos;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorUsuarios;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorVerDespuesPeliculas;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;
import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;

public class FragmentDetalle extends Fragment {
    private TextView textViewTitulo;
    private TextView textViewResena;

    private ImageButton buttonPlayTrailer;
    private ImageButton imageButtonFavorito;
    private ImageButton imageButtonAddToList;
    private ImageButton imageButtonShare;

    private RatingBar ratingBar;

    private EscuchadorFragmentDetalle escuchadorFragmentDetalle;

    private String url_foto;
    private String titulo;
    private String keyYoutube;
    private String id;
    private String resena;
    private String modo;
    private Integer ranking;
    private ImageView imageView;

    private static final String TITULO = "titulo";
    private static final String FOTO = "foto";
    private static final String RESENA = "RESENA";
    private static final String VIDEO = "VIDEO";
    private static final String ID_LLAVE = "ID";
    private static final String RANKING = "RANKING";

    private Boolean estadoFavorito;
    private Boolean estadoVerDespues;

    private FirebaseUser user;

    private String verDespuesActivo;
    private String favoritoActivo;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.escuchadorFragmentDetalle = (FragmentDetalle.EscuchadorFragmentDetalle) context;
    }

    public FragmentDetalle() {
    }

    //CONSTRUCTOR DEL FRAGMENTO PELÍCULA PARA REUTILIZAR
    public static Fragment dameUnFragment(Movie movie) {
        FragmentDetalle fragmentDetalle = new FragmentDetalle();
        Bundle args = new Bundle();
        args.putString(TITULO, movie.getTitle());
        args.putString(FOTO, movie.getBackdrop_path());
        args.putString(ID_LLAVE, movie.getId().toString());
        args.putString(RESENA, movie.getOverview());
        args.putString(Constantes.MODO_INTERNO, Constantes.PELICULAS);
        args.putString(RANKING, movie.getVote_average());
        fragmentDetalle.setArguments(args);
        return fragmentDetalle;
    }

    //CONSTRUCTOR DEL FRAGMENTO SERIE PARA REUTILIZAR
    public static Fragment dameUnFragment(Tv tv) {
        FragmentDetalle fragmentDetalle = new FragmentDetalle();
        Bundle args = new Bundle();
        args.putString(TITULO, tv.getName());
        args.putString(FOTO, tv.getPoster_path());
        args.putString(ID_LLAVE, tv.getId().toString());
        args.putString(RESENA, tv.getOverview());
        args.putString(RANKING, tv.getVote_average());
        args.putString(Constantes.MODO_INTERNO, Constantes.SERIES);
        fragmentDetalle.setArguments(args);
        return fragmentDetalle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        //AQUÍ TAMBIÉN MODULARIZAMOS CON MÉTODOS PARA ORGANIZAR MEJOR EL PROYECTO
        initViews(view);

        final Bundle bundle = getArguments();

        if (bundle != null) {
            cargosValores(bundle);
        }
        setearOnClickImageViewVideo();
        setearOnClickButtonShare();
        setearOnClickButtonListaPorVer();
        setearOnClickButtonFavoritos();
        return view;
    }

    private void initViews(View view) {
        imageButtonFavorito = view.findViewById(R.id.imageButtonFavorito);
        imageButtonAddToList = view.findViewById(R.id.buttonAddToList);
        imageButtonShare = view.findViewById(R.id.imageButtonShare);
        imageView = view.findViewById(R.id.imageViewAudiovisualCarteleraDetalle);
        textViewTitulo = view.findViewById(R.id.textViewTitulo);
        textViewResena = view.findViewById(R.id.textViewResena);
        ratingBar = view.findViewById(R.id.ratingBar);
        buttonPlayTrailer = view.findViewById(R.id.buttonPlayTrailer);
    }

    //SETEAMOS EL REPRODUCTOR DE VIDEO PARA QUE ENVÍE A REPRODUCIR A YOUTUBE
    private void setearOnClickImageViewVideo() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyYoutube.isEmpty()) {
                    Toast.makeText(getContext(), "Video no disponible", Toast.LENGTH_SHORT).show();
                } else {
                    escuchadorFragmentDetalle.envioUrl(keyYoutube);
                }
            }
        });
    }

    //SETEAMOS EL BOTÓN DE COMPARTIR PELÍCULAS O SERIES EN LAS REDES
    private void setearOnClickButtonShare() {
        imageButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if (keyYoutube.isEmpty()) {
                    intent.putExtra(Intent.EXTRA_TEXT, "Hey! te recomiendo " + titulo + " Buscala aca: " + Constantes.URL_BASE_GOOGLE + titulo.replace(" ", "+") + "+pelicula");
                } else {

                    intent.putExtra(Intent.EXTRA_TEXT, "Hey! te recomiendo " + titulo + " mirala aca: " + Constantes.URL_BASE_YOUTUBE + keyYoutube);
                }
                startActivity(Intent.createChooser(intent, "COMPARTÍ POR MOVIE RANK"));
            }
        });

        //SETEAMOS BOTÓN DE PLAY TRAILER
        buttonPlayTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyYoutube.isEmpty()) {
                    Toast.makeText(getContext(), "Video no disponible", Toast.LENGTH_SHORT).show();
                } else {
                    escuchadorFragmentDetalle.envioUrl(keyYoutube);
                }
            }
        });
        setearOnClickButtonListaPorVer();
        setearOnClickButtonFavoritos();
    }

    private boolean estasLogeado() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        return (user != null);
    }

    //SETEAMOS LOS ESTADOS DE LOS BOTONES DE VER DESPUÉS Y FAVORITOS
    private void consultarSiEstaEnVerDespues(String id) {
        ControladorVerDespuesPeliculas controladorVerDespuesPeliculas = new ControladorVerDespuesPeliculas(getActivity().getApplicationContext());
        Identificable identificable;
        if (modo.equals(Constantes.PELICULAS)) {
            identificable = controladorVerDespuesPeliculas.consultaSiEstaVerDespuesMovieId(id);
        } else {
            identificable = controladorVerDespuesPeliculas.consultaSiEstaVerDespuesSerieId(id);
        }
        if (identificable != null) {
            verDespuesActivo = identificable.getIdIdentificable();
        } else {
            verDespuesActivo = null;
        }
        if (verDespuesActivo == null) {
            estadoVerDespues = false;
            imageButtonAddToList.setImageResource(R.drawable.ic_playlist_add_white_24dp);
        } else {
            estadoVerDespues = true;
            imageButtonAddToList.setImageResource(R.drawable.ic_playlist_add_check_green_24dp);
        }
    }
    //SETEAMOS BOTÓN VER DESPUÉS
    private void setearOnClickButtonListaPorVer() {
        imageButtonAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estasLogeado()) {
                    if (estadoVerDespues) {
                        estadoVerDespues = false;
                        imageButtonAddToList.setImageResource(R.drawable.ic_playlist_add_white_24dp);
                        eliminarDeRoomVerDespues(id);
                    } else {
                        estadoVerDespues = true;
                        imageButtonAddToList.setImageResource(R.drawable.ic_playlist_add_check_green_24dp);
                        cargarARoomVerDespues(id);
                    }
                } else {
                    Toast.makeText(getActivity(), "Para usar esta función tenés que iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //CARGAMOS EL ELEMENTO VER DESPUÉS A ROOM
    public void cargarARoomVerDespues(String id) {
        ControladorVerDespuesPeliculas controladorVerDespuesPeliculas = new ControladorVerDespuesPeliculas(getActivity().getApplicationContext());
        controladorVerDespuesPeliculas.addVerDespuesARoom(id, modo);
        cargarAFirebaseVerDespues(id);
    }
    // Y A FIREBASE TAMBIÉN
    public void cargarAFirebaseVerDespues(String idVerDespues) {
        ControladorUsuarios controladorUsuarios = new ControladorUsuarios();
        controladorUsuarios.cargarAFirebaseVerDespues(user.getUid(), idVerDespues, modo);
    }

    //ELIMINAMOS EL ELEMENTO VER DESPUÉS DE ROOM
    private void eliminarDeRoomVerDespues(String id) {
        ControladorVerDespuesPeliculas controladorVerDespuesPeliculas = new ControladorVerDespuesPeliculas(getActivity().getApplicationContext());
        controladorVerDespuesPeliculas.removeVerDespues(id, modo);
        eliminarDeFirebaseVerDespues(id);
    }

    //ELIMINAMOS EL ELEMENTO VER DESPUÉS DE FIREBASE
    private void eliminarDeFirebaseVerDespues(String id) {
        ControladorUsuarios controladorUsuarios = new ControladorUsuarios();
        controladorUsuarios.eliminarDeFirebaseVerDespues(user.getUid(), id, modo);
    }

    //SETEAMOS EL ESTADO DEL BOTÓN DE FAVORITOS
    private void consultarSiEstaFavorito(String id) {
        ControladorFavoritos controladorFavoritos = new ControladorFavoritos(getActivity().getApplicationContext());

        Identificable identificable;
        if (modo.equals(Constantes.PELICULAS)) {
            identificable = controladorFavoritos.consultaSiEstaFavoritoMovieId(id);
        } else {
            identificable = controladorFavoritos.consultaSiEstaFavoritoSerieId(id);
        }
        if (identificable != null) {
            favoritoActivo = identificable.getIdIdentificable();
        } else {
            favoritoActivo = null;
        }
        if (favoritoActivo == null) {
            estadoFavorito = false;
            imageButtonFavorito.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else {
            estadoFavorito = true;
            imageButtonFavorito.setImageResource(R.drawable.ic_favorite_red_24dp);
        }
    }

    //DAMOS FUNCIONALIDAD AL BOTÓN DE FAVORITOS
    private void setearOnClickButtonFavoritos() {
        imageButtonFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estasLogeado()) {
                    if (estadoFavorito) {
                        estadoFavorito = false;
                        imageButtonFavorito.setImageResource(R.drawable.ic_favorite_white_24dp);
                        eliminarDeRoomFavorito(id);
                    } else {
                        estadoFavorito = true;
                        imageButtonFavorito.setImageResource(R.drawable.ic_favorite_red_24dp);
                        cargarARoomFavorito(id);
                    }
                } else {
                    Toast.makeText(getActivity(), "Para usar esta función tenés que iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //CARGAMOS EL ELEMENTO FAVORITO A ROOM POR SU ID
    private void cargarARoomFavorito(String id) {
        ControladorFavoritos controladorFavoritos = new ControladorFavoritos(getActivity().getApplicationContext());
        controladorFavoritos.addFavoritoARoom(id, modo);
        cargarAFirebaseFavorito(id);
    }

    //CARGAMOS EL ELEMENTO FAVORITO A FIREBASE POR SU ID
    private void cargarAFirebaseFavorito(String idFavorito) {
        ControladorUsuarios controladorUsuarios = new ControladorUsuarios();
        controladorUsuarios.cargarAFirebaseFavorito(user.getUid(), idFavorito, modo);
    }

    //ELIMINAMOS EL ELEMENTO FAVORITO DE ROOM POR SU ID
    private void eliminarDeRoomFavorito(String id) {
        ControladorFavoritos controladorFavoritos = new ControladorFavoritos(getActivity().getApplicationContext());
        controladorFavoritos.removeFavorito(id, modo);
        eliminarDeFirebaseFavorito(id);
    }

    //ELIMINAMOS EL ELEMENTO FAVORITO DE FIREBASE POR SU ID
    private void eliminarDeFirebaseFavorito(String id) {
        ControladorUsuarios controladorUsuarios = new ControladorUsuarios();
        controladorUsuarios.eliminarDeFirebaseFavorito(user.getUid(), id, modo);
    }

    //CARGAMOS TODA LA INFORMACIÓN QUE NECESITA EL DETALLE DE LA SERIE O PELÍCULA
    private void cargosValores(Bundle bundle) {
        url_foto = bundle.getString(FOTO);
        titulo = bundle.getString(TITULO);
        id = bundle.getString(ID_LLAVE);
        resena = bundle.getString(RESENA);
        textViewResena.setText(resena);
        textViewTitulo.setText(titulo);
        String stringRanking = bundle.getString(RANKING);
        modo = bundle.getString(Constantes.MODO_INTERNO);
        consultarSiEstaFavorito(id);
        consultarSiEstaEnVerDespues(id);
        Glide.with(getActivity()).load(Constantes.URL_BASE_IMAGE + url_foto).into(imageView);
        ratingBar.setRating(conviertoRanking(stringRanking));
        ratingBar.setIsIndicator(true);
    }

    private float conviertoRanking(String ranking) {
        Float floatRanking = Float.parseFloat(ranking) / 2;
        return floatRanking;
    }

    public interface Identificable {
        String getIdIdentificable();
    }

    public interface EscuchadorFragmentDetalle {
        void envioUrl(String urlVideo);
    }
}
