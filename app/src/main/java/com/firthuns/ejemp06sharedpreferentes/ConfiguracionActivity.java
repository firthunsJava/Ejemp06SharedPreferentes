package com.firthuns.ejemp06sharedpreferentes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firthuns.ejemp06sharedpreferentes.configuraciones.Configuraciones;
import com.firthuns.ejemp06sharedpreferentes.modelos.citaMedico;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

public class ConfiguracionActivity extends AppCompatActivity {

    private TextView txtNombre, txtDireccion;
    private CalendarView fechaNacimiento;
    private Switch casado;
    private RadioGroup radioGroupSexo;
    private RadioButton rbHombre, rbMujer;
    private FloatingActionButton fabGuardar;
// declaramos SharedPreferences
    private  SharedPreferences sharedPreferences;
    private String sexo; // creamos un atributo para controlar  el radioGroup
    private  Long fecha; // atributo para controlar el valor tomado del calendar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
// Inicializamos valores
        txtNombre = findViewById(R.id.txtNombreConfig);
        txtDireccion = findViewById(R.id.txtDireccionConfig);
        fechaNacimiento = findViewById(R.id.cFechaNacimientoConfig);
        radioGroupSexo = findViewById(R.id.rbGroupConfig);
        rbHombre = findViewById(R.id.rbHombreConfig);
        rbMujer = findViewById(R.id.rbMujerConfig);
        casado= findViewById(R.id.swCasadoConfig);
        fabGuardar = findViewById(R.id.floatingActionButton);

// inicializamos los SharedPreferences
         sharedPreferences = getSharedPreferences(Configuraciones.SP_PERFIL, MODE_PRIVATE);

        /**
         * Recuperar un objeto
         */
        String citacodificada = sharedPreferences.getString("CITA", null);
        String listaCItasCOdificada = sharedPreferences.getString("LISTACITAS",null);
        citaMedico citaMedico;
        if ( citacodificada != null){
            Gson codificador = new Gson();
            citaMedico = codificador.fromJson(citacodificada, new TypeToken<citaMedico>(){}.getType()  );
            ArrayList<citaMedico> listaCitas    = codificador.fromJson(listaCItasCOdificada, new TypeToken<ArrayList<citaMedico>>(){}.getType()  );
            Toast.makeText(this,"longitud datos guardados: " + listaCitas.size(), Toast.LENGTH_SHORT).show();
        }

        // Operador ternario para controlar el valor del sexo, dentro del radioGroup
      //  sexo = rbHombre.isChecked() ? "HOMBRE" : "MUJER";

        // setOnCheckedChangeListener -> funcion que salta cuando se clickea sobre
        // algunos de los radiobutton seleccionado
         radioGroupSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup group, int checkedId) {
//                 RadioButton rb = findViewById(checkedId);
//                 sexo = rb.getText().toString();

//                 switch (checkedId){
//                     case rbHombre.getId():
//                         sexo = "HOMBRE";
//                         break;
//
//                     case rbMujer.getId():
//                         sexo = "MUJER";
//
//                 }
                 switch (checkedId){
                     case R.id.rbHombreConfig:
                         sexo = "HOMBRE";
                         break;

                     case R.id.rbMujerConfig:
                         sexo = "MUJER";

                 }
             }
         });
//  calendar
         fechaNacimiento.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
             @Override
             public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                 // project structure hemos cambiado el sdk de 21 a 26, ya que getLong requiere una sdk26
                 // Project Structure> Modules,> Default Config> Min sdk Version = 26
                 LocalDate localDate = LocalDate.of(year,month,dayOfMonth);
                 fecha = localDate.getLong(ChronoField.EPOCH_DAY);
             }
         });


//SharedPreferents la accion de escribir tiene que recaer sobre el boton guardar
        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nombre de las variables que vamos a guardar, tomaran el mismo nombre
                //  que hemos utilizando en la clase Configuraciones
                SharedPreferences.Editor editor= sharedPreferences.edit();

                editor.putString("nombre", txtNombre.getText().toString());
                editor.putString("direccion", txtDireccion.getText().toString());
                editor.putBoolean("casado", casado.isChecked());
                editor.putString("sexo",sexo);
                editor.putLong("fechaNacimiento",fecha  );
                editor.apply(); // para confirmar el guardado de las anteriores variables
                finish();

            }
        });





    }
}