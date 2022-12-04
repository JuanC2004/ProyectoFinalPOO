package com.jcqh.democlase.Modelos;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()

public class Usuario {
    @Id
    String _id;
    String cedula;
    String nombre;
    String email;
    int anoNacimiento;

    public Usuario() {
    }

    public Usuario(String cedula, String nombre, String email, int anoNacimiento) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.email = email;
        this.anoNacimiento = anoNacimiento;
    }

    public String get_id() {
        return _id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(int anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }
}
