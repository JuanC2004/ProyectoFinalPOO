package com.jcqh.democlase.Repositorios;

import com.jcqh.democlase.Modelos.Silla;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RepositorioSilla extends MongoRepository<Silla,String> {
    @Query("{'sala.$id': ObjectId(?0)}")
    List<Silla> getSillasEnSala(String idSala);
}
