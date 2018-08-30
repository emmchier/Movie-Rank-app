package com.integrador.grupo2android.proyectointegrador.Modelo.DAO;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.ContenedorDeGeneros;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.ContenedorDePeliculas;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.ContenedorDeTv;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DAOServicePeliculas {
    //SERVICE DE PELÍCULAS

    @GET("movie/{movieId}")
    Call<Movie> getMovieDetail(@Path("movieId") String movieId,
                               @Query("api_key") String apiKey,
                               @Query("language") String language);

    @GET("genre/movie/list")
    Call<ContenedorDeGeneros> getGenreListMovie(@Query("api_key") String apiKey,
                                                @Query("language") String language);

    @GET("movie/top_rated")
    Call<ContenedorDePeliculas> getTopRatedMovies(@Query("api_key") String apiKey,
                                                  @Query("language") String language,
                                                  @Query("page") Integer page);

    @GET("tv/top_rated")
    Call<ContenedorDeTv> getTopRatedTv(@Query("api_key") String apiKey,
                                       @Query("language") String language,
                                       @Query("page") Integer page);

    @GET("discover/movie")
    Call<ContenedorDePeliculas> getSearchMoviesFilter(@Query("api_key") String apiKey,
                                                      @Query("with_genres") Integer genres,
                                                      @Query("language") String language,
                                                      @Query("vote_average.gte") Integer calification,
                                                      @Query("year") Integer year,
                                                      @Query("page") Integer page);

    @GET("discover/tv")
    Call<ContenedorDeTv> getSearchTvFilter(@Query("api_key") String apiKey,
                                           @Query("with_genres") Integer genres,
                                           @Query("language") String language,
                                           @Query("page") Integer page);

    @GET("search/movie")
    Call<ContenedorDePeliculas> getSearchMovie(@Query("api_key") String apiKey,
                                               @Query("language") String language,
                                               @Query("query") String query,
                                               @Query("page") Integer page);

    @GET("search/tv")
    Call<ContenedorDeTv> getSearchTv(@Query("api_key") String apiKey,
                                     @Query("language") String language,
                                     @Query("query") String query,
                                     @Query("page") Integer page);

    @GET("movie/now_playing")
    Call<ContenedorDePeliculas> getNowPlayingMovies(@Query("api_key") String apiKey,
                                                    @Query("language") String language,
                                                    @Query("page") Integer page);

    @GET("movie/upcoming")
    Call<ContenedorDePeliculas> getUpComingMovies(@Query("api_key") String apiKey,
                                                  @Query("language") String language,
                                                  @Query("page") Integer page);

    @GET("genre/tv/list")
    Call<ContenedorDeGeneros> getGenreListTv(@Query("api_key") String apiKey,
                                             @Query("language") String language);
}