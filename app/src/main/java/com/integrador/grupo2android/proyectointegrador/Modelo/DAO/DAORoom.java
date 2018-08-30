package com.integrador.grupo2android.proyectointegrador.Modelo.DAO;
import android.arch.persistence.room.Room;
import android.content.Context;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.FavoritoMovie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.FavoritoSerie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Genero;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.VerDespuesMovie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.VerDespuesSerie;
import com.integrador.grupo2android.proyectointegrador.Util.RoomAppDatabase;
import java.util.List;

public class DAORoom {
    RoomAppDatabase roomAppDatabase;
    private List<Movie> carteleraMovie;

    //PELÍCULA

    //CREAMOS LA BASE DE DATOS EN ROOM
    public DAORoom(Context context) {
        roomAppDatabase = Room.databaseBuilder(context,
                RoomAppDatabase.class, "database-name").allowMainThreadQueries().build();
    }

    //OBTENEMOS TODAS LAS PELÍCULAS DE ROOM
    public List<Movie> getAllMovies() {
        return roomAppDatabase.daoRoomMovie().getAllMovie();
    }

    //OBTENEMOS EL RANKING DE PELÍCULAS MEJOR VALORADAS DE ROOM
    public List<Movie> getRankingMovies(String lugarAMostrar) {
        return roomAppDatabase.daoRoomMovie().getRankingMovies(lugarAMostrar);
    }

    //OBTENEMOS EL TÍTULO DE LA PELÍCULA EN ROOM
    public Movie getMovieTitle(String title) {
        return roomAppDatabase.daoRoomMovie().getMovieTitle(title);
    }

    //AGREGAMOS UNA PELÍCULA A ROOM
    public void insertMovieAll(Movie... movies) {
        roomAppDatabase.daoRoomMovie().insertMovieAll(movies);
    }

    //AGREGAMOS TODAS LAS PELÍCULAS A ROOM
    public void insertMovieAll(List<Movie> movies) {
        roomAppDatabase.daoRoomMovie().insertMovieAll(movies);
    }

    //ELIMINAMOS UNA PELÍCULA DE ROOM
    public void delete(Movie movies) {
        roomAppDatabase.daoRoomMovie().delete(movies);
    }

    //VER DESPUES MOVIE

    //OBTENEMOS DE ROOM TODAS LAS PELÍCULAS PARA VER DESPUÉS
    public List<VerDespuesMovie> getAllVerDespuesMovies() {
        return roomAppDatabase.daoRoomVerDespuesMovieInterface().getAllVerDespuesMovie();
    }

    //OBTENEMOS DE ROOM UNA LISTA DE PELÍCULAS PARA VER DESPUÉS
    public List<VerDespuesMovie> obtenerVerDespuesDeLasMovieVerDespues() {
        return roomAppDatabase.daoRoomVerDespuesMovieInterface().getVerDespuesMovie();
    }

    //AGREGAMOS A ROOM TODAS LAS PELÍCULAS PALA VER DESPUÉS
    public void insertVerDespuesAll(VerDespuesMovie... verDespuesMovies) {
        roomAppDatabase.daoRoomVerDespuesMovieInterface().insertVerDespuesAll(verDespuesMovies);
    }

    //AGREGAMOS A ROOM TODAS LAS SERIES PALA VER DESPUÉS
    public void insertVerDespuesAll(VerDespuesSerie... verDespuesSeries) {
        roomAppDatabase.daoRoomVerDespuesSerieInterface().insertVerDespuesAll(verDespuesSeries);
    }

    //AGREGAMOS A ROOM UNA LISTA DE PELÍCULAS PALA VER DESPUÉS
    public void insertVerDespuesMovieAll(List<VerDespuesMovie> verDespuesMovies) {
        roomAppDatabase.daoRoomVerDespuesMovieInterface().insertVerDespuesAll(verDespuesMovies);
    }

    //AGREGAMOS A ROOM UNA LISTA DE SERIES PALA VER DESPUÉS
    public void insertVerDespuesSerieAll(List<VerDespuesSerie> verDespuesSeries) {
        roomAppDatabase.daoRoomVerDespuesSerieInterface().insertVerDespuesAll(verDespuesSeries);
    }

