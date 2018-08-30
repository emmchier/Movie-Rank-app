package com.integrador.grupo2android.proyectointegrador.Vista.Activitys;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;
import com.integrador.grupo2android.proyectointegrador.Vista.Adapters.ViewPagerPrincipalAdapterCasa;
import com.integrador.grupo2android.proyectointegrador.Vista.Adapters.ViewPagerPrincipalAdapterCine;
import com.integrador.grupo2android.proyectointegrador.Vista.Fragments.FragmentCasa;
import com.integrador.grupo2android.proyectointegrador.Vista.Fragments.FragmentCine;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityPrincipal extends AppCompatActivity implements FragmentCasa.EscuchadorDelFragmentCasa, FragmentCine.EscuchadorFragmentCine {

    private FragmentManager fragmentManager;
    public static String modoSeleccionado;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPagerPrincipal)
    ViewPager viewPager;

    @BindView(R.id.bottomBar)
    LinearLayout bottomBar;

    @BindView(R.id.buttonMenu)
    ImageButton buttonMenu;

    @BindView(R.id.buttonChat)
    ImageButton buttonChat;

    @BindView(R.id.buttonPerfil)
    ImageButton buttonPerfil;

    @BindView(R.id.imageViewDado)
    ImageView imageViewDado;

    @BindView(R.id.buttonBuscador)
    ImageButton buttonBuscador;

    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;

    @BindView(R.id.navigationView)
    NavigationView navigationView;

    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);

        //OBTENEMOS EL MODO QUE VIENE DESDE ActivityHome COMO CASA O CINE
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            modoSeleccionado = bundle.getString("modo");
        }

        //TOOLBAR
        setSupportActionBar(myToolbar);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //TABLAYOUT CON VIEWPAGER
        tabLayout.setupWithViewPager(viewPager);
        cargarFragment(modoSeleccionado,fragmentTransaction);
        setearEscuchadoresBottomBar();
        navigationDrawer();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_toolbar, menu);
        return true;
    }

    //SETEAMOS BOTONES TOOLBAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buttonCasa:
                if(!modoSeleccionado.equals(Constantes.MODO_CASA)){
                    item.setIcon(R.drawable.compu_on);
                    Intent intent = new Intent(this,ActivityPrincipal.class);
                    intent.putExtra("modo",Constantes.MODO_CASA);
                    startActivity(intent);
                    this.finish();
                }
                break;
            case R.id.buttonCine:
                if(!modoSeleccionado.equals(Constantes.MODO_CINE)){
                    item.setIcon(R.drawable.cinema_on);
                    Intent intent = new Intent(this,ActivityPrincipal.class);
                    intent.putExtra("modo",Constantes.MODO_CINE);
                    startActivity(intent);
                    this.finish();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //CARGAMOS FRAGMENTS AL VIEWPAGER DEL TABLAYOUT, CON LOS ÍTEMS DE LAS PESTAÑAS
    private void cargarFragment(String modoSeleccionado, FragmentTransaction fragmentTransaction) {
            switch (modoSeleccionado) {
                case Constantes.MODO_CASA:
                    tabLayout.setupWithViewPager(viewPager);
                    viewPager.setAdapter(new ViewPagerPrincipalAdapterCasa(fragmentManager));
                    tabLayout.getTabAt(0).setText("Peliculas");
                    tabLayout.getTabAt(1).setText("Series");
                    break;
                case Constantes.MODO_CINE:
                    tabLayout.setupWithViewPager(viewPager);
                    viewPager.setAdapter(new ViewPagerPrincipalAdapterCine(fragmentManager));
                    tabLayout.getTabAt(0).setText("En cartelera");
                    tabLayout.getTabAt(1).setText("Proximos estrenos");
                    break;
                default:
                    break;
            }
        }

    private void setearEscuchadoresBottomBar(){

        buttonMenu.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                  drawerLayout.closeDrawer(GravityCompat.START);
                  } else {
                      drawerLayout.openDrawer(GravityCompat.START);
                  }
              }
        });
        buttonBuscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPrincipal.this, ActivityBuscador.class);
                startActivity(intent);
            }
        });
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(estasLogeado()){
                    Intent intent = new Intent(ActivityPrincipal.this,ActivityChat.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Para usar esta función tenés que iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageViewDado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(estasLogeado()){
                    Intent intent = new Intent(ActivityPrincipal.this,ActivityRandom.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Para usar esta función tenés que iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(estasLogeado()){
                    Intent intent = new Intent(ActivityPrincipal.this,ActivityPerfil.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ActivityPrincipal.this,ActivityLogin.class);
                    startActivity(intent);
                }
            }
        });
    }

    //EVENTOS AL CLICKEAR ALGUNA PELÍCULA O SERIE.
    //SE ENVÍA A VER EL DETALLE.
    @Override
    public void seleccionaronRanking(Integer posicion, String tipoLista) {
        Intent intent = new Intent(ActivityPrincipal.this,ActivityDetalle.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constantes.POSICION_LISTA,posicion);
        bundle.putString(Constantes.TIPO_DE_LISTA,tipoLista);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void seleccionaronAudiovisual(Integer posicion, String tituloDeLaLista) {
        Intent intent = new Intent(ActivityPrincipal.this,ActivityDetalle.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constantes.POSICION_LISTA,posicion);
        bundle.putString(Constantes.TIPO_DE_LISTA,tituloDeLaLista);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void peliculaSeleccionadaFragment(Integer posicion, String tipoDeLista) {
        Intent intent = new Intent(ActivityPrincipal.this,ActivityDetalle.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constantes.POSICION_LISTA,posicion);
        bundle.putString(Constantes.TIPO_DE_LISTA,tipoDeLista);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //NAVIGATIONVIEW CON EL LISTADO AMPLIO DE GÉNEROS
    private void navigationDrawer(){

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent(ActivityPrincipal.this,ActivityListado.class);
                Bundle bundle = new Bundle();
                switch (item.getItemId()){
                    case R.id.generoPeliculaAccion:
                        bundle.putInt(Constantes.GENERO, 28);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaAventura:
                        bundle.putInt(Constantes.GENERO, 12);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaAnimación:
                        bundle.putInt(Constantes.GENERO, 16);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaComedia:
                        bundle.putInt(Constantes.GENERO, 35);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaCrimen:
                        bundle.putInt(Constantes.GENERO, 80);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaDocumental:
                        bundle.putInt(Constantes.GENERO, 99);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaDrama:
                        bundle.putInt(Constantes.GENERO, 18);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaFamilia:
                        bundle.putInt(Constantes.GENERO, 10751);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaFantasía:
                        bundle.putInt(Constantes.GENERO, 14);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaHistoria:
                        bundle.putInt(Constantes.GENERO, 36);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaTerror:
                        bundle.putInt(Constantes.GENERO, 27);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaMusica:
                        bundle.putInt(Constantes.GENERO, 10402);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaMisterio:
                        bundle.putInt(Constantes.GENERO, 9648);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaRomance:
                        bundle.putInt(Constantes.GENERO, 10749);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaCienciaFicción:
                        bundle.putInt(Constantes.GENERO, 878);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaDeLaTelevisión:
                        bundle.putInt(Constantes.GENERO, 10770);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaSuspenso:
                        bundle.putInt(Constantes.GENERO, 53);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaGuerra:
                        bundle.putInt(Constantes.GENERO, 10752);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoPeliculaWestern:
                        bundle.putInt(Constantes.GENERO, 37);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.PELICULAS);
                        break;
                    case R.id.generoSerieAccionAdventure:
                        bundle.putInt(Constantes.GENERO, 10759);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                    case R.id.generoSerieAnimación:
                        bundle.putInt(Constantes.GENERO, 16);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                    case R.id.generoSerieComedia:
                        bundle.putInt(Constantes.GENERO, 35);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                    case R.id.generoSerieCrimen:
                        bundle.putInt(Constantes.GENERO, 80);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                    case R.id.generoSerieDocumental:
                        bundle.putInt(Constantes.GENERO, 10759);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                    case R.id.generoSerieDrama:
                        bundle.putInt(Constantes.GENERO, 18);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                    case R.id.generoSerieFamilia:
                        bundle.putInt(Constantes.GENERO, 10751);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                    case R.id.generoSerieInfantil:
                        bundle.putInt(Constantes.GENERO, 10762);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                    case R.id.generoSerieMisterio:
                        bundle.putInt(Constantes.GENERO, 9648);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                    case R.id.generoSerieNews:
                        bundle.putInt(Constantes.GENERO, 10763);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                    case R.id.generoSerieReality:
                        bundle.putInt(Constantes.GENERO, 10764);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                    case R.id.generoSerieSci_FiYFantasy:
                        bundle.putInt(Constantes.GENERO, 10765);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                    case R.id.generoSerieGerraYpelitica:
                        bundle.putInt(Constantes.GENERO, 10768);
                        bundle.putString(Constantes.MODO_INTERNO,Constantes.SERIES);
                        break;
                }
                drawerLayout.closeDrawers();
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }
        });
    }

    private boolean estasLogeado() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        return (user != null);
    }
}
