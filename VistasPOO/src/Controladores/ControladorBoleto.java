/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.Boleto;
import Modelos.Funcion;
import Modelos.Silla;
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
public class ControladorBoleto {
    Servicio miServicio;
    String subUrl;
    ControladorUsuario miControladorUsuario;
    ControladorFuncion miControladorFuncion;
    ControladorSilla miControladorSilla;

    public ControladorBoleto(String server, String subUrl) {
        this.miServicio = new Servicio(server);
        this.subUrl = subUrl;
        this.miControladorUsuario = new ControladorUsuario(server,"/usuarios");
        this.miControladorFuncion = new ControladorFuncion(server, "/funciones");
        this.miControladorSilla = new ControladorSilla(server, "/sillas");
    }
    

    public Boleto procesarJson(String jsonString) {
        Boleto nuevoBoleto = new Boleto();
        try {
            JSONParser parser = new JSONParser();
            JSONObject boletoJSON = (JSONObject) parser.parse(jsonString);
            nuevoBoleto=armar(boletoJSON);
        } catch (Exception e) {
            System.out.println("ERROR "+e);
            nuevoBoleto = null;
        }
        return nuevoBoleto;
    }
    public Boleto armar(JSONObject boletoJson){
        Boleto nuevoBoleto = new Boleto();
        System.out.println("boleto json---->"+ boletoJson);
        try {
            nuevoBoleto.setId((String)boletoJson.get("_id"));
            nuevoBoleto.setTipo((String)boletoJson.get("tipo"));
            nuevoBoleto.setValor((double) boletoJson.get("valor"));
             System.out.println("json usuario ---"+ boletoJson.get("usuario"));
             if(boletoJson.get("usuario")!=null){
                 JSONObject usuarioJson=(JSONObject)boletoJson.get("usuario");
            Usuario elUsuario=this.miControladorUsuario.armar(usuarioJson);
            System.out.println("el nombre sala "+ elUsuario.getNombre());
            nuevoBoleto.setMiUsuario(elUsuario);
            JSONObject sillaJson=(JSONObject)boletoJson.get("silla");
            System.out.println("la silla que necesito llena ----="+sillaJson);
            Silla laSilla=this.miControladorSilla.armar(sillaJson);
            System.out.println("la Letra Silla "+ laSilla.getLetra());
            nuevoBoleto.setMiSilla(laSilla);
            JSONObject funcionJson=(JSONObject)boletoJson.get("funcion");
            Funcion laFuncion=this.miControladorFuncion.armar(funcionJson);
            System.out.println("la f funcion "+ laFuncion.getAno());
            nuevoBoleto.setMiFuncion(laFuncion);
             }

            
        } catch (Exception e) {
            System.out.println("error al armar boleto "+e);
            nuevoBoleto = null;
        }
        return nuevoBoleto;
    }
    
    public Boleto crear (Boleto nuevoBoleto){
        Boleto respuesta = new Boleto();
        try {
            String resultado = this.miServicio.POST(this.subUrl, nuevoBoleto.toJson());
            respuesta = procesarJson(resultado);
        } catch (Exception e) {
            System.out.println("ERROR al crear el boleto "+e);
            respuesta=null;
        }
        respuesta.setMiSilla(nuevoBoleto.getMiSilla());
        respuesta.setMiFuncion(nuevoBoleto.getMiFuncion());
        respuesta.setMiUsuario(nuevoBoleto.getMiUsuario());
        return respuesta;
    }
    
    public Boleto buscarPorId (String id){
        Boleto respuesta = new Boleto();
        try {
            String endPoint = this.subUrl+"/id/"+id;
            String resultado= this.miServicio.GET(endPoint);
            respuesta=procesarJson(resultado);
        } catch (Exception e){
            System.out.println("ERROR "+e);
            respuesta=null;
        }
        return respuesta;
    }
    
    public Boleto actualizar (Boleto nuevoBoleto){
        Boleto respuesta = new Boleto();
        try {
            System.out.println("boleto json verificar 3"+ nuevoBoleto.toJson());
            String endPoint = this.subUrl+"/"+ nuevoBoleto.getId();
            String resultado = this.miServicio.PUT(endPoint, nuevoBoleto.toJson());
            respuesta = procesarJson(resultado);
        } catch (Exception e) {
            System.out.println("ERROR "+e);
            respuesta=null;
        }
        return respuesta;
    }
    public Boleto actualizarRelaciones(Boleto relacionado, String idClase,String clase){
        Boleto respuesta = new Boleto();
        try {
            String endPoint=this.subUrl+"/"+relacionado.getId()+"/"+clase+"/"+idClase;
            String resultado = this.miServicio.PUT(endPoint,relacionado.toJson() );
            respuesta = procesarJson(resultado);
        } catch (Exception e) {
            System.out.println("ERROR aqui actualizarRelaciones"+e);
            respuesta=null;
        }
        return respuesta;
    }
    public LinkedList<Boleto> listar() {
        LinkedList<Boleto> respuesta = new LinkedList<>();
        try {
            String endPoint = this.subUrl;
            String resultado = this.miServicio.GET(endPoint);
            JSONParser parser = new JSONParser();
            JSONArray boletosJSON = (JSONArray) parser.parse(resultado);
            for (Object actual : boletosJSON) {
                JSONObject boletoJSON= (JSONObject) actual;
                Boleto nuevoBoleto=new Boleto();
                nuevoBoleto=armar(boletoJSON);
                respuesta.add(nuevoBoleto);
            }
        } catch (Exception e) {
            System.out.println("Error al listar boletos " + e);
            respuesta = null;
        }
        return respuesta;
    }
    
    public void eliminar (String id){
        String endPoint=this.subUrl+"/"+id;
        this.miServicio.DELETE(endPoint);
    }



}
