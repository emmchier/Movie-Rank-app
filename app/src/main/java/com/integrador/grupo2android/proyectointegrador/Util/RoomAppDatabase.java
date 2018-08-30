package com.integrador.grupo2android.proyectointegrador.Util;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.integrador.grupo2android.proyectointegrador.Modelo.DAO.DAORoomFavoritoMovieInterface;
import com.integrador.grupo2android.proyectointegrador.Modelo.DAO.DAORoomFavoritoSerieInterface;
import com.integrador.grupo2android.proyectointegrador.Modelo.DAO.DAORoomGeneroInterface;
import com.integrador.grupo2android.proyectointegrador.Modelo.DAO.DAORoomMovieInterface;
import com.integrador.grupo2android.proyectointegrador.Modelo.DAO.DAORoomSerieInterface;
import com.integrador.grupo2android.proyectointegrador.Modelo.DAO.DAORoomVerDespuesMovieInterface;
import com.integrador.grupo2android.proyectointegrador.Modelo.DAO.DAORoomVerDespuesSerieInterface;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.FavoritoMovie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.FavoritoSerie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Genero;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.VerDespuesMovie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.VerDespuesSerie;

@Database(entities = {Movie.class, FavoritoMovie.class, FavoritoSerie.class, VerDespuesMovie.class, VerDespuesSerie.class, Tv.class, Genero.class}, version = 1)
public abstract class RoomAppDatabase extends RoomDatabase {
    //CREAMOS TODA LA BASE DE DATOS DE ROOM

    //DAO PELICULAS
    public abstract DAORoomMovieInterface daoRoomMovie();

    public abstract DAORoomSerieInterface daoRoomSerie();

    //DAO FAVORITOS MOVIE Y SERIES
    public abstract DAORoomFavoritoMovieInterface daoRoomFavoritoMovieInterface();

    public abstract DAORoomFavoritoSerieInterface daoRoomFavoritoSerieInterface();

    //DAO VER DESPUES MOVIE Y SERIES
    public abstract DAORoomVerDespuesMovieInterface daoRoomVerDespuesMovieInterface();

    public abstract DAORoomVerDespuesSerieInterface daoRoomVerDespuesSerieInterface();

    public abstract DAORoomGeneroInterface daoRoomGeneroInterface();

}
