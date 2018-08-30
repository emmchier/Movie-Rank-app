package com.integrador.grupo2android.proyectointegrador.Modelo.POJO;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.google.gson.annotations.Expose;

@Entity
public class Genero {
    @PrimaryKey
    @NonNull
    private Integer id;

    @Expose
    private String tipoDeGenero;

    public Genero(Integer id) {
        this.id = id;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getTipoDeGenero() {
        return tipoDeGenero;
    }

    public void setTipoDeGenero(String tipoDeGenero) {
        this.tipoDeGenero = tipoDeGenero;
    }
}
