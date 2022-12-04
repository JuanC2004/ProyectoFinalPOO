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
public class Usuario {
    private String _id;
    private String cedula;
    private String nombre;
    private String email;
    private int anoNacimiento;
   
    private LinkedList <Boleto> misBoletos;

    public Usuario() {
    }

    public Usuario(String cedula, String nombre, String email, int anoNacimiento) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.email = email;
        this.anoNacimiento = anoNacimiento;
    }
    
    public JSONObject toJson(){
        JSONObject respuesta = new JSONObject();
        respuesta.put("cedula", this.getCedula());
        respuesta.put("nombre", this.getNombre());
        respuesta.put("email", this.getEmail());
        respuesta.put("anoNacimiento", this.getAnoNacimiento());
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
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the anoNacimiento
     */
    public int getAnoNacimiento() {
        return anoNacimiento;
    }

    /**
     * @param anoNacimiento the anoNacimiento to set
     */
    public void setAnoNacimiento(int anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    /**
     * @return the misBoletos
     */
    public LinkedList <Boleto> getMisBoletos() {
        return misBoletos;
    }

    /**
     * @param misBoletos the misBoletos to set
     */
    public void setMisBoletos(LinkedList <Boleto> misBoletos) {
        this.misBoletos = misBoletos;
    }
    
    
    
}
