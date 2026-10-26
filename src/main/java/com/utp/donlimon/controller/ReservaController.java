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

import com.utp.donlimon.model.Reserva;
import com.utp.donlimon.service.ReservaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<Reserva> listar() {
        return reservaService.listar();
    }

    @GetMapping("/{id}")
    public Reserva buscar(@PathVariable Integer id) {
        return reservaService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Reserva> guardar(@Valid @RequestBody Reserva reserva) {
        reserva.setIdReserva(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.guardar(reserva));
    }

    @PutMapping("/{id}")
    public Reserva actualizar(@PathVariable Integer id, @Valid @RequestBody Reserva reserva) {
        Reserva r = reservaService.buscarPorId(id);
        r.setNombre(reserva.getNombre());
        r.setEmail(reserva.getEmail());
        r.setFecha(reserva.getFecha());
        r.setComensales(reserva.getComensales());
        return reservaService.guardar(r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
