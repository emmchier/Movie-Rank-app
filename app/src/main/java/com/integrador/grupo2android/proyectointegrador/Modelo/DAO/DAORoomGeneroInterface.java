package com.integrador.grupo2android.proyectointegrador.Modelo.DAO;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Genero;
import java.util.List;

@Dao
public interface DAORoomGeneroInterface {
    //SERVICE DE GÉNEROS EN ROOM

    @Query("SELECT * FROM Genero")
    List<Genero> getAllGenero();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGeneroAll(Genero... generos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGeneroAll(List<Genero> generos);

    @Delete
    void delete(Genero genero);

    @Query("SELECT * FROM Genero WHERE tipoDeGenero Like :tipo")
    List<Genero> getGeneroTipo(String tipo);
}
