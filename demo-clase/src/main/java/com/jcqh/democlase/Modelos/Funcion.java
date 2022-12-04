package com.jcqh.democlase.Modelos;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
public class Funcion {
    @Id
    String _id;
    int hora;
    int dia;
    int mes;
    int ano;
    @DBRef
    Sala sala;
    @DBRef
    Pelicula pelicula;

    public Funcion() {
    }

    public Funcion(int hora, int dia, int mes, int ano) {
        this.hora = hora;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }




    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String get_id() {
        return _id;
    }
}
