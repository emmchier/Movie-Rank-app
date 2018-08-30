package com.integrador.grupo2android.proyectointegrador.Modelo.POJO;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ContenedorDeGeneros {
    //GUARDAMOS TODOS LOS RESULTADOS DESCARGADOS DE INTERNET EN UN CONTENEDOR DE GENEROS

    @SerializedName("genres")
    private List<Genero> listaGeneros;

    public ContenedorDeGeneros(List<Genero> listaGeneros) {
        this.listaGeneros = listaGeneros;
    }

    public List<Genero> getListaGeneros() {
        return listaGeneros;
    }

    public void setListaGeneros(List<Genero> listaGeneros) {
        this.listaGeneros = listaGeneros;
    }
}
