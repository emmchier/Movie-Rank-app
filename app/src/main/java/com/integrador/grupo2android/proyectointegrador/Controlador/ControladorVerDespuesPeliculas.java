package com.integrador.grupo2android.proyectointegrador.Controlador;
import android.content.Context;
import com.integrador.grupo2android.proyectointegrador.Modelo.DAO.DAORoom;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.VerDespuesMovie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.VerDespuesSerie;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;
import java.util.List;

public class ControladorVerDespuesPeliculas {

    private Context context;

    public ControladorVerDespuesPeliculas(Context context) {
        this.context = context;
    }

    //CONTROLADOR ROOM DE PELÍCULAS

    //OBTENEMOS DE ROOM LA LISTA DE PELÍCULAS PARA VER DESPUÉS
    public List<VerDespuesMovie> getVerDespuesMovie() {
        DAORoom dataBase = new DAORoom(context);
        return dataBase.getAllVerDespuesMovies();
    }

    //AGREGAMOS A ROOM UN ID DE PELÍCULA O SERIE PARA VER DESPUÉS
    public void addVerDespuesARoom(String id, String modo) {
        DAORoom dataBase = new DAORoom(context);

        if (modo.equals(Constantes.PELICULAS)) {
            VerDespuesMovie verDespuesMovie = new VerDespuesMovie(id);
            dataBase.insertVerDespuesAll(verDespuesMovie);
        } else {
            VerDespuesSerie verDespuesSerie = new VerDespuesSerie(id);
            dataBase.insertVerDespuesAll(verDespuesSerie);
        }
    }

    //AGREGAMOS A ROOM UNA LISTA DE PELÍCULAS O SERIES PARA VER DESPUÉS
    public void addVerDespuesMovieARoom(List<VerDespuesMovie> verDespuesMovies) {
        DAORoom dataBase = new DAORoom(context);
        dataBase.insertVerDespuesMovieAll(verDespuesMovies);
    }

    //AGREGAMOS A ROOM UNA LISTA DE SERIES PARA VER DESPUÉS
    public void addVerDespuesSerieARoom(List<VerDespuesSerie> verDespuesSeries) {
        DAORoom dataBase = new DAORoom(context);
        dataBase.insertVerDespuesSerieAll(verDespuesSeries);
    }

    //REMOVEMOS UNA PELÍCULA O SERIE DE ROOM POR SU ID
    public void removeVerDespues(String id, String modo) {
        DAORoom dataBase = new DAORoom(context);
        if (modo.equals(Constantes.PELICULAS)) {
            dataBase.deleteVerDespuesMovie(id);
        } else {
            dataBase.deleteVerDespuesSerie(id);
        }
    }

    //OBTENEMOS UNA LISTA DE PELÍCULAS PARA VER DESPUÉS DE ROOM
    public List<VerDespuesMovie> obtenerVerDespuesDeLasMovieVerDespues() {
        DAORoom dataBase = new DAORoom(context);
        return dataBase.obtenerVerDespuesDeLasMovieVerDespues();
    }

    //OBTENEMOS UNA LISTA DE SERIES PARA VER DESPUÉS DE ROOM
    public List<VerDespuesSerie> obtenerVerDespuesDeLasSerieVerDespues() {
        DAORoom dataBase = new DAORoom(context);
        return dataBase.obtenerVerDespuesDeLasSerieVerDespues();
    }

    //CONSULTAMOS SI LA SERIE ESTÁ EN ROOM POR SU ID
    public VerDespuesSerie consultaSiEstaVerDespuesSerieId(String id) {
        DAORoom dataBase = new DAORoom(context);
        return dataBase.consultaSiEstaVerDespuesSerieId(id);
    }

    //CONSULTAMOS SI LA PELÍCULA ESTÁ EN ROOM POR SU ID
    public VerDespuesMovie consultaSiEstaVerDespuesMovieId(String id) {
        DAORoom dataBase = new DAORoom(context);
        return dataBase.consultaSiEstaVerDespuesMovieId(id);
    }
}

