package com.integrador.grupo2android.proyectointegrador.Vista.Activitys;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Vista.Adapters.AdapterChat;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Mensaje;
import java.util.Date;

public class ActivityChat extends AppCompatActivity {
    private EditText editTextInsertarTexto;
    private FloatingActionButton fabBotonEnviar;
    private ImageView backButton;
    private TextView textViewNombreUsuarioChat;
    private ImageView imageUserChat;
    private RecyclerView recyclerViewChat;
    private AdapterChat adapterChat;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        editTextInsertarTexto = findViewById(R.id.editTextInsertarTexto);
        fabBotonEnviar = findViewById(R.id.fabBotonEnviar);
        textViewNombreUsuarioChat = findViewById(R.id.textViewNombreUsuarioChat);
        imageUserChat = findViewById(R.id.imageUserChat);
        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        backButton = findViewById(R.id.backButton);

        //CONEXIÓN CON FIREBASE
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //SI EL USUARIO NO ES NULO, OBTENEMOS NOMBRE E IMAGEN POR LA LIBRERÍA GLIDE
            textViewNombreUsuarioChat.setText(user.getDisplayName());
            Glide.with(this).load(user.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imageUserChat);
        }

        //RECYCLERVIEW
        adapterChat = new AdapterChat();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewChat.setLayoutManager(layoutManager);
        recyclerViewChat.setAdapter(adapterChat);

        if (!hayInternet()) {
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show();
        }

        //REFERENCIAMOS AL CHAT EN FIREBASE
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("CHAT");

        //SETEAMOS BOTÓN DE ENVIAR MENSAJES
        fabBotonEnviar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String mensaje = editTextInsertarTexto.getText().toString();
                String nameUser = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
                editTextInsertarTexto.setText("");
                databaseReference.push().setValue(new Mensaje(mensaje, nameUser, date));
                recyclerViewChat.smoothScrollToPosition(adapterChat.getItemCount());
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //ESCUCHAMOS LOS MENSAJES EFECTUADOS EN EL CHAT Y OBTENEMOS RESULTADOS
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Mensaje unMensaje = dataSnapshot.getValue(Mensaje.class);
                adapterChat.agregarMensaje(unMensaje);
                //AUNQUE SE ESCRIBAN MENSAJES, EL RECYCLERVIEW SIEMPRE SCROLLEA HACIA LA BASE
                //DONDE SE ESCRIBEN LOS MENSAJES
                recyclerViewChat.smoothScrollToPosition(adapterChat.getItemCount());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mantenerLaVistaDelRecyclerHaciaArriba();
    }

    private boolean hayInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    //SIEMPRE LOS MENSAJES SE ESCRIBEN DE ABAJO PARA ARRIBA Y NO AL REVÉS
    private void mantenerLaVistaDelRecyclerHaciaArriba() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
}
