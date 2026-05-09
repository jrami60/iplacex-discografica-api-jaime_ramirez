package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;

    @Autowired
    private IArtistaRepository artistaRepo;

    // -------------------- POST (NUEV0) --------------------
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {

        if (!artistaRepo.existsById(disco.artistaId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no existe");
        }

        Disco saved = discoRepo.insert(disco);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // -------------------- GET (MOSTRAR TODOS) --------------------
    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return ResponseEntity.ok(discoRepo.findAll());
    }

    // -------------------- GET (MOSTRAR POR ID) --------------------
    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable("id") String id) {
        return discoRepo.findById(id)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disco no encontrado"));
    }

    // -------------------- GET (MOSTRAR POR ARTISTA) --------------------
    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String idArtista) {
        return ResponseEntity.ok(discoRepo.findDiscosByArtistaId(idArtista));
    }

    // -------------------- PUT (MODIFICAR) --------------------
    @PutMapping(
        value = "/disco/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePutDiscoRequest(
            @PathVariable("id") String id,
            @RequestBody Disco discoActualizado) {

        return discoRepo.findById(id).map(disco -> {

            disco.titulo = discoActualizado.titulo;
            disco.anioLanzamiento = discoActualizado.anioLanzamiento;
            disco.artistaId = discoActualizado.artistaId;
            disco.canciones = discoActualizado.canciones;

            Disco saved = discoRepo.save(disco);
            return ResponseEntity.ok((Object) saved);

        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body((Object) "Disco no encontrado"));
    }

    // -------------------- DELETE (ELIMINAR) --------------------
    @DeleteMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteDiscoRequest(@PathVariable("id") String id) {

        if (!discoRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disco no encontrado");
        }

        discoRepo.deleteById(id);
        return ResponseEntity.ok("Disco eliminado correctamente");
    }
}
