package com.jcqh.democlase.Controladores;


import com.jcqh.democlase.Modelos.Funcion;
import com.jcqh.democlase.Modelos.Pelicula;
import com.jcqh.democlase.Modelos.Sala;
import com.jcqh.democlase.Repositorios.RepositorioFuncion;
import com.jcqh.democlase.Repositorios.RepositorioPelicula;
import com.jcqh.democlase.Repositorios.RepositorioSala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/funciones")
public class ControladorFuncion {
    @Autowired
    private RepositorioFuncion miRepositorioFuncion;
    @Autowired
    private RepositorioSala miRepositorioSala;
    @Autowired
    private RepositorioPelicula miRepositorioPelicula;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sala/{id_sala}/pelicula/{id_pelicula}")
    public Funcion create(@RequestBody Funcion infoFuncion,
                          @PathVariable String id_sala,
                          @PathVariable String id_pelicula){

        Sala salaActual = this.miRepositorioSala
                .findById(id_sala)
                .orElseThrow(RuntimeException::new);
        Pelicula peliculaActual = this.miRepositorioPelicula
                .findById(id_pelicula)
                .orElseThrow(RuntimeException::new);
        infoFuncion.setSala(salaActual);
        infoFuncion.setPelicula(peliculaActual);
        return this.miRepositorioFuncion.save(infoFuncion);
    }

    @GetMapping("")
    public List<Funcion> index(){
        return this.miRepositorioFuncion.findAll();
    }
    @GetMapping("{id}")
    public Funcion show(@PathVariable String id){
        Funcion funcionActual = this.miRepositorioFuncion
                .findById(id)
                .orElseThrow(RuntimeException::new);
        return funcionActual;
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Funcion funcionActual = this.miRepositorioFuncion
                .findById(id)
                .orElseThrow(RuntimeException::new);
        this.miRepositorioFuncion.delete(funcionActual);
    }

    @PutMapping("{id}")
    public Funcion update(@PathVariable String id, @RequestBody Funcion infoFuncion){
        Funcion funcionActual = this.miRepositorioFuncion
                .findById(id)
                .orElseThrow(RuntimeException::new);
        funcionActual.setAno(infoFuncion.getAno());
        funcionActual.setDia(infoFuncion.getDia());
        funcionActual.setHora(infoFuncion.getHora());
        funcionActual.setMes(infoFuncion.getMes());
        return this.miRepositorioFuncion.save(funcionActual);
    }
    @PutMapping("{id}/sala/{id_sala}/pelicula/{id_pelicula}")
    public Funcion update(@PathVariable String id, @RequestBody Funcion infoFuncion, @PathVariable String id_sala, @PathVariable String id_pelicula){
        Funcion funcionActual = this.miRepositorioFuncion
                .findById(id)
                .orElseThrow(RuntimeException::new);
        funcionActual.setAno(infoFuncion.getAno());
        funcionActual.setDia(infoFuncion.getDia());
        funcionActual.setHora(infoFuncion.getHora());
        funcionActual.setMes(infoFuncion.getMes());
        Sala salaActual = this.miRepositorioSala
                .findById(id_sala)
                .orElseThrow(RuntimeException::new);
        Pelicula peliculaActual = this.miRepositorioPelicula
                .findById(id_pelicula)
                .orElseThrow(RuntimeException::new);
        infoFuncion.setSala(salaActual);
        infoFuncion.setPelicula(peliculaActual);

        return this.miRepositorioFuncion.save(funcionActual);
    }
}
