package org.iplacex.proyectos.discografia.artistas;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepo;

    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista saved = artistaRepo.insert(artista);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
        return ResponseEntity.ok(artistaRepo.findAll());
    }

    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable("id") String id) {
        return artistaRepo.findById(id)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no encontrado"));
    }

    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateArtistaRequest(
            @PathVariable("id") String id,
            @RequestBody Artista artista) {

        if (!artistaRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no existe");
        }

        artista._id = id;
        Artista updated = artistaRepo.save(artista);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable("id") String id) {

        if (!artistaRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artista no existe");
        }

        artistaRepo.deleteById(id);
        return ResponseEntity.ok("Artista eliminado");
    }
}


