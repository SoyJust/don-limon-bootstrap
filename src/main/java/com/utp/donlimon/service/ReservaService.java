package com.utp.donlimon.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.utp.donlimon.model.Reserva;
import com.utp.donlimon.repository.ReservaRepository;

@Service
public class ReservaService {

    private final ReservaRepository repository;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> listar() {
        return repository.findAll();
    }

    public Reserva buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No existe reserva con id " + id));
    }

    public Reserva guardar(Reserva reserva) {
        return repository.save(reserva);
    }

    public void eliminar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
