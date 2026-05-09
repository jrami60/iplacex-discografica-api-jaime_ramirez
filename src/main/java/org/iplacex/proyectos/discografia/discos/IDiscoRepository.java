package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface IDiscoRepository extends MongoRepository<Disco, String> {

    @Query("{ 'artistaId': ?0 }")
    List<Disco> findDiscosByArtistaId(String artistaId);
}
