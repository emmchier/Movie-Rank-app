package com.integrador.grupo2android.proyectointegrador.Vista.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.integrador.grupo2android.proyectointegrador.R;
import com.integrador.grupo2android.proyectointegrador.Util.Constantes;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.buttonIngresoCasa)
    ImageView buttonCasa;

    @BindView(R.id.buttonIngresoCine)
    ImageView buttonCine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        buttonCine.setOnClickListener(this);
        buttonCasa.setOnClickListener(this);

        //SI ES LA PRIMERA VEZ QUE EL USUARIO ABRE LA APP, SE LO ENVÍA AL ONBOARDING
        if (isFirstTimeStartApp()) {
            startActivityOnBoarding();
        }
    }

    private void startActivityOnBoarding() {
        Intent intent = new Intent(ActivityHome.this, ActivityOnboarding.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //ENVIAMOS AL USUARIO AL MODO CORRESPONDIENTE, CASA O CINE, SEGÚN EL BOTÓN QUE PULSE.
            case R.id.buttonIngresoCasa:
                startActivityPrincipal(Constantes.MODO_CASA);
                this.finish();
                break;
            case R.id.buttonIngresoCine:
                startActivityPrincipal(Constantes.MODO_CINE);
                this.finish();
                break;
            default:
                Toast.makeText(this, "Hubo un error. Volvé a intentarlo", Toast.LENGTH_LONG).show();
                break;
        }
    }

    //SHARED PREFERENCES, NOS PERMITE COMPROBAR SI EL USUARIO INGRESA O NO POR PRIMERA VEZ A LA APP
    private boolean isFirstTimeStartApp() {
        SharedPreferences ref = getApplicationContext().getSharedPreferences("IntroSliderApp", Context.MODE_PRIVATE);
        return ref.getBoolean("FirstTimeStartFlag", true);
    }

    //ENVIAMOS A LA ACTIVIDAD PRINCIPAL SEGÚN EL MODO
    private void startActivityPrincipal(String valor) {
        Intent irACasa = new Intent(this, ActivityPrincipal.class);
        Bundle bundleCasa = new Bundle();
        bundleCasa.putString("modo", valor);
        irACasa.putExtras(bundleCasa);
        startActivity(irACasa);
    }
}
