package com.utp.donlimon.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.utp.donlimon.model.Plato;
import com.utp.donlimon.repository.PlatoRepository;

@Service
public class PlatoService {

    private final PlatoRepository repository;
    private final CategoriaService categoriaService;

    public PlatoService(PlatoRepository repository, CategoriaService categoriaService) {
        this.repository = repository;
        this.categoriaService = categoriaService;
    }

    public List<Plato> listar() {
        return repository.findAll();
    }

    public Plato buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No existe plato con id " + id));
    }

    public Plato guardar(Plato plato) {
        // La categoría debe existir: si el id no está en la BD responde 404
        plato.setCategoria(categoriaService.buscarPorId(plato.getCategoria().getIdCategoria()));
        return repository.save(plato);
    }

    public void eliminar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
