package com.jcqh.democlase.Repositorios;

import com.jcqh.democlase.Modelos.Funcion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioFuncion extends MongoRepository<Funcion,String>{
}
