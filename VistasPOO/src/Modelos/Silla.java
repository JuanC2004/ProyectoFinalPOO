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
public class Silla {
    private String _id;
    private String letra;
    private int numero;
    private Boleto miBoleto;

    public Silla() {
    }

    public Silla(String letra, int numero) {
        this.letra = letra;
        this.numero = numero;
    }
    
    public JSONObject toJson(){
        JSONObject respuesta = new JSONObject();
        respuesta.put("letra", this.getLetra());
        respuesta.put("numero", this.getNumero());
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
     * @return the letra
     */
    public String getLetra() {
        return letra;
    }

    /**
     * @param letra the letra to set
     */
    public void setLetra(String letra) {
        this.letra = letra;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the miBoleto
     */
    public Boleto getMiBoleto() {
        return miBoleto;
    }

    /**
     * @param miBoleto the miBoleto to set
     */
    public void setMiBoleto(Boleto miBoleto) {
        this.miBoleto = miBoleto;
    }
    
    
}
