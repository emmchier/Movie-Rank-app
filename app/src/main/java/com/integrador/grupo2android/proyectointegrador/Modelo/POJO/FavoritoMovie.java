package com.integrador.grupo2android.proyectointegrador.Modelo.POJO;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.integrador.grupo2android.proyectointegrador.Vista.Fragments.FragmentDetalle;

@Entity
public class FavoritoMovie implements FragmentDetalle.Identificable {
    //IMPLEMENTAMOS LA INTERFACE Identificable EN EL FRAGMENT DETALLE PARA PODER GUARDAR
    //PELÍCULAS FAVORITAS EN LA LISTA DE PELÍCULAS FAVORITAS DEL USUARIO

    @PrimaryKey
    @NonNull
    private String idMovie;

    public FavoritoMovie(@NonNull String idMovie) {
        this.idMovie = idMovie;
    }

    @NonNull
    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(@NonNull String idMovie) {
        this.idMovie = idMovie;
    }

    @Override
    public String getIdIdentificable() {
        return getIdMovie();
    }
}
