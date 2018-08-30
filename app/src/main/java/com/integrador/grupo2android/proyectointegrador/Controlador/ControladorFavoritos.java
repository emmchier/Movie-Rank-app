package com.integrador.grupo2android.proyectointegrador.Controlador;
import android.content.Context;
import com.integrador.grupo2android.proyectointegrador.Modelo.DAO.DAORoom;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.FavoritoMovie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.FavoritoSerie;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;
import java.util.List;

public class ControladorFavoritos {
    private Context context;

    public ControladorFavoritos(Context context) {
        this.context = context;
    }

    //CONTROLADOR ROOM DE PELICULAS

    public List<FavoritoMovie> getFavoritoMovie() {
        DAORoom dataBase = new DAORoom(context);
        return dataBase.getAllFavoritoMovie();
    }

    //AGREGAMOS A ROOM PELÍCULAS Y SERIES FAVORITAS DEL USUARIO
    public void addFavoritoARoom(String id, String modo) {
        DAORoom dataBase = new DAORoom(context);

        if (modo.equals(Constantes.PELICULAS)) {
            FavoritoMovie favoritoMovie = new FavoritoMovie(id);
            dataBase.insertFavoritoMovieAll(favoritoMovie);
        } else {
            FavoritoSerie favoritoSerie = new FavoritoSerie(id);
            dataBase.insertFavoritoSerieAll(favoritoSerie);
        }
    }

    //AGREGAMOS SOLO PELÍCULA FAVORITA DEL USUARIO A ROOM
    public void addFavoritoMovieARoom(List<FavoritoMovie> favoritoMovies) {
        DAORoom dataBase = new DAORoom(context);
        dataBase.insertFavoritoMovieAll(favoritoMovies);
    }

    //AGREGAMOS SOLO SERIES FAVORITA DEL USUARIO A ROOM
    public void addFavoritoSerieARoom(List<FavoritoSerie> favoritoSeries) {
        DAORoom dataBase = new DAORoom(context);
        dataBase.insertFavoritoSerieAll(favoritoSeries);
    }

    //REMOVEMOS SERIES O PELÍCULAS FAVORITAS DE ROOM
    public void removeFavorito(String id, String modo) {
        DAORoom dataBase = new DAORoom(context);
        if (modo.equals(Constantes.PELICULAS)) {
            dataBase.deleteFavoritoMovie(id);
        } else {
            dataBase.deleteFavoritoSerie(id);
        }
    }

    //OBTENEMOS UNA PELÍCULA FAVORITA DEL USUARIO DESDE ROOM
    public List<FavoritoMovie> obtenerFavoritoDeLasMovieFavorito() {
        DAORoom dataBase = new DAORoom(context);
        return dataBase.obtenerFavoritoMovieDeLasMovieFavorito();
    }

    //OBTENEMOS UNA SERIE FAVORITA DEL USUARIO DESDE ROOM
    public List<FavoritoSerie> obtenerFavoritoDeLasSerieFavorito() {
        DAORoom dataBase = new DAORoom(context);
        return dataBase.obtenerFavoritoSerieDeLasSerieFavorito();
    }

    //CONSULTAMOS SI LA PELÍCULA FAVORITA DEL USUARIO ESTÁ EN ROOM POR SU ID
    public FavoritoMovie consultaSiEstaFavoritoMovieId(String id) {
        DAORoom dataBase = new DAORoom(context);
        return dataBase.consultaSiEstaFavoritoMovieId(id);
    }

    //CONSULTAMOS SI LA SERIE FAVORITA DEL USUARIO ESTÁ EN ROOM POR SU ID
    public FavoritoSerie consultaSiEstaFavoritoSerieId(String id) {
        DAORoom dataBase = new DAORoom(context);
        return dataBase.consultaSiEstaFavoritoSerieId(id);
    }
}

