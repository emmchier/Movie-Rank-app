package com.integrador.grupo2android.proyectointegrador.Modelo.DAO;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import java.util.List;

@Dao
public interface DAORoomMovieInterface {
    //SERVICE DE PELÍCULAS EN ROOM

    @Query("SELECT * FROM Movie")
    List<Movie> getAllMovie();

    @Query("SELECT * FROM Movie WHERE lugarAMostrar LIKE :ranking ORDER BY Movie.posicionAMostrar DESC")
    List<Movie> getRankingMovies(String ranking);

    @Query("SELECT * FROM Movie WHERE title Like :title")
    Movie getMovieTitle(String title);

    @Insert
    void insertMovieAll(Movie... movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieAll(List<Movie> movies);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM Movie WHERE lugarAMostrar Like :lugar ORDER BY Movie.popularity DESC")
    List<Movie> getMovieCartelera(String lugar);

    @Query("SELECT * FROM Movie WHERE lugarAMostrar Like :lugar ORDER BY Movie.popularity DESC")
    List<Movie> getMovieProximosEstrenos(String lugar);

    @Query("SELECT * FROM Movie WHERE idGenero = :genres ORDER BY Movie.popularity DESC")
    List<Movie> getAllMoviePorGenero(Integer genres);
}
