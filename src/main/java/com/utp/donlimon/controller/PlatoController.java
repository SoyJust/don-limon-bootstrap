package com.utp.donlimon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utp.donlimon.model.Plato;
import com.utp.donlimon.service.PlatoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    @GetMapping
    public List<Plato> listar() {
        return platoService.listar();
    }

    @GetMapping("/{id}")
    public Plato buscar(@PathVariable Integer id) {
        return platoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Plato> guardar(@Valid @RequestBody Plato plato) {
        plato.setIdPlato(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(platoService.guardar(plato));
    }

    @PutMapping("/{id}")
    public Plato actualizar(@PathVariable Integer id, @Valid @RequestBody Plato plato) {
        Plato p = platoService.buscarPorId(id);
        p.setNombre(plato.getNombre());
        p.setDescripcion(plato.getDescripcion());
        p.setPrecio(plato.getPrecio());
        p.setImagen(plato.getImagen());
        p.setCategoria(plato.getCategoria());
        return platoService.guardar(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        platoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
