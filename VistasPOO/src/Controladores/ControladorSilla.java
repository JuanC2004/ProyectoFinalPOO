/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.Silla;
import Servicios.Servicio;
import java.util.LinkedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author juanc
 */
public class ControladorSilla {
    Servicio miServicio;
    String subUrl;
    ControladorBoleto miControladorBoleto;

    public ControladorSilla( String server, String subUrl) {
        this.miServicio = new Servicio(server);
        this.subUrl = subUrl;

    }
    

    
        public Silla armar(JSONObject SillaJson){
        Silla nuevoSilla = new Silla();
        try {
            nuevoSilla.setId((String)SillaJson.get("_id"));
            nuevoSilla.setLetra((String)SillaJson.get("letra"));
            nuevoSilla.setNumero(((Long)SillaJson.get("numero")).intValue());
        } catch (Exception e) {
            System.out.println("ERROR al armar la silla "+e);
            nuevoSilla = null;
        }
        return nuevoSilla;
    }
    
    public Silla procesarJson(String jsonString) {
        Silla nuevoSilla = new Silla();
        try {
            JSONParser parser = new JSONParser();
            JSONObject SillaJSON = (JSONObject) parser.parse(jsonString);
            nuevoSilla=armar(SillaJSON);
        } catch (Exception e) {
            System.out.println("ERROR "+e);
            nuevoSilla = null;
        }
        return nuevoSilla;
    }
    
    
    public Silla crear (Silla nuevoSilla){
        Silla respuesta = new Silla();
        try {
            String resultado = this.miServicio.POST(this.subUrl, nuevoSilla.toJson());
            System.out.println("resultadoooo"+ resultado);
            respuesta = procesarJson(resultado);
        } catch (Exception e) {
            System.out.println("ERROR "+e);
            respuesta=null;
        }
        return respuesta;
    }
    

    
    public Silla actualizar(Silla actualizado){
        Silla respuesta=new Silla();
        try {
            String endPoint=this.subUrl+"/"+actualizado.getId();
            String resultado = this.miServicio.PUT(endPoint,actualizado.toJson());
            respuesta = procesarJson(resultado);
        } catch (Exception e) {
            System.out.println("Error " + e);
            respuesta = null;
        }
        return respuesta;
    }
    
    public void eliminar(String id) {
        String endPoint = this.subUrl + "/" + id;
        this.miServicio.DELETE(endPoint);
    }
    
    public LinkedList<Silla> listar() {
        LinkedList<Silla> respuesta = new LinkedList<>();
        try {
            String endPoint = this.subUrl;
            String resultado = this.miServicio.GET(endPoint);
            JSONParser parser = new JSONParser();
            JSONArray SillasJSON = (JSONArray) parser.parse(resultado);
            for (Object actual : SillasJSON) {
                JSONObject SillaJSON= (JSONObject) actual;
                Silla nuevaSilla=new Silla();
                nuevaSilla=armar(SillaJSON);
                respuesta.add(nuevaSilla);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            respuesta = null;
        }
        return respuesta;
    }
    public LinkedList<Silla> listarPorSala(String idSala) {
        LinkedList<Silla> respuesta = new LinkedList<>();
        try {
            String endPoint = this.subUrl+"/sala/"+idSala;
            String resultado = this.miServicio.GET(endPoint);
            JSONParser parser = new JSONParser();
            JSONArray SillasJSON = (JSONArray) parser.parse(resultado);
            for (Object actual : SillasJSON) {
                JSONObject SillaJSON= (JSONObject) actual;
                Silla nuevaSilla=new Silla();
                nuevaSilla=armar(SillaJSON);
                respuesta.add(nuevaSilla);
            }
        } catch (Exception e) {
            System.out.println("Error al listar por salas las sillas" + e);
            respuesta = null;
        }
        return respuesta;
    }
}
