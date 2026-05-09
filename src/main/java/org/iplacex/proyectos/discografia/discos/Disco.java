package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("discos")
public class Disco {

    @Id
    public String _id;

    public String titulo;
    public int anioLanzamiento;
    public String artistaId;
    public List<String> canciones;
}
