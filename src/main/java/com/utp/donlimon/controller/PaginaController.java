package com.utp.donlimon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.utp.donlimon.model.Reserva;
import com.utp.donlimon.service.PlatoService;
import com.utp.donlimon.service.ReservaService;

import jakarta.validation.Valid;

@Controller
public class PaginaController {

    private final PlatoService platoService;
    private final ReservaService reservaService;

    public PaginaController(PlatoService platoService, ReservaService reservaService) {
        this.platoService = platoService;
        this.reservaService = reservaService;
    }

    @GetMapping("/")
    public String portada(Model model) {
        model.addAttribute("totalPlatos", platoService.listar().size());
        return "portada";
    }

    @GetMapping("/carta")
    public String carta(Model model) {
        model.addAttribute("platos", platoService.listar());
        return "carta";
    }

    @GetMapping("/reservas")
    public String reservas(@RequestParam(defaultValue = "false") boolean ok, Model model) {
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("reservaConfirmada", ok);
        return "reservas";
    }

    @PostMapping("/reservas/guardar")
    public String guardarReserva(@Valid @ModelAttribute("reserva") Reserva reserva,
            BindingResult resultado, Model model) {
        if (resultado.hasErrors()) {
            model.addAttribute("reservaConfirmada", false);
            return "reservas";
        }
        reservaService.guardar(reserva);
        return "redirect:/reservas?ok=true";
    }
}
