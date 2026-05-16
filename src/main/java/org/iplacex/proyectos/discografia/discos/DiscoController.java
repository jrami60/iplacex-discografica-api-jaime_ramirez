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

    // -------------------- POST --------------------
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {

        if (!artistaRepo.existsById(disco.getArtistaId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no existe");
        }

        Disco saved = discoRepo.insert(disco);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // -------------------- GET TODOS --------------------
    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return ResponseEntity.ok(discoRepo.findAll());
    }

    // -------------------- GET POR ID --------------------
    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable("id") String id) {
        return discoRepo.findById(id)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Disco no encontrado")
                );
    }

    // -------------------- GET POR ARTISTA --------------------
    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String idArtista) {
        return ResponseEntity.ok(discoRepo.findDiscosByArtistaId(idArtista));
    }

    // -------------------- PUT --------------------
    @PutMapping(
        value = "/disco/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePutDiscoRequest(
            @PathVariable("id") String id,
            @RequestBody Disco discoActualizado) {

        return discoRepo.findById(id)
                .<ResponseEntity<Object>>map(disco -> {

                    disco.setTitulo(discoActualizado.getTitulo());
                    disco.setAnioLanzamiento(discoActualizado.getAnioLanzamiento());
                    disco.setArtistaId(discoActualizado.getArtistaId());
                    disco.setCanciones(discoActualizado.getCanciones());

                    Disco saved = discoRepo.save(disco);
                    return ResponseEntity.ok((Object) saved);

                })
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Disco no encontrado")
                );
    }

    // -------------------- DELETE --------------------
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