    //BORRAMOS DE ROOM UNA PELÍCULA PARA VER DESPUÉS
    public void deleteVerDespuesMovie(String id) {
        roomAppDatabase.daoRoomVerDespuesMovieInterface().delete(id);
    }

    //CONSULTAMOS POR UNA PELÍCULA EN ROOM SEGÚN SU ID
    public VerDespuesMovie consultaSiEstaVerDespuesMovieId(String id) {
        return roomAppDatabase.daoRoomVerDespuesMovieInterface().traemeTodasVerDespuesMovieConMismoIdMovie(id);
    }

    //FAVORITO MOVIE

    //OBTENEMOS DE ROOM TODAS LAS PELÍCULAS FAVORITAS
    public List<FavoritoMovie> getAllFavoritoMovie() {
        return roomAppDatabase.daoRoomFavoritoMovieInterface().getAllFavoritoMovie();
    }

    //OBTENEMOS DE ROOM UNA LISTA DE PELÍCULAS FAVORITAS
    public List<FavoritoMovie> obtenerFavoritoMovieDeLasMovieFavorito() {
        return roomAppDatabase.daoRoomFavoritoMovieInterface().getFavoritoMovie();
    }

    //AGREGAMOS A ROOM TODAS LAS PELÍCULAS FAVORITAS
    public void insertFavoritoMovieAll(FavoritoMovie... favoritoMovies) {
        roomAppDatabase.daoRoomFavoritoMovieInterface().insertFavoritoAll(favoritoMovies);
    }

    //AGREGAMOS A ROOM UNA LISTA DE PELÍCULAS FAVORITAS
    public void insertFavoritoMovieAll(List<FavoritoMovie> favoritoMovies) {
        roomAppDatabase.daoRoomFavoritoMovieInterface().insertFavoritoAll(favoritoMovies);
    }

    //AGREGAMOS A ROOM UNA LISTA DE TODAS LAS PELÍCULAS FAVORITAS
    public void insertVFavoritoMovieAll(List<FavoritoMovie> favoritoMovies) {
        roomAppDatabase.daoRoomFavoritoMovieInterface().insertFavoritoAll(favoritoMovies);
    }

    //BORRAMOS DE ROOM UNA PELÍCULAS FAVORITA POR SU ID
    public void deleteFavoritoMovie(String id) {
        roomAppDatabase.daoRoomFavoritoMovieInterface().delete(id);
    }

    //CONSULTAMOS EN ROOM SI LA PELÍCULA FAVORITA ESTÁ EN LA LISTA SEGÚN SU ID
    public FavoritoMovie consultaSiEstaFavoritoMovieId(String id) {
        return roomAppDatabase.daoRoomFavoritoMovieInterface().traemeTodasFavoritoMovieConMismoIdMovie(id);
    }

    //SERIE

    //OBTENEMOS DE ROOM TODAS LAS SERIES
    public List<Tv> getAllSeries() {
        return roomAppDatabase.daoRoomSerie().getAllSerie();
    }

    //OBTENEMOS DE ROOM EL RANKING DE SERIES MEJOR VALORADAS
    public List<Tv> getRankingSeries(String ranking) {
        return roomAppDatabase.daoRoomSerie().getRankingSerie(ranking);
    }

    //OBTENEMOS DE ROOM EL TÍTULO DE LA SERIE
    public Tv getSerieTitle(String title) {
        return roomAppDatabase.daoRoomSerie().getSerieTitle(title);
    }

    //AGREGAMOS A ROOM TODAS LAS SERIES
    public void insertSerieAll(Tv... series) {
        roomAppDatabase.daoRoomSerie().insertSerieAll(series);
    }

    //AGREGAMOS A ROOM UNA LISTA DE SERIES
    public void insertSerieAll(List<Tv> series) {
        roomAppDatabase.daoRoomSerie().insertSerieAll(series);
    }

    //BORRAMOS DE ROOM UNA SERIE
    public void delete(Tv movies) {
        roomAppDatabase.daoRoomSerie().delete(movies);
    }

    //SERIE PARA VER DESPUÉS

    //CONSULTAMOS EN ROOMSI ESTÁ LA SERIE PARA VER DESPUÉS EN LA LISTA SEGÚN SU ID
    public VerDespuesSerie consultaSiEstaVerDespuesSerieId(String id) {
        return roomAppDatabase.daoRoomVerDespuesSerieInterface().traemeTodasVerDespuesSerieConMismoIdSerie(id);
    }

