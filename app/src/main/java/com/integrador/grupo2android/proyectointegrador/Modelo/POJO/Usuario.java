package com.integrador.grupo2android.proyectointegrador.Modelo.POJO;
import java.util.List;

public class Usuario {
    //SETEAMOS LOS DATOS DEL USUARIO DE FIREBASE

    private String idUser;
    private String nombre;
    private String foto;
    private String cargoFoto;
    private List<String> listaDePeliculasFavoritos;

    public Usuario() {
    }

    public Usuario(String idUser, String nombre, String foto, String cargoFoto, List<String> listaDePeliculasFavoritos) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.foto = foto;
        this.cargoFoto = cargoFoto;
        this.listaDePeliculasFavoritos = listaDePeliculasFavoritos;
    }

    public Usuario(String idUser, String nombre, String foto, String cargoFoto) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.foto = foto;
        this.cargoFoto = cargoFoto;
    }

    public Usuario(String idUser, String nombre, String foto) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.foto = foto;
        this.cargoFoto = cargoFoto;
    }

    public String getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUser='" + idUser + '\'' +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                ", cargoFoto='" + cargoFoto + '\'' +
                ", listaDePeliculasFavoritas=" + listaDePeliculasFavoritos +
                '}';
    }

    public List<String> getListaDePeliculasFavoritas() {
        return listaDePeliculasFavoritos;
    }

    public String getCargoFoto() {
        return cargoFoto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }
}