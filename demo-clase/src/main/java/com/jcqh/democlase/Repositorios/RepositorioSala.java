package com.jcqh.democlase.Repositorios;

import com.jcqh.democlase.Modelos.Sala;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioSala extends MongoRepository<Sala,String>{

}
