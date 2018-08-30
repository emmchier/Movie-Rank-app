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
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;
import com.integrador.grupo2android.proyectointegrador.R;
import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerBuscadorSeries extends RecyclerView.Adapter {
    private List<Tv> listaDeTv;
    private Context context;
    private EscuchadorRecyclerSeries escuchadorRecyclerSeries;

    public AdapterRecyclerBuscadorSeries(EscuchadorRecyclerSeries escuchadorRecyclerSeries) {
        this.listaDeTv = new ArrayList<>();
        this.escuchadorRecyclerSeries = escuchadorRecyclerSeries;
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
        Tv tv = listaDeTv.get(position);
        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
        movieViewHolder.asignarMovie(tv);
    }

    @Override
    public int getItemCount() {
        return listaDeTv.size();
    }

    public void setTv(List<Tv> tv) {
        if (tv != null) {
            listaDeTv.clear();
            listaDeTv.addAll(tv);
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
                    escuchadorRecyclerSeries.seleccionaronSerie(getAdapterPosition());
                }
            });
        }

        public void asignarMovie(Tv tv) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w300/" + tv.getPoster_path()).into(imageViewMovie);
            textViewTitulo.setText(tv.getName());
        }
    }

    public interface EscuchadorRecyclerSeries {
        void seleccionaronSerie(Integer posicion);
    }
}
