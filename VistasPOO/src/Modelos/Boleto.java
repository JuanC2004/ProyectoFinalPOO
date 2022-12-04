/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import org.json.simple.JSONObject;

/**
 *
 * @author juanc
 */
public class Boleto {
    private String _id;
    private double valor;
    private String tipo;
    
    private Silla miSilla;
    private Usuario miUsuario;
    private Funcion miFuncion;

    public Boleto() {
    }

    public Boleto(double valor, String tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }
    
    public JSONObject toJson(){
        JSONObject respuesta = new JSONObject();
        respuesta.put("Valor", this.getValor());
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
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
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
     * @return the miSilla
     */
    public Silla getMiSilla() {
        return miSilla;
    }

    /**
     * @param miSilla the miSilla to set
     */
    public void setMiSilla(Silla miSilla) {
        this.miSilla = miSilla;
    }

    /**
     * @return the miUsuario
     */
    public Usuario getMiUsuario() {
        return miUsuario;
    }

    /**
     * @param miUsuario the miUsuario to set
     */
    public void setMiUsuario(Usuario miUsuario) {
        this.miUsuario = miUsuario;
    }

    /**
     * @return the miFuncion
     */
    public Funcion getMiFuncion() {
        return miFuncion;
    }

    /**
     * @param miFuncion the miFuncion to set
     */
    public void setMiFuncion(Funcion miFuncion) {
        this.miFuncion = miFuncion;
    }
    
    
    
}
