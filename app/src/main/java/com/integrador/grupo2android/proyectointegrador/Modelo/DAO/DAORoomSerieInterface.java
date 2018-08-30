package com.integrador.grupo2android.proyectointegrador.Modelo.DAO;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;

import java.util.List;

@Dao
public interface DAORoomSerieInterface {
    //SERVICE DE SERIES EN ROOM

    @Query("SELECT * FROM Tv")
    List<Tv> getAllSerie();

    @Query("SELECT * FROM Tv WHERE lugarAMostrar LIKE :ranking ORDER BY Tv.posicionAMostrar DESC")
    List<Tv> getRankingSerie(String ranking);

    @Query("SELECT * FROM Tv WHERE name Like :name")
    Tv getSerieTitle(String name);

    @Insert
    void insertSerieAll(Tv... serie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSerieAll(List<Tv> Serie);

    @Delete
    void delete(Tv serie);

    @Query("SELECT * FROM Tv WHERE idGenero = :genres ORDER BY Tv.popularity DESC")
    List<Tv> getAllSeriePorGenero(Integer genres);
}
