package com.integrador.grupo2android.proyectointegrador.Modelo.DAO;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.FavoritoSerie;
import java.util.List;

@Dao
public interface DAORoomFavoritoSerieInterface {
    //SERVICE DE SERIES FAVORITAS EN ROOM

    @Query("SELECT * FROM FavoritoSerie")
    List<FavoritoSerie> getAllFavoritoSerie();

    @Query("SELECT * FROM FavoritoSerie")
    List<FavoritoSerie> getFavoritoSerie();

    @Query("SELECT * FROM FavoritoSerie WHERE idSerie = :idSerie")
    FavoritoSerie traemeTodasFavoritoSerieConMismoIdSerie(String idSerie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoritoAll(FavoritoSerie... favoritoSeries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoritoAll(List<FavoritoSerie> favoritoSeries);

    @Query("DELETE FROM FavoritoSerie WHERE idSerie = :idSerie")
    void delete(String idSerie);

    @Delete
    void delete(FavoritoSerie FavoritoSerie);
}
