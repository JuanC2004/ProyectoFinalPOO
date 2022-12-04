package com.jcqh.democlase.Controladores;


import com.jcqh.democlase.Modelos.Usuario;
import com.jcqh.democlase.Repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {

    @Autowired
    private RepositorioUsuario miRepositorioUsuario;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario create(@RequestBody Usuario infoUsuario){
        return this.miRepositorioUsuario.save(infoUsuario);
    }

    @GetMapping("")
    public List<Usuario> index(){
        return this.miRepositorioUsuario.findAll();
    }

    @GetMapping("{id}")
    public Usuario show(@PathVariable String id){
        Usuario usuarioActual = this.miRepositorioUsuario
                .findById(id)
                .orElseThrow(RuntimeException::new);
        return usuarioActual;
    }

    @GetMapping("/cedula/{cedula}")
    public Usuario update(@PathVariable String cedula){
        Usuario usuarioActual = this.miRepositorioUsuario.getUsuario(cedula);
        return usuarioActual;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Usuario usuarioActual = this.miRepositorioUsuario
                .findById(id)
                .orElseThrow(RuntimeException::new);
        this.miRepositorioUsuario.delete(usuarioActual);
    }
    @PutMapping("{id}")
    public Usuario update(@PathVariable String id, @RequestBody Usuario infoUsuario){
        Usuario usuarioActual = this.miRepositorioUsuario
                .findById(id)
                .orElseThrow(RuntimeException::new);
        usuarioActual.setNombre(infoUsuario.getNombre());
        usuarioActual.setCedula(infoUsuario.getCedula());
        usuarioActual.setEmail(infoUsuario.getEmail());
        usuarioActual.setAnoNacimiento(infoUsuario.getAnoNacimiento());

        return this.miRepositorioUsuario.save(usuarioActual);
    }
}
