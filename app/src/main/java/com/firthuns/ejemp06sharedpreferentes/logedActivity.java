package com.firthuns.ejemp06sharedpreferentes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.firthuns.ejemp06sharedpreferentes.configuraciones.Configuraciones;
import com.firthuns.ejemp06sharedpreferentes.modelos.citaMedico;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class logedActivity extends AppCompatActivity {

    private Button btnLogOut;
    private ImageButton btnConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged);

// Inicializamos los componentes.
        btnLogOut = findViewById(R.id.btnLoggout);
        btnConfig = findViewById(R.id.btnConfigLoged);

        // limpiamos las preferencias tando de login como de perfil
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(Configuraciones.SP_LOGIN, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                SharedPreferences spPerfil = getSharedPreferences(Configuraciones.SP_PERFIL,MODE_PRIVATE);
                editor = spPerfil.edit();
                editor.clear();
                editor.apply();
                finish();
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(logedActivity.this, ConfiguracionActivity.class));
            }
        });

        /*   Guardar Objetos en SharedPreferents  */
        /* Project struture > Dependencies >agregamos una dependencias que me permita gestionar el conjunto de informacion
        * > Declared Dependencies > Libraries dependencies > .com.google.code.gson
        * Y esta libreria me coge un objeto y me lo convierte en un archivo json
        * */

        citaMedico citaMedico = new citaMedico("fERNANDO", "jIMENEZ", "4646046460",new Date());
        Gson codificador = new Gson();
        String citaCodificada = codificador.toJson(citaMedico);

        SharedPreferences sp = getSharedPreferences(Configuraciones.SP_PERFIL,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("CITA", citaCodificada);
        ArrayList<citaMedico> listaCistas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            listaCistas.add( new citaMedico("fer", "sanz", "00000"+i ,new Date()));
        }
        editor.putString("LISTACITAS", codificador.toJson(listaCistas));
        /*despues del anterior instruccion voy a tener un string en formato .json*/
        editor.apply();

    } // FIN onCreate
// onResume()-> se ejecuta despues del onCreate(), y su vuelve para atras, y vuelvas a esta actividad ,y salte otra vez
    // onResume(), obligare al usuario guarde como minimo el nombre, una vez guardado y generado el archivo .xml,
    // ya si el usuario pica volver atras , le dejaria hacerlo, por que ya tenemos picado un usuario.
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(Configuraciones.SP_PERFIL,MODE_PRIVATE);
        if ( sharedPreferences.getString("nombre", null) == null){
            startActivity( new Intent(logedActivity.this, ConfiguracionActivity.class));
        }

    }
}