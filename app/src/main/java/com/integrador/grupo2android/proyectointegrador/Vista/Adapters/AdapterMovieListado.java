package com.integrador.grupo2android.proyectointegrador.Vista.Adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;
import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;
import java.util.ArrayList;
import java.util.List;

public class AdapterMovieListado extends RecyclerView.Adapter {

    private List<Movie> listaDeMovie;
    private List<Tv> listaDeSerie;
    private Context context;
    private String modoInteno;
    private EscuchadorDelAdapterMovieListado escuchadorDelAdapterMovieListado;

    public AdapterMovieListado(Context context, String modoInteno, EscuchadorDelAdapterMovieListado escuchadorDelAdapterMovieListado) {
        this.listaDeMovie = new ArrayList<>();
        this.listaDeSerie = new ArrayList<>();
        this.context = context;
        this.modoInteno = modoInteno;
        this.escuchadorDelAdapterMovieListado = escuchadorDelAdapterMovieListado;
    }

    //RECYCLERVIEW
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celda = layoutInflater.inflate(R.layout.celda_audiovisual_gird, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(celda);
        return movieViewHolder;
    }

    //CARGAMOS LAS CELDAS SEGÚN EL MODO INTERNO QUE RECIBIMOS
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (modoInteno.equals(Constantes.SERIES)) {
            Tv tv = listaDeSerie.get(position);
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            movieViewHolder.asignarSerie(tv);
        } else {
            Movie movie = listaDeMovie.get(position);
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            movieViewHolder.asignarMovie(movie);
        }
    }

    @Override
    public int getItemCount() {
        if (modoInteno.equals(Constantes.SERIES)) {
            return listaDeSerie.size();
        } else {
            return listaDeMovie.size();
        }
    }

    //SETEAMOS UNA LISTA DE SERIES
    public void setTv(List<Tv> listaSeries) {
        this.listaDeSerie.clear();
        if (listaSeries != null) {
            this.listaDeSerie.addAll(listaSeries);
        } else {
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }

    //SETEAMOS UNA LISTA DE PELÍCULAS
    public void setMovies(List<Movie> listaDeMovieNuevos) {
        this.listaDeMovie.clear();
        if (listaDeMovieNuevos != null) {
            this.listaDeMovie.addAll(listaDeMovieNuevos);
        } else {
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }

    private class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewMovie;
        private TextView textViewTitulo;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageViewMovie = itemView.findViewById(R.id.imageViewAudiovisualCartelera);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modoInteno.equals(Constantes.SERIES)) {
                        escuchadorDelAdapterMovieListado.seleccionaronTv(getAdapterPosition());
                    } else {
                        escuchadorDelAdapterMovieListado.seleccionaronMovie(getAdapterPosition());
                    }
                }
            });
        }

        public void asignarMovie(Movie movie) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w300/" + movie.getPoster_path()).into(imageViewMovie);
            textViewTitulo.setText(movie.getTitle());
        }

        public void asignarSerie(Tv tv) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w300/" + tv.getPoster_path()).into(imageViewMovie);
            textViewTitulo.setText(tv.getName());
        }
    }

    public interface EscuchadorDelAdapterMovieListado {
        void seleccionaronMovie(Integer posicion);
        void seleccionaronTv(Integer posicion);
    }
}