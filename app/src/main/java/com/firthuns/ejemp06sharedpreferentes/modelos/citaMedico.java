package com.firthuns.ejemp06sharedpreferentes.modelos;

import java.time.DateTimeException;
import java.util.Date;

public class citaMedico {

    private String nombre;
    private String apellidos;
    private  String sip;
    private Date FechaCita;


    public citaMedico(String nombre, String apellidos, String sip, Date fechaCita) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sip = sip;
        FechaCita = fechaCita;
    }
// para el plugin de google para converit un conjuntos de datos en un archivo .json
// es necesario crear un constructor vacio
    public citaMedico() {
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public Date getFechaCita() {
        return FechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        FechaCita = fechaCita;
    }
}
