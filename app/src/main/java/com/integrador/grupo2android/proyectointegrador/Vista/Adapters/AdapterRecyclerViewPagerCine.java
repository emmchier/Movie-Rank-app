package com.integrador.grupo2android.proyectointegrador.Vista.Adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.R;
import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerViewPagerCine extends RecyclerView.Adapter {
    private List<Movie> listaPeliculas;
    private Context context;
    private EscuchadorAdapterCine escuchadorAdapterCine;
    private String modointerno;

    public AdapterRecyclerViewPagerCine(Context context, String modointerno, EscuchadorAdapterCine escuchadorAdapterCine) {
        this.escuchadorAdapterCine = escuchadorAdapterCine;
        this.context = context;
        this.listaPeliculas = new ArrayList<>();
        this.modointerno = modointerno;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celda = layoutInflater.inflate(R.layout.celda_audiovisual_gird, parent, false);
        AdapterRecyclerViewPagerCine.MovieViewHolder movieViewHolder = new AdapterRecyclerViewPagerCine.MovieViewHolder(celda);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = listaPeliculas.get(position);
        AdapterRecyclerViewPagerCine.MovieViewHolder movieViewHolder = (AdapterRecyclerViewPagerCine.MovieViewHolder) holder;
        movieViewHolder.cargarDatos(movie);
    }

    public void agregarPeliculas(List<Movie> listaPeliculas) {
        this.listaPeliculas.clear();
        if (listaPeliculas != null) {
            this.listaPeliculas.addAll(listaPeliculas);
        } else {
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaPeliculas.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPortada;
        private TextView textViewTitulo;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageViewPortada = itemView.findViewById(R.id.imageViewAudiovisualCartelera);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    escuchadorAdapterCine.peliculaSeleccionada(getAdapterPosition(), modointerno);
                }
            });
        }

        public void cargarDatos(Movie movie) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w300/" + movie.getPoster_path()).into(imageViewPortada);
            textViewTitulo.setText(movie.getTitle());
        }
    }

    public interface EscuchadorAdapterCine {
        void peliculaSeleccionada(Integer posicion, String modoInterno);
    }
}
