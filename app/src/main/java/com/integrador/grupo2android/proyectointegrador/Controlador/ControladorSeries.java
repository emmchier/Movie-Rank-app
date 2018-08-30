package com.integrador.grupo2android.proyectointegrador.Controlador;
import android.content.Context;
import com.integrador.grupo2android.proyectointegrador.Modelo.DAO.DAOInternet;
import com.integrador.grupo2android.proyectointegrador.Modelo.DAO.DAORoom;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.FavoritoSerie;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Genero;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Tv;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.VerDespuesSerie;
import com.integrador.grupo2android.proyectointegrador.Util.HTTPConnectionManager;
import com.integrador.grupo2android.proyectointegrador.Util.ResultListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.integrador.grupo2android.proyectointegrador.Util.Constantes.LISTA_RANKING_SERIES;
import static com.integrador.grupo2android.proyectointegrador.Util.Constantes.SERIES;

public class ControladorSeries {
    private DAOInternet daoInternet = new DAOInternet();
    private Context context;

    public ControladorSeries(Context context) {
        this.context = context;
    }

    //OBTENEMOS LAS 3 SERIES MEJOR VALORADAS EN INTERNET
    public void obtenerSeriesRanking(final ResultListener<List<Tv>> escuchadorVista, String language, Integer page) {
        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            ResultListener<List<Tv>> escuchadorControlador = new ResultListener<List<Tv>>() {
                @Override
                public void finish(List<Tv> resultado) {
                    if (resultado != null && resultado.size() > 0) {

                        Tv serie1 = resultado.get(0);
                        Tv serie2 = resultado.get(1);
                        Tv serie3 = resultado.get(2);

                        //SIEMPRE LA PRIMER SERIE TIENE EL MAYOR PUNTAJE
                        serie1.setPosicionAMostrar(3);
                        serie2.setPosicionAMostrar(2);
                        serie3.setPosicionAMostrar(1);

                        List<Tv> listaDeRankingAMostrar = new ArrayList<>();
                        for (Tv serie : resultado) {
                            serie.setLugarAMostrar(LISTA_RANKING_SERIES);
                            listaDeRankingAMostrar.add(serie);
                        }

                        //SIEMPRE, GUARDAMOS LAS SERIES QUE BAJAMOS DE INTERNET EN ROOM
                        DAORoom daoRoom = new DAORoom(context);
                        daoRoom.insertSerieAll(listaDeRankingAMostrar);
                        escuchadorVista.finish(listaDeRankingAMostrar);
                    }
                }
            };

            //BAJAMOS LAS SERIES POR INTERNET A TRAVÉS DEL CONTROLADOR
            daoInternet.obtenerSeriesRanking(escuchadorControlador, language, page);
        } else {

            //O SE LAS PEDIMOS A ROOM
            DAORoom daoRoom = new DAORoom(context);
            List<Tv> listaDeSeries = daoRoom.getRankingSeries(LISTA_RANKING_SERIES);
            escuchadorVista.finish(listaDeSeries);
        }
    }

    //OBTENEMOS SERIES FILTRADAS POR SU GENERO Y CALIFICACIÓN
    public void obtenerSeriesFiltradas(final ResultListener<List<Tv>> escuchadorVista, final Integer genres, Integer calification, String language, Integer page) {
        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            ResultListener<List<Tv>> escuchadorControlador = new ResultListener<List<Tv>>() {
                @Override
                public void finish(List<Tv> resultado) {
                    DAORoom daoRoom = new DAORoom(context);
                    List<Tv> listaACargar = new ArrayList<>();
                    for (Tv serie : resultado) {
                        serie.setIdGenero(genres);
                        listaACargar.add(serie);
                    }

                    daoRoom.insertSerieAll(listaACargar);
                    escuchadorVista.finish(resultado);
                }
            };
            daoInternet.obtenerSeriesFiltrada(escuchadorControlador, genres, calification, language, page);
        } else {
            DAORoom daoRoom = new DAORoom(context);
            List<Tv> listaDeMovies = daoRoom.getAllSeriesPorGenero(genres);
            if (listaDeMovies.size() > 0) {
                escuchadorVista.finish(listaDeMovies);
            }
        }
    }

    //OBTENEMOS LOS TÍTULOS DE LAS SERIES EN STRINGS
    public void searchSeriesText(final ResultListener<List<Tv>> escuchadorVista, String language, String query, Integer page) {
        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            ResultListener<List<Tv>> escuchadorControlador = new ResultListener<List<Tv>>() {
                @Override
                public void finish(List<Tv> resultado) {
                    DAORoom daoRoom = new DAORoom(context);
                    daoRoom.insertSerieAll(resultado);
                    escuchadorVista.finish(resultado);
                }
            };
            daoInternet.buscarSeriesTexto(escuchadorControlador, language, query, page);

        } else {
            DAORoom daoRoom = new DAORoom(context);
            List<Tv> listaDeSeries = daoRoom.getAllSeries();

            //RECORREMOS LA LISTA Y BUSCAMOS UN MATCH ENTRE EL STRING QUE TENEMOS Y ALGUNO DE LA LISTA
            //SI LA SERIE COINCIDE CON EL MISMO STRING ENCONTRADO, LA DEVUELVE
            String aguja = query;
            List<Tv> listaAMostar = new ArrayList<>();
            for (Tv serie : listaDeSeries) {
                String pajar = serie.getName();
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(aguja) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(pajar);
                if (match.find()) {
                    listaAMostar.add(serie);
                }
            }
            escuchadorVista.finish(listaAMostar);
        }
    }

    //HACEMOS FUNCIONAR EL BOTÓN DE SERIES PARA AGREGAR A LA LISTA DE VER DESPUÉS
    public List<Tv> consultoListaPorVerDespues() {
        ControladorVerDespuesPeliculas controladorVerDespuesPeliculas = new ControladorVerDespuesPeliculas(context);
        List<VerDespuesSerie> listaVerDespuesSerie = controladorVerDespuesPeliculas.obtenerVerDespuesDeLasSerieVerDespues();

        List<Tv> listaAMostrar = new ArrayList<>();

        List<String> listDeIdsMoviesParaMostrar = new ArrayList<>();

        for (VerDespuesSerie verDespuesSerie : listaVerDespuesSerie) {
            listDeIdsMoviesParaMostrar.add(verDespuesSerie.getIdSerie());
        }
        List<Tv> listaDeSeries = getSeries();
        for (Tv serie : listaDeSeries) {
            if (listDeIdsMoviesParaMostrar.contains(serie.getId().toString())) {
                listaAMostrar.add(serie);
            }
        }
        return listaAMostrar;
    }

    //HACEMOS FUNCIONAR EL BOTÓN DE SERIES PARA AGREGAR A LA LISTA DE FAVORITOS
    public List<Tv> consultoListaPorFavorito() {
        ControladorFavoritos controladorFavoritos = new ControladorFavoritos(context);
        List<FavoritoSerie> listaFavoritoSerie = controladorFavoritos.obtenerFavoritoDeLasSerieFavorito();

        List<Tv> listaAMostrar = new ArrayList<>();

        List<String> listDeIdsMoviesParaMostrar = new ArrayList<>();

        for (FavoritoSerie favoritoSerie : listaFavoritoSerie) {
            listDeIdsMoviesParaMostrar.add(favoritoSerie.getIdSerie());
        }
        List<Tv> listaDeSeries = getSeries();
        for (Tv serie : listaDeSeries) {
            if (listDeIdsMoviesParaMostrar.contains(serie.getId().toString())) {
                listaAMostrar.add(serie);
            }
        }
        return listaAMostrar;
    }

    //OBTENEMOS TODAS LAS SERIES DE ROOM
    public List<Tv> getSeries() {
        DAORoom dataBase = new DAORoom(context);
        return dataBase.getAllSeries();
    }

    //AGREGAMOS UNA SERIE A ROOM
    public void addSerieARoom(Tv... serie) {
        DAORoom dataBase = new DAORoom(context);
        dataBase.insertSerieAll(serie);
    }

    //AGREGAMOS UNA LISTA DE SERIES A ROOM
    public void addSerieARoom(List<Tv> series) {
        DAORoom dataBase = new DAORoom(context);
        dataBase.insertSerieAll(series);
    }

    //BORRAMOS UNA SERIE DE ROOM
    public void removeTv(Tv movie) {
        DAORoom dataBase = new DAORoom(context);
        dataBase.delete(movie);
    }

    //OBTENEMOS UN TÍTULO DE UNA SERIE DE ROOM
    public Tv getTvTitle(String title) {
        DAORoom dataBase = new DAORoom(context);
        return dataBase.getSerieTitle(title);
    }

    //OBTENEMOS EL LISTADO DE GENEROS DE SERIES Y LO GUARDAMOS EN ROOM
    public void obtenerListadoDeGenerosSeries(final ResultListener<List<Genero>> escuchadorVista, String language) {
        if (HTTPConnectionManager.isNetworkingOnline(context)) {
            final ResultListener<List<Genero>> escuchadorControlador = new ResultListener<List<Genero>>() {
                @Override
                public void finish(List<Genero> resultado) {
                    List<Genero> listaParaRoom = new ArrayList<>();
                    for (Genero genero : resultado) {
                        genero.setTipoDeGenero(SERIES);
                        listaParaRoom.add(genero);
                    }
                    DAORoom daoRoom = new DAORoom(context);
                    daoRoom.insertGeneroAll(listaParaRoom);
                    escuchadorVista.finish(resultado);
                }
            };
            daoInternet.obtenerListadoDeGenerosSerie(escuchadorControlador, language);

        } else {
            DAORoom daoRoom = new DAORoom(context);
            List<Genero> listaDeGenero = daoRoom.getGenero(SERIES);
            escuchadorVista.finish(listaDeGenero);
        }
    }
}
