package com.integrador.grupo2android.proyectointegrador.Modelo.POJO;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ContenedorDeTv {
    //GUARDAMOS TODOS LOS RESULTADOS DESCARGADOS DE INTERNET EN UN CONTENEDOR DE SERIES

    @SerializedName("results")
    private List<Tv> tv;

    public ContenedorDeTv(List<Tv> listaTv) {
        this.tv = listaTv;
    }

    public List<Tv> getTv() {
        return tv;
    }

    public void setTv(List<Tv> tv) {
        this.tv = tv;
    }
}


