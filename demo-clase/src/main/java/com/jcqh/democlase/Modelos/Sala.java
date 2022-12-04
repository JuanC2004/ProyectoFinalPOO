package com.jcqh.democlase.Modelos;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
public class Sala {
    @Id
    String _id;
    String nombre;
    boolean efectosEspeciales;

    public Sala() {
    }

    public Sala(String nombre, boolean efectosEspeciales) {
        this.nombre = nombre;
        this.efectosEspeciales = efectosEspeciales;
    }

    public String get_id() {
        return _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEfectosEspeciales() {
        return efectosEspeciales;
    }

    public void setEfectosEspeciales(boolean efectosEspeciales) {
        this.efectosEspeciales = efectosEspeciales;
    }
}
