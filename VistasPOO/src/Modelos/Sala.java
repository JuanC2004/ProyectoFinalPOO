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
public class Sala {
    private String _id;
    private String nombre;
    private boolean efectosEspeciales;
    
    private LinkedList <Funcion> misFunciones;
    private LinkedList <Silla> misSillas;
    
    public Sala() {
    }

    public Sala(String nombre, boolean efectosEspeciales) {
        this.nombre = nombre;
        this.efectosEspeciales = efectosEspeciales;
    }
    
    public JSONObject toJson(){
        JSONObject respuesta = new JSONObject();
        respuesta.put("nombre", this.getNombre());
        respuesta.put("efectosEspeciales", this.isEfectosEspeciales());
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
     * @return the efectosEspeciales
     */
    public boolean isEfectosEspeciales() {
        return efectosEspeciales;
    }

    /**
     * @param efectosEspeciales the efectosEspeciales to set
     */
    public void setEfectosEspeciales(boolean efectosEspeciales) {
        this.efectosEspeciales = efectosEspeciales;
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

    /**
     * @return the misSillas
     */
    public LinkedList <Silla> getMisSillas() {
        return misSillas;
    }

    /**
     * @param misSillas the misSillas to set
     */
    public void setMisSillas(LinkedList <Silla> misSillas) {
        this.misSillas = misSillas;
    }
    
    
}
