package com.integrador.grupo2android.proyectointegrador.Modelo.DAO;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.FavoritoMovie;
import java.util.List;

@Dao
public interface DAORoomFavoritoMovieInterface {
    //SERVICE DE PELÍCULAS FAVORITAS EN ROOM

    @Query("SELECT * FROM FavoritoMovie")
    List<FavoritoMovie> getAllFavoritoMovie();

    @Query("SELECT * FROM FavoritoMovie")
    List<FavoritoMovie> getFavoritoMovie();

    @Query("SELECT * FROM FavoritoMovie WHERE idMovie = :idMovie")
    FavoritoMovie traemeTodasFavoritoMovieConMismoIdMovie(String idMovie);

    @Insert
    void insertFavoritoAll(FavoritoMovie... favoritoMovies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoritoAll(List<FavoritoMovie> favoritoMovies);

    @Query("DELETE FROM FavoritoMovie WHERE idMovie = :idMovie")
    void delete(String idMovie);

    @Delete
    void delete(FavoritoMovie favoritoMovie);
}
