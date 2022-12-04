/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.LinkedList;
import org.json.simple.JSONObject;

/**
 *
 * @author juanc
 */
public class Pelicula {
    private String _id;
    private String nombre;
    private int ano;
    private String tipo;
    
    private LinkedList <Funcion>misFunciones;
    public Pelicula() {
    }

    public Pelicula(String nombre, int ano, String tipo) {
        this.nombre = nombre;
        this.ano = ano;
        this.tipo = tipo;
        this.misFunciones = new LinkedList<>();
    }
    
    public JSONObject toJson(){
        JSONObject respuesta = new JSONObject();
        respuesta.put("nombre", this.getNombre());
        respuesta.put("ano", this.getAno());
        respuesta.put("Tipo", this.getTipo());
        return respuesta;
    }

    /**
     * @return the _id
     */
    public String getId() {
        return _id;
    }

    /**
     * @param _id the _id to set
     */
    public void setId(String _id) {
        this._id = _id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the ano
     */
    public int getAno() {
        return ano;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the misFunciones
     */
    public LinkedList <Funcion> getMisFunciones() {
        return misFunciones;
    }

    /**
     * @param misFunciones the misFunciones to set
     */
    public void setMisFunciones(LinkedList <Funcion> misFunciones) {
        this.misFunciones = misFunciones;
    }
    
    
    
}
