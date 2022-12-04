/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.Pelicula;
import Servicios.Servicio;
import java.util.LinkedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author juanc
 */
public class ControladorPelicula {
    Servicio miServicio;
    String subUrl;

    public ControladorPelicula(String server, String subUrl) {
        this.miServicio = new Servicio(server);
        this.subUrl = subUrl;
    }
    
    public Pelicula armar (JSONObject peliculaJson){
        Pelicula nuevaPelicula = new Pelicula();
        try {
            nuevaPelicula.setId((String)peliculaJson.get("_id"));
            nuevaPelicula.setNombre((String)peliculaJson.get("nombre"));
            nuevaPelicula.setAno(((Long)peliculaJson.get("ano")).intValue());
            nuevaPelicula.setTipo((String)peliculaJson.get("tipo"));
        } catch (Exception e) {
            System.out.println("ERROR "+e);
            nuevaPelicula = null;
        }
        return nuevaPelicula;
    }
    
    public Pelicula crear (Pelicula nuevaPelicula){
        Pelicula respuesta = new Pelicula();
        try {
            String resultado = this.miServicio.POST(this.subUrl, nuevaPelicula.toJson());
            System.out.println("resultadoooo"+ resultado);
            respuesta = procesarJson(resultado);
        } catch (Exception e) {
            System.out.println("ERROR "+e);
            respuesta=null;
        }
        return respuesta;
    }
    
    public Pelicula actualizar(Pelicula actualizado){
        Pelicula respuesta=new Pelicula();
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
    
    public Pelicula procesarJson(String jsonString) {
        Pelicula nuevoEstudiante = new Pelicula();
        try {
            JSONParser parser = new JSONParser();
            JSONObject peliculaJSON = (JSONObject) parser.parse(jsonString);
            nuevoEstudiante=armar(peliculaJSON);
        } catch (Exception e) {
            nuevoEstudiante = null;
        }
        return nuevoEstudiante;
    }
    
    public LinkedList<Pelicula> listar() {
        LinkedList<Pelicula> respuesta = new LinkedList<>();
        try {
            String endPoint = this.subUrl;
            String resultado = this.miServicio.GET(endPoint);
            JSONParser parser = new JSONParser();
            JSONArray peliculasJSON = (JSONArray) parser.parse(resultado);
            for (Object actual : peliculasJSON) {
                JSONObject peliculaJSON= (JSONObject) actual;
                Pelicula nuevaPelicula=new Pelicula();
                nuevaPelicula=armar(peliculaJSON);
                respuesta.add(nuevaPelicula);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            respuesta = null;
        }
        return respuesta;
    }
    
    public Pelicula buscarPorNombre(String nombre) {
        Pelicula respuesta = new Pelicula();
        try {
            String endPoint = this.subUrl + "/nombre/" + nombre;
            String resultado = this.miServicio.GET(endPoint);
            respuesta = procesarJson(resultado);
        } catch (Exception e) {
            System.out.println("Error " + e);
            respuesta = null;
        }
        return respuesta;
    }
}
