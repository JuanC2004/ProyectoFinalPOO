/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.Funcion;
import Modelos.Pelicula;
import Modelos.Sala;
import Servicios.Servicio;
import java.util.LinkedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author juanc
 */
public class ControladorFuncion {
    Servicio miServicio;
    String subUrl;
    ControladorSala miControladorSala;
    ControladorPelicula miControladorPelicula;

    public ControladorFuncion(String server, String subUrl) {
        this.miServicio = new Servicio(server);
        this.subUrl = subUrl;
        this.miControladorSala = new ControladorSala(server, "salas");
        this.miControladorPelicula = new ControladorPelicula(server, "/peliculas");
    }


   
    
    public Funcion procesarJson(String jsonString) {
        Funcion nuevaFuncion = new Funcion();
        try {
            JSONParser parser = new JSONParser();
            JSONObject funcionJson = (JSONObject) parser.parse(jsonString);
            nuevaFuncion= armar(funcionJson);
            
        } catch (Exception e) {
            System.out.println(e);
            nuevaFuncion = null;
        }
        return nuevaFuncion;
    }


    public LinkedList<Funcion> listar() {
        LinkedList<Funcion> respuesta = new LinkedList<>();
        try {
            String endPoint = this.subUrl;
            String resultado = this.miServicio.GET(endPoint);
            JSONParser parser = new JSONParser();
            JSONArray funcionesJSON = (JSONArray) parser.parse(resultado);
            for (Object actual : funcionesJSON) {
                JSONObject funcionJSON= (JSONObject) actual;
                Funcion nuevaFuncion=new Funcion();
                Sala salaFuncion= nuevaFuncion.getMiSala();
                Pelicula peliculaFuncion=nuevaFuncion.getMiPelicula();
                nuevaFuncion=armar(funcionJSON);
                respuesta.add(nuevaFuncion);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            respuesta = null;
        }
        return respuesta;
    }
    public Funcion armar(JSONObject funcionJson){
        Funcion nuevaFuncion = new Funcion();
        try {
            nuevaFuncion.setId((String)funcionJson.get("_id"));
            nuevaFuncion.setHora(((Long)funcionJson.get("hora")).intValue());
            nuevaFuncion.setDia(((Long)funcionJson.get("dia")).intValue());
            nuevaFuncion.setAno(((Long)funcionJson.get("ano")).intValue());
            nuevaFuncion.setMes(((Long)funcionJson.get("mes")).intValue());
            JSONObject salaJson=(JSONObject)funcionJson.get("sala");
            Sala laSala=this.miControladorSala.armar(salaJson);
            System.out.println("el nombre sala "+ laSala.getNombre());
            nuevaFuncion.setMiSala(laSala);
            JSONObject peliculaJson=(JSONObject)funcionJson.get("pelicula");
            Pelicula laPelicula=this.miControladorPelicula.armar(peliculaJson);
            System.out.println("el nombre pelicula "+ laPelicula.getNombre());
            nuevaFuncion.setMiPelicula(laPelicula);
           
        } catch (Exception e) {
            System.out.println("Error al crear la funcion "+ e);
            nuevaFuncion = null;
        }
        return nuevaFuncion;
    }
    
    public Funcion crear (Funcion nuevaFuncion,Sala miSala,Pelicula miPelicula){
        Funcion respuesta = new Funcion();
        try {
            String resultado = this.miServicio.POST(this.subUrl, nuevaFuncion.toJson());
            respuesta = procesarJson(resultado);
            if(respuesta.getMiPelicula()== null ){
                respuesta.setMiPelicula(miPelicula);
            }
            if(respuesta.getMiSala()== null ){
            respuesta.setMiSala(miSala);    
            }
            
        } catch (Exception e) {
            System.out.println("ERROR en la funcion crear especificamente "+e);
            respuesta=null;
        }
        return respuesta;
    }
    
    
}
