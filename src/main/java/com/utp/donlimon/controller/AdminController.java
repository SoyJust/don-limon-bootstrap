package com.utp.donlimon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.utp.donlimon.model.Categoria;
import com.utp.donlimon.model.Plato;
import com.utp.donlimon.service.CategoriaService;
import com.utp.donlimon.service.PlatoService;

import jakarta.validation.Valid;

/** Página web para administrar la carta; reutiliza los mismos servicios que la API REST. */
@Controller
@RequestMapping("/admin/carta")
public class AdminController {

    private final PlatoService platoService;
    private final CategoriaService categoriaService;

    public AdminController(PlatoService platoService, CategoriaService categoriaService) {
        this.platoService = platoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) Integer editar,
            @RequestParam(defaultValue = "false") boolean ok, Model model) {
        Plato plato;
        if (editar != null) {
            plato = platoService.buscarPorId(editar);
        } else {
            plato = new Plato();
            plato.setCategoria(new Categoria());
        }
        cargarModelo(model, plato);
        model.addAttribute("ok", ok);
        return "admin-carta";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("plato") Plato plato, BindingResult resultado, Model model) {
        if (plato.getCategoria() == null) {
            plato.setCategoria(new Categoria());
        }
        if (plato.getCategoria().getIdCategoria() == null) {
            resultado.rejectValue("categoria.idCategoria", "categoria.requerida",
                    "La categoría del plato es obligatoria");
        }
        if (resultado.hasErrors()) {
            cargarModelo(model, plato);
            model.addAttribute("ok", false);
            return "admin-carta";
        }
        if (plato.getIdPlato() != null) {
            Plato existente = platoService.buscarPorId(plato.getIdPlato());
            existente.setNombre(plato.getNombre());
            existente.setDescripcion(plato.getDescripcion());
            existente.setPrecio(plato.getPrecio());
            existente.setCategoria(plato.getCategoria());
            platoService.guardar(existente);
        } else {
            platoService.guardar(plato);
        }
        return "redirect:/admin/carta?ok=true";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        platoService.eliminar(id);
        return "redirect:/admin/carta?ok=true";
    }

    private void cargarModelo(Model model, Plato plato) {
        model.addAttribute("plato", plato);
        model.addAttribute("platos", platoService.listar());
        model.addAttribute("categorias", categoriaService.listar());
    }
}
