package com.integrador.grupo2android.proyectointegrador.Vista.Adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.R;
import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerBuscadorPeliculas extends RecyclerView.Adapter {
    private List<Movie> listaDeMovie;
    private Context context;
    private EscuchadorRecyclerPeliculas escuchadorRecyclerPeliculas;

    public AdapterRecyclerBuscadorPeliculas(EscuchadorRecyclerPeliculas escuchadorRecyclerPeliculas) {
        this.escuchadorRecyclerPeliculas = escuchadorRecyclerPeliculas;
        this.listaDeMovie = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celda = layoutInflater.inflate(R.layout.celda_audiovisual_gird, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(celda);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = listaDeMovie.get(position);
        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
        movieViewHolder.asignarMovie(movie);
    }

    @Override
    public int getItemCount() {
        return listaDeMovie.size();
    }

    public void setMovies(List<Movie> movies) {
        if (movies != null) {
            listaDeMovie.clear();
            listaDeMovie.addAll(movies);
            notifyDataSetChanged();
        }
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
                    escuchadorRecyclerPeliculas.seleccionaronPelicula(getAdapterPosition());
                }
            });
        }

        public void asignarMovie(Movie movie) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w300/" + movie.getPoster_path()).into(imageViewMovie);
            textViewTitulo.setText(movie.getTitle());
        }
    }

    public interface EscuchadorRecyclerPeliculas {
        void seleccionaronPelicula(Integer posicion);
    }
}
