package com.firthuns.ejemp06sharedpreferentes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.firthuns.ejemp06sharedpreferentes.configuraciones.Configuraciones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText txtUserName, txtPassword;
    private Button btnLogin;
    //spConfig -> para la informacion gestionada en ConfiguracionActivity.java
    private  SharedPreferences sharedPreferences , spConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inicializamos datos, habilitando la unicion entre mi archivo xml con la actividad java.class
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnGuardar);

//         1º creamos una variable  SharedPreferents,
//         CON EL CODIGO OPTIMIZADO, CUANDO EL PROGRAMA ARRANCA LO PRIMERO QUE HARIA SERIA LEER
//         EL ARCHIVO PARA obtener el 'userName' y 'password'
//         getSharedPreferences(ficheroNombre, modo privado)
//         mejor abrirlas en modo privado para que nadie pueda accedar a ellas
         sharedPreferences = getSharedPreferences(Configuraciones.SP_LOGIN, MODE_PRIVATE);
        // cuando el username/password-> no exista me devuelva null
        String username = sharedPreferences.getString("userName", null);
        String password = sharedPreferences.getString("password", null);
// esta funcion se ejecutara cuando una vez hayamos hecho el primer login, y cerramos la aplicacion
// de forma voluntaria(sin hacer Logout) o involunariamente, y trsa iniciar la aplicacion
// si detecta la contraseña y usuario se saltará la accion de logear. ALMACENAMIENTO PERSISTENTE ->.XML
        if (username !=null && password != null) {
            //Si ya has hecho LOGIN Es porque  el perfil se encuentra guardado
            spConfig = getSharedPreferences( Configuraciones.SP_PERFIL, MODE_PRIVATE);
            Configuraciones.nombre = spConfig.getString("nombre", null);
            Configuraciones.direccion = spConfig.getString("direccion", null);
            Configuraciones.casado = spConfig.getBoolean("casado",false);
            Configuraciones.sexo = spConfig.getString("sexo", null);
            Configuraciones.fechaNacimiento = new Date(spConfig.getLong("fechaNacimiento", 0));

            startActivity(new Intent(MainActivity.this, logedActivity.class));
        }

        // BOTON QUE NOS PARA PERMITIR EL ACCESO DEL USUARIO A LA APLICACION EN EL CASO DE
        // QUE EL LOGIN SEA CORRECTO.
        /** Login = OK-> Device File Explorer_> data-> data-> nombre de nuestra activida
         *              Veremos nuestra contraseña y usuario en un archivo .xml
         *
         */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUserName.getText().toString().equalsIgnoreCase("Fer")
                 && txtPassword.getText().toString().equalsIgnoreCase("2020") )  {
                 //Almacenar el login en las SharedPrerences

                    //
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userName", txtUserName.getText().toString());
                    editor.putString("password", txtPassword.getText().toString());
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, logedActivity.class));
                    /**  editor.commit()-> el commit fuerza que se haga al instante, parando los demas procesos
                     *   editor.apply()-> hace un commit cuando pueda, es decir, no bloquea el programa y lo hace cuando
                     *                      no detecta movimiento de otros procesos en ejecucion(prioridades)
                     */
                }
            }
        });
    }


}