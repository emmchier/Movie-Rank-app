package com.integrador.grupo2android.proyectointegrador.Modelo.POJO;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.integrador.grupo2android.proyectointegrador.Vista.Fragments.FragmentDetalle;

@Entity
public class VerDespuesSerie implements FragmentDetalle.Identificable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    private String idSerie;

    public VerDespuesSerie(String idSerie) {
        this.idSerie = idSerie;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(String idSerie) {
        this.idSerie = idSerie;
    }

    public String getIdIdentificable() {
        return getIdSerie();
    }
}
