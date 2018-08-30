package com.integrador.grupo2android.proyectointegrador.Modelo;
import java.util.ArrayList;
import java.util.List;

public final class Harcodeo {
    //DATOS QUE NECESITAMOS OBTENERLOS DE CLASES EN EL PROYECTO

    public static List<String> generoPeliculas() {
        List<String> listaDeGenerosPeliculas = new ArrayList<>();
        listaDeGenerosPeliculas.add("Action");
        listaDeGenerosPeliculas.add("Adventure");
        listaDeGenerosPeliculas.add("Animation");
        listaDeGenerosPeliculas.add("Comedy");
        listaDeGenerosPeliculas.add("Crime");
        listaDeGenerosPeliculas.add("Documentary");
        listaDeGenerosPeliculas.add("Drama");
        listaDeGenerosPeliculas.add("Family");
        listaDeGenerosPeliculas.add("Fantasy");
        listaDeGenerosPeliculas.add("History");
        listaDeGenerosPeliculas.add("Horror");
        listaDeGenerosPeliculas.add("Music");
        listaDeGenerosPeliculas.add("Mystery");
        listaDeGenerosPeliculas.add("Romance");
        listaDeGenerosPeliculas.add("Science Fiction");
        listaDeGenerosPeliculas.add("TV Movie");
        listaDeGenerosPeliculas.add("Thriller");
        listaDeGenerosPeliculas.add("War");
        listaDeGenerosPeliculas.add("Western");

        return listaDeGenerosPeliculas;
    }

    public static List<String> generoSeries() {
        List<String> listaDeGenerosSeries = new ArrayList<>();
        listaDeGenerosSeries.add("Action & Adventure");
        listaDeGenerosSeries.add("Animation");
        listaDeGenerosSeries.add("Comedy");
        listaDeGenerosSeries.add("Crime");
        listaDeGenerosSeries.add("Documentary");
        listaDeGenerosSeries.add("Drama");
        listaDeGenerosSeries.add("Family");
        listaDeGenerosSeries.add("Kids");
        listaDeGenerosSeries.add("Mystery");
        listaDeGenerosSeries.add("News");
        listaDeGenerosSeries.add("Reality");
        listaDeGenerosSeries.add("Fantasy");
        listaDeGenerosSeries.add("History");
        listaDeGenerosSeries.add("Horror");
        listaDeGenerosSeries.add("Sci-Fi & Fantasy");
        listaDeGenerosSeries.add("Soap");
        listaDeGenerosSeries.add("Romance");
        listaDeGenerosSeries.add("Talk");
        listaDeGenerosSeries.add("War & Politics");
        listaDeGenerosSeries.add("Western");

        return listaDeGenerosSeries;
    }
}

