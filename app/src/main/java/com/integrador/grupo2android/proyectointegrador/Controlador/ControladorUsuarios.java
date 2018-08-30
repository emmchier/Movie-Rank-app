package com.integrador.grupo2android.proyectointegrador.Controlador;
import android.content.Context;
import com.integrador.grupo2android.proyectointegrador.Modelo.DAO.DAOFirebaseUsuarios;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.ContenedorDeUsuarios;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;
import com.integrador.grupo2android.proyectointegrador.Util.ResultListener;
import java.util.List;

public class ControladorUsuarios {

    Context context;
    private DAOFirebaseUsuarios daoFirebaseUsuarios;

    //HACEMOS PETICIONES A FIREBASE, OBTENIENDO LOS USUARIOS AUTENTICADOS
    public void obtenerUsuarios(final ResultListener<ContenedorDeUsuarios> escuchadorDeLaVista) {
        daoFirebaseUsuarios = new DAOFirebaseUsuarios();
        ResultListener<ContenedorDeUsuarios> escuchadorDelControlador = new ResultListener<ContenedorDeUsuarios>() {
            @Override
            public void finish(ContenedorDeUsuarios resultado) {
                escuchadorDeLaVista.finish(resultado);
            }
        };
        daoFirebaseUsuarios.obtenerUsuariosDeFirebase(escuchadorDelControlador);
    }

    //OBTENEMOS LA LISTA DE PELÍCULAS PARA VER DESPUES DEL USUARIO, SEGÚN SU ID
    public void obtenerListaIdPeliculasVerDespues(final ResultListener<List<String>> escuchadorVista, String idUser) {
        ResultListener<List<String>> escuchadorControlador = new ResultListener<List<String>>() {
            @Override
            public void finish(List<String> resultado) {
                escuchadorVista.finish(resultado);
            }
        };
        daoFirebaseUsuarios = new DAOFirebaseUsuarios();
        daoFirebaseUsuarios.obtenerListaVerDespuesPelicula(escuchadorControlador, idUser);
    }

    //OBTENEMOS LA LISTA DE ID DE PELÍCULAS FAVORITAS DEL USUARIO DE FIREBASE
    public void obtenerListaIdPeliculasFavoritas(final ResultListener<List<String>> escuchadorVista, String idUser) {
        ResultListener<List<String>> escuchadorControlador = new ResultListener<List<String>>() {
            @Override
            public void finish(List<String> resultado) {
                escuchadorVista.finish(resultado);
            }
        };
        daoFirebaseUsuarios = new DAOFirebaseUsuarios();
        daoFirebaseUsuarios.obtenerListaFavoritosPelicula(escuchadorControlador, idUser);
    }

    //OBTENEMOS LA LISTA DE ID DE SERIES PARA VER DESPUES DEL USUARIO DE FIREBASE
    public void obtenerListaIdSeriesVerDespues(final ResultListener<List<String>> escuchadorVista, String idUser) {
        ResultListener<List<String>> escuchadorControlador = new ResultListener<List<String>>() {
            @Override
            public void finish(List<String> resultado) {
                escuchadorVista.finish(resultado);
            }
        };
        daoFirebaseUsuarios = new DAOFirebaseUsuarios();
        daoFirebaseUsuarios.obtenerListaVerDespuesSerie(escuchadorControlador, idUser);
    }

    //OBTENEMOS LA LISTA DE ID DE SERIES FAVORITAS DEL USUARIO DE FIREBASE
    public void obtenerListaIdSeriesFavoritas(final ResultListener<List<String>> escuchadorVista, String idUser) {
        ResultListener<List<String>> escuchadorControlador = new ResultListener<List<String>>() {
            @Override
            public void finish(List<String> resultado) {
                escuchadorVista.finish(resultado);
            }
        };
        daoFirebaseUsuarios = new DAOFirebaseUsuarios();
        daoFirebaseUsuarios.obtenerListaFavoritosSerie(escuchadorControlador, idUser);
    }

    //CARGAMOS LOS ID DE PELÍCULAS O SERIES PARA VER DESPUÉS DEL USUARIO A FIREBASE
    public void cargarAFirebaseVerDespues(String uid, String idVerDespues, String modo) {
        daoFirebaseUsuarios = new DAOFirebaseUsuarios();
        if (modo.equals(Constantes.PELICULAS)) {
            daoFirebaseUsuarios.cargarAFirebaseVerDespuesMovie(uid, idVerDespues);
        } else {
            daoFirebaseUsuarios.cargarAFirebaseVerDespuesSerie(uid, idVerDespues);
        }
    }

    //CARGAMOS LOS ID DE PELÍCULAS O SERIES FAVORITAS DEL USUARIO A FIREBASE
    public void cargarAFirebaseFavorito(String uid, String idFavorito, String modo) {
        daoFirebaseUsuarios = new DAOFirebaseUsuarios();
        if (modo.equals(Constantes.PELICULAS)) {
            daoFirebaseUsuarios.cargarAFirebaseFavoritoMovie(uid, idFavorito);
        } else {
            daoFirebaseUsuarios.cargarAFirebaseFavoritoSerie(uid, idFavorito);
        }
    }

    //ELIMINAMOS LOS ID DE PELÍCULAS O SERIES PARA VER DESPUÉS DEL USUARIO DE FIREBASE
    public void eliminarDeFirebaseVerDespues(String user, String id, String modo) {
        daoFirebaseUsuarios = new DAOFirebaseUsuarios();
        if (modo.equals(Constantes.PELICULAS)) {
            daoFirebaseUsuarios.eliminarDeFirebaseVerDespuesMovie(user, id);
        } else {
            daoFirebaseUsuarios.eliminarDeFirebaseVerDespuesSerie(user, id);
        }
    }

    //ELIMINAMOS LOS ID DE PELÍCULAS O SERIES PARA VER DESPUÉS DEL USUARIO DE FIREBASE
    public void eliminarDeFirebaseFavorito(String uid, String idFavorito, String modo) {
        daoFirebaseUsuarios = new DAOFirebaseUsuarios();
        if (modo.equals(Constantes.PELICULAS)) {
            daoFirebaseUsuarios.eliminarDeFirebaseFavoritoMovie(uid, idFavorito);
        } else {
            daoFirebaseUsuarios.eliminarDeFirebaseFavoritoSerie(uid, idFavorito);
        }
    }
}