    //OBTENEMOS DE ROOM UNA LISTA DE SERIES PARA VER DESPUÉS
    public List<VerDespuesSerie> obtenerVerDespuesDeLasSerieVerDespues() {
        return roomAppDatabase.daoRoomVerDespuesSerieInterface().getVerDespuesSerie();
    }

    //BORRAMOS DE ROOM UNA SERIE PARA VER DESPUÉS POR SU ID
    public void deleteVerDespuesSerie(String id) {
        roomAppDatabase.daoRoomVerDespuesSerieInterface().delete(id);
    }

    //SERIE FAVORITA

    //OBTENEMOS DE ROOM TODAS LAS SERIES FAVORITAS
    public List<FavoritoSerie> getAllFavoritoSerie() {
        return roomAppDatabase.daoRoomFavoritoSerieInterface().getAllFavoritoSerie();
    }

    //OBTENEMOS DE ROOM UNA LISTA DE SERIES FAVORITAS
    public List<FavoritoSerie> obtenerFavoritoSerieDeLasSerieFavorito() {
        return roomAppDatabase.daoRoomFavoritoSerieInterface().getFavoritoSerie();
    }

    //AGREGAMOS A ROOM TODAS LAS SERIES FAVORITAS
    public void insertFavoritoSerieAll(FavoritoSerie... favoritoSerie) {
        roomAppDatabase.daoRoomFavoritoSerieInterface().insertFavoritoAll(favoritoSerie);
    }

    //AGREGAMOS A ROOM UN LISTADO DE SERIES FAVORITAS
    public void insertFavoritoSerieAll(List<FavoritoSerie> favoritoSerie) {
        roomAppDatabase.daoRoomFavoritoSerieInterface().insertFavoritoAll(favoritoSerie);
    }

    //BORRAMOS DE ROOM LA SERIE FAVORITA POR SU ID
    public void deleteFavoritoSerie(String id) {
        roomAppDatabase.daoRoomFavoritoSerieInterface().delete(id);
    }

    //CONSULTAMOS EN ROOM SI LA SERIE FAVORITA ESTÁ EN LA LISTA POR SU ID
    public FavoritoSerie consultaSiEstaFavoritoSerieId(String id) {
        return roomAppDatabase.daoRoomFavoritoSerieInterface().traemeTodasFavoritoSerieConMismoIdSerie(id);
    }

    //OBTENEMOS DE ROOM UNA PELÍCULA EN CARTELERA DE CINE
    public List<Movie> getMovieCartelera(String lugar) {
        return roomAppDatabase.daoRoomMovie().getMovieCartelera(lugar);
    }

    //OBTENEMOS DE ROOM UN PRÓXIMO ESTRENO DE CINE
    public List<Movie> getMovieProximosEstrenos(String lugar) {
        return roomAppDatabase.daoRoomMovie().getMovieProximosEstrenos(lugar);
    }

    //AGREGAMOS A ROOM UNA LISTA DE GENEROS
    public void insertGeneroAll(List<Genero> generos) {
        roomAppDatabase.daoRoomGeneroInterface().insertGeneroAll(generos);
    }

    //AGREGAMOS A ROOM TODOS LOS GENEROS
    public void insertGeneroAll(Genero... generos) {
        roomAppDatabase.daoRoomGeneroInterface().insertGeneroAll(generos);
    }

    //OBTENEMOS DE ROOM UNA LISTA DE GENEROS POR SU TIPO
    public List<Genero> getGenero(String tipo) {
        return roomAppDatabase.daoRoomGeneroInterface().getGeneroTipo(tipo);
    }

    //OBTENEMOS DE ROOM TODAS LAS PELÍCULAS POR GENERO
    public List<Movie> getAllMoviesPorGenero(Integer genres) {
        return roomAppDatabase.daoRoomMovie().getAllMoviePorGenero(genres);
    }

    //OBTENEMOS DE ROOM TODAS LAS SERIES POR GENERO
    public List<Tv> getAllSeriesPorGenero(Integer genres) {
        return roomAppDatabase.daoRoomSerie().getAllSeriePorGenero(genres);
    }
}
