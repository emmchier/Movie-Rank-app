package com.integrador.grupo2android.proyectointegrador.Vista.Activitys;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorUsuarios;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorVerDespuesPeliculas;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.ContenedorDeUsuarios;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Usuario;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.VerDespuesMovie;
import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Util.ResultListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityLogin extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private FirebaseUser user;
    private Usuario usuarioDeFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //CREAMOS AUTENTICACIÓN CON GOOGLE, FACEBOOK Y CUENTA PROPIA GRACIAS A FIREBASE
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.movie_rank_blanco)      // SETEAMOS EL LOGO
                        .setTheme(R.style.AppThemeLogin)
                        // SETEAMOS EL THEME
                        .build(),
                RC_SIGN_IN);

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // SI ES SATISFACTORIO SE LOGUEA PIDIENDO LA REFERENCIA EN FIREBASE.
                user = FirebaseAuth.getInstance().getCurrentUser();

                //BUSCAMOS EL ID DE USUARIO EN FIREBASE PARA VER SI EL USUARIO ACTUAL COINCIDE
                //CON ALGUNO DE LA BASE DE DATOS EN LA NUBE
                final ControladorUsuarios controladorUsuarios = new ControladorUsuarios();
                controladorUsuarios.obtenerUsuarios(new ResultListener<ContenedorDeUsuarios>() {
                    @Override
                    public void finish(ContenedorDeUsuarios resultado) {
                        for (Usuario usuario : resultado.getUsuarios()) {
                            if (usuario.getIdUser().equals(user.getUid())) {
                                usuarioDeFirebase = usuario;
                                break;
                            }
                        }
                        DatabaseReference mDatabase;
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        mDatabase = firebaseDatabase.getReference();

                        //SI EL USUARIO ESTÁ LOGUEADO, OBTIENE LA URL DE LA CUENTA CONECTADA Y LA PASA A STRING
                        if (usuarioDeFirebase == null) {
                            DatabaseReference referenceAgregoUsuario = mDatabase.child("usuarios").child(user.getUid());
                            Uri photoUrl = user.getPhotoUrl();
                            String foto = "0";
                            if (photoUrl != null) {
                                foto = photoUrl.toString();
                            }
                            Usuario usuario = new Usuario(user.getUid(), user.getDisplayName(), foto, "false");
                            referenceAgregoUsuario.setValue(usuario);
                        }

                        ResultListener<List<String>> escuchadorVista = new ResultListener<List<String>>() {
                            @Override
                            public void finish(List<String> resultado) {
                                List<VerDespuesMovie> listaVerDespuesMovie = new ArrayList<>();
                                for (String idVerDespues : resultado) {
                                    VerDespuesMovie verDespuesMovie = new VerDespuesMovie(idVerDespues);
                                    listaVerDespuesMovie.add(verDespuesMovie);
                                }
                                ControladorVerDespuesPeliculas controladorVerDespuesPeliculas = new ControladorVerDespuesPeliculas(getApplicationContext());
                                controladorVerDespuesPeliculas.addVerDespuesMovieARoom(listaVerDespuesMovie);
                            }
                        };
                        controladorUsuarios.obtenerListaIdPeliculasVerDespues(escuchadorVista, user.getUid());

                        Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
