/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.Usuario;
import Servicios.Servicio;
import java.util.LinkedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author juanc
 */
public class ControladorUsuario {
    Servicio miServicio;
    String subUrl;

    public ControladorUsuario(String server, String subUrl) {
        this.miServicio = new Servicio(server) ;
        this.subUrl = subUrl;
    }
    
    public Usuario armar(JSONObject usuarioJson){
        Usuario nuevoUsuario = new Usuario();
        try {
            nuevoUsuario.setId((String)usuarioJson.get("_id"));
            nuevoUsuario.setCedula((String)usuarioJson.get("cedula"));
            nuevoUsuario.setNombre((String)usuarioJson.get("nombre"));
            nuevoUsuario.setEmail((String)usuarioJson.get("email"));
            nuevoUsuario.setAnoNacimiento(((Long)usuarioJson.get("anoNacimiento")).intValue());
        } catch (Exception e) {
            System.out.println("ERROR "+e);
            nuevoUsuario = null;
        }
        return nuevoUsuario;
    }
    
    public Usuario procesarJson(String jsonString) {
        Usuario nuevoUsuario = new Usuario();
        try {
            JSONParser parser = new JSONParser();
            JSONObject usuarioJSON = (JSONObject) parser.parse(jsonString);
            nuevoUsuario=armar(usuarioJSON);
        } catch (Exception e) {
            System.out.println("ERROR "+e);
            nuevoUsuario = null;
        }
        return nuevoUsuario;
    }
    
    
    public Usuario crear (Usuario nuevoUsuario){
        Usuario respuesta = new Usuario();
        try {
            String resultado = this.miServicio.POST(this.subUrl, nuevoUsuario.toJson());
            System.out.println("resultadoooo"+ resultado);
            respuesta = procesarJson(resultado);
        } catch (Exception e) {
            System.out.println("ERROR "+e);
            respuesta=null;
        }
        return respuesta;
    }
    
    public Usuario buscarPorCedula(String cedula) {
        Usuario respuesta = new Usuario();
        try {
            String endPoint = this.subUrl + "/cedula/" + cedula;
            String resultado = this.miServicio.GET(endPoint);
            respuesta = procesarJson(resultado);
        } catch (Exception e) {
            System.out.println("Error " + e);
            respuesta = null;
        }
        return respuesta;
    }
    
    public Usuario actualizar(Usuario actualizado){
        Usuario respuesta=new Usuario();
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
    
    public LinkedList<Usuario> listar() {
        LinkedList<Usuario> respuesta = new LinkedList<>();
        try {
            String endPoint = this.subUrl;
            String resultado = this.miServicio.GET(endPoint);
            JSONParser parser = new JSONParser();
            JSONArray usuariosJSON = (JSONArray) parser.parse(resultado);
            for (Object actual : usuariosJSON) {
                JSONObject usuarioJSON= (JSONObject) actual;
                Usuario nuevaUsuario=new Usuario();
                nuevaUsuario=armar(usuarioJSON);
                respuesta.add(nuevaUsuario);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            respuesta = null;
        }
        return respuesta;
    }
}
