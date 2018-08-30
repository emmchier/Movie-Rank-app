package com.integrador.grupo2android.proyectointegrador.Vista.Adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.ContenedorDePeliculas;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.R;
import java.util.ArrayList;
import java.util.List;

public class AdapterCine extends RecyclerView.Adapter {
    private List<Movie> listaDeLaCarteleraCine;
    private Context context;

    public AdapterCine(Context context) {
        this.listaDeLaCarteleraCine = new ArrayList<>();
        this.context = context;
    }

    //RECYCLERVIEW
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celda = layoutInflater.inflate(R.layout.celda_audiovisual_gird, parent, false);
        AdapterCine.ViewHolderCarteleraCine viewHolderCarteleraCine = new AdapterCine.ViewHolderCarteleraCine(celda);
        return viewHolderCarteleraCine;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = listaDeLaCarteleraCine.get(position);
        AdapterCine.ViewHolderCarteleraCine viewHolderCarteleraCine = (AdapterCine.ViewHolderCarteleraCine) holder;
        viewHolderCarteleraCine.asignarPeliculaEnCartelera(movie);
    }

    private class ViewHolderCarteleraCine extends RecyclerView.ViewHolder {
        private ImageView imageViewMovie;
        private TextView textViewTitulo;

        public ViewHolderCarteleraCine(View itemView) {
            super(itemView);
            imageViewMovie = itemView.findViewById(R.id.imageViewAudiovisualCartelera);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
        }

        //OBTENEMOS DE LA API TMDB LA URL CON LA FOTO DE LA PELÍCULA MEDIANTE GLIDE + EL TÍTULO DE LA PELÍCULA
        public void asignarPeliculaEnCartelera(Movie movie) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + movie.getPoster_path()).into(imageViewMovie);
            textViewTitulo.setText(movie.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return listaDeLaCarteleraCine.size();
    }

    public void setMovies(ContenedorDePeliculas listaDeMovieNuevos) {
        this.listaDeLaCarteleraCine.clear();
        this.listaDeLaCarteleraCine.addAll(listaDeMovieNuevos.getMovies());
        notifyDataSetChanged();
    }
}
