package com.jcqh.democlase.Repositorios;

import com.jcqh.democlase.Modelos.Pelicula;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RepositorioPelicula extends MongoRepository<Pelicula,String>{
    @Query("{'nombre':?0}")
    public Pelicula getPelicula(String nombre);
}
