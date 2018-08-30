package com.integrador.grupo2android.proyectointegrador.Modelo.POJO;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.integrador.grupo2android.proyectointegrador.Vista.Fragments.FragmentDetalle;

@Entity
public class FavoritoSerie implements FragmentDetalle.Identificable {
    //IMPLEMENTAMOS LA INTERFACE Identificable EN EL FRAGMENT DETALLE PARA PODER GUARDAR
    //SERIES FAVORITAS EN LA LISTA DE SERIES FAVORITAS DEL USUARIO

    @PrimaryKey
    @NonNull
    private String idSerie;

    public FavoritoSerie(String idSerie) {
        this.idSerie = idSerie;
    }

    @NonNull
    public String getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(@NonNull String idSerie) {
        this.idSerie = idSerie;
    }

    @Override
    public String getIdIdentificable() {
        return getIdSerie();
    }
}
