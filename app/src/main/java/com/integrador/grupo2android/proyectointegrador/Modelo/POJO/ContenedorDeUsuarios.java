package com.integrador.grupo2android.proyectointegrador.Modelo.POJO;
import java.util.List;

public class ContenedorDeUsuarios {
    //GUARDAMOS TODOS LOS RESULTADOS DESCARGADOS DE FIREBASE EN UN CONTENEDOR DE USUARIOS

    private List<Usuario> usuarios;

    public void setMovies(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ContenedorDeUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

}
