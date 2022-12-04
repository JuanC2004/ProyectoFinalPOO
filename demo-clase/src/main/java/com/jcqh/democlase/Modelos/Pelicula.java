package com.jcqh.democlase.Modelos;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
public class Pelicula {
    @Id
    String _id;
    String nombre;
    int ano;
    String tipo;

    public Pelicula() {
    }

    public Pelicula(String nombre, int ano, String tipo) {
        this.nombre = nombre;
        this.ano = ano;
        this.tipo = tipo;
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

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
