package com.jcqh.democlase.Controladores;


import com.jcqh.democlase.Modelos.Pelicula;
import com.jcqh.democlase.Repositorios.RepositorioPelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/peliculas")

public class ControladorPelicula {
    @Autowired
    private RepositorioPelicula miRepositorioPelicula;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Pelicula create(@RequestBody Pelicula infoPelicula){
        return this.miRepositorioPelicula.save(infoPelicula);
    }
    @GetMapping("")
    public List<Pelicula> index(){
        return this.miRepositorioPelicula.findAll();
    }
    @GetMapping("{id}")
    public Pelicula show(@PathVariable String id){
        Pelicula peliculaActual = this.miRepositorioPelicula
                .findById(id)
                .orElseThrow(RuntimeException::new);
        return peliculaActual;
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Pelicula peliculaActual = this.miRepositorioPelicula
                .findById(id)
                .orElseThrow(RuntimeException::new);
        this.miRepositorioPelicula.delete(peliculaActual);
    }
    @PutMapping("{id}")
    public Pelicula update(@PathVariable String id, @RequestBody Pelicula info_pelicula){
        Pelicula peliculaActual = this.miRepositorioPelicula
                .findById(id)
                .orElseThrow(RuntimeException::new);
        peliculaActual.setAno(info_pelicula.getAno());
        peliculaActual.setNombre(info_pelicula.getNombre());
        peliculaActual.setTipo(info_pelicula.getTipo());
        return this.miRepositorioPelicula.save(peliculaActual);
    }

    @GetMapping("/nombre/{Nombre}")
    public Pelicula buscarPorNombre(@PathVariable String nombre){
        Pelicula peliculaActual = this.miRepositorioPelicula.getPelicula(nombre);
        return peliculaActual;
    }
}
