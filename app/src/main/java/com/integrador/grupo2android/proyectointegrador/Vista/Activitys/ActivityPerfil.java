package com.integrador.grupo2android.proyectointegrador.Vista.Activitys;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.integrador.grupo2android.proyectointegrador.Controlador.ControladorUsuarios;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.ContenedorDeUsuarios;
import com.integrador.grupo2android.proyectointegrador.Modelo.POJO.Usuario;
import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Util.GlideApp;
import com.integrador.grupo2android.proyectointegrador.Util.ResultListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class ActivityPerfil extends AppCompatActivity {
    @BindView(R.id.textViewNombre)
    TextView textViewNombre;

    @BindView(R.id.imageViewFotoPerfil)
    ImageView imageViewFotoPerfil;

    @BindView(R.id.buttonLogout)
    CardView buttonLogout;

    FirebaseUser user;
    Usuario usuarioActivo;

    private String currentDateTimeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ButterKnife.bind(this);

        //CONECTAMOS CON FIREBASE
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            startActivity(new Intent(ActivityPerfil.this, ActivityLogin.class));
            return;
        }
        leerListaDeUsuarios();
        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar_perfil);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //BOTÓN PARA VOLVER ATRÁS
                onBackPressed();
                finish();
            }
        });

        //CERRAR SESIÓN
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(ActivityPerfil.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intentLogin = new Intent(ActivityPerfil.this, ActivityHome.class);
                                startActivity(intentLogin);
                                finish();
                            }
                        });
            }
        });

        //CARGAMOS UNA FOTO DE PERFIL DESDE NUESTRO DISPOSITIVO
        imageViewFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeFacha(view);
            }
        });
    }

    //LEEMOS LA LISTA DE USUARIO PARA COMPROBAR LA AUTENTICACIÓN DEL LOGIN
    public void leerListaDeUsuarios(){
        final ControladorUsuarios controladorUsuarios = new ControladorUsuarios();
        controladorUsuarios.obtenerUsuarios(new ResultListener<ContenedorDeUsuarios>() {
            @Override
            public void finish(ContenedorDeUsuarios resultado) {
                for (Usuario usuario : resultado.getUsuarios()) {
                    if (usuario.getIdUser().equals(user.getUid())){
                        usuarioActivo = usuario;
                        break;
                    }
                }
                //SI EL USUARIO ESTÁ LOGUEADO, SETEA EL NOMBRE DE LA CUENTA
                //Y CARGA LA FOTO MEDIANTE "GLIDE"
                textViewNombre.setText(user.getDisplayName());
                cargarFotoPerfil();
            }
        });
    }

    //BUSCA LA FOTO EN LA CUENTA DEL USUARIO Y LA CARGA
    public void cargarFotoPerfil() {
        if (usuarioActivo.getFoto().equals("0")){
            imageViewFotoPerfil.setImageResource(R.drawable.sinfoto);
        }else if(usuarioActivo.getCargoFoto().equals("false")){
            GlideApp
                    .with(this)
                    .load(usuarioActivo.getFoto())
                    .centerCrop()
                    .into(imageViewFotoPerfil);
        }else{
            //SI NO LA ENCUENTRA, LA BUSCA EN EL STORAGE
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child(usuarioActivo.getFoto());
            GlideApp
                    .with(this)
                    .load(storageRef)
                    .centerCrop()
                    .into(imageViewFotoPerfil);
        }
    }

    //CARGAMOS A FIREBASE LOS DATOS DE LA NUEVA FOTO
    public void cargarAFirebaseDatabase(){
        DatabaseReference mDatabase;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        mDatabase.child("usuarios").child(user.getUid()).child("foto").setValue("/photos_user/" + user.getUid() +"/"+ user.getUid()+ currentDateTimeString);
        mDatabase.child("usuarios").child(user.getUid()).child("cargoFoto").setValue("true");
    }
    //MÉTODO PARA CARGAR LA FOTO DESDE EL DISPOSITIVO
    //Y TODOS LOS PROCESOS SIGUIENTES
    public void takeFacha(View view) {
        EasyImage.openChooserWithGallery(this,"ELEGI", 1);
    }
    public void uploadFacha() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference deleteFile = storageRef.child(usuarioActivo.getFoto());
        deleteFile.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ActivityPerfil.this, "Previous Image Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        StorageReference imageRef = storageRef.child("photos_user").child(user.getUid()).child(user.getUid()+currentDateTimeString);

        imageViewFotoPerfil.setDrawingCacheEnabled(true);
        imageViewFotoPerfil.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageViewFotoPerfil.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(ActivityPerfil.this, "subida fallida", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                cargarAFirebaseDatabase();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(ActivityPerfil.this, "Error"+ String.valueOf(type), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                //Handle the images
                File imageFile = imagesFiles.get(0);
                Bitmap bitmapDeImagen = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                imageViewFotoPerfil.setImageBitmap(bitmapDeImagen);
                uploadFacha();
            }
        });
    }
}
