package com.integrador.grupo2android.proyectointegrador.Modelo.POJO;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.google.gson.annotations.Expose;
import java.io.Serializable;

@Entity
public class Movie implements Serializable {
    //SETEAMOS TODOS LOS DATOS DEL OBJETO PELÍCULA
    //LOS GUARDAMOS EN UN SERIALIZABLE

    @PrimaryKey
    @NonNull
    private Integer id;
    private String title;
    private String poster_path;
    private String backdrop_path;
    private String release_date;
    private String vote_average;
    private String vote_count;
    private String overview;
    private boolean video;
    private Double popularity;
    private Integer idGenero;

    @Expose
    private String lugarAMostrar;
    @Expose
    private Integer posicionAMostrar;

    public Movie(String title, Integer id, String poster_path, String backdrop_path, String release_date, String vote_average, String vote_count, String overview, boolean video, Double popularity) {
        this.title = title;
        this.id = id;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.overview = overview;
        this.video = video;
        this.popularity = popularity;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getLugarAMostrar() {
        return lugarAMostrar;
    }

    public void setLugarAMostrar(String lugarAMostrar) {
        this.lugarAMostrar = lugarAMostrar;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public void setPosicionAMostrar(Integer posicionAMostrar) {
        this.posicionAMostrar = posicionAMostrar;
    }

    public Integer getPosicionAMostrar() {
        return posicionAMostrar;
    }
}
