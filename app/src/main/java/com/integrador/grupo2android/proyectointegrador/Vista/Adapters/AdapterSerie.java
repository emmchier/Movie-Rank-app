package com.integrador.grupo2android.proyectointegrador.Vista.Adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;
import com.integrador.grupo2android.proyectointegrador.R;
import java.util.List;

public class AdapterSerie extends RecyclerView.Adapter {
    private List<Tv> listaDeTvs;
    private EscuchadorDelAdapterSerie escuchadorDelAdapterSerie;
    private String tituloDeLaLista;
    private Context context;

    public AdapterSerie(EscuchadorDelAdapterSerie escuchadorDelAdapterSerie, List<Tv> listaDeTvs, String tituloDeLaLista) {
        this.escuchadorDelAdapterSerie = escuchadorDelAdapterSerie;
        this.listaDeTvs = listaDeTvs;
        this.tituloDeLaLista = tituloDeLaLista;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celda = layoutInflater.inflate(R.layout.celda_audiovisual, parent, false);
        SerieViewHolder serieViewHolder = new SerieViewHolder(celda);
        return serieViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tv serie = listaDeTvs.get(position);SerieViewHolder serieViewHolder = (SerieViewHolder) holder;
        serieViewHolder.asignarSerie(serie);
    }

    private class SerieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewMovie;

        public SerieViewHolder(View itemView) {
            super(itemView);
            imageViewMovie = itemView.findViewById(R.id.imageViewAudiovisualCartelera);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    escuchadorDelAdapterSerie.seleccionaronSerie(getAdapterPosition(), tituloDeLaLista);
                }
            });
        }
        public void asignarSerie(Tv serie) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w300/" + serie.getPoster_path()).into(imageViewMovie);
        }
    }

    public void setTvs(List<Tv> listaDeTvsNuevos) {
        this.listaDeTvs.clear();
        if (listaDeTvsNuevos != null) {
            this.listaDeTvs.addAll(listaDeTvsNuevos);
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Lista Null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return listaDeTvs.size();
    }

    public interface EscuchadorDelAdapterSerie {
        void seleccionaronSerie(Integer posicion, String tituloDeLaLista);
    }
}