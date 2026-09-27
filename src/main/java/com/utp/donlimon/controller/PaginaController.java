package com.utp.donlimon.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.utp.donlimon.domain.Plato;
import com.utp.donlimon.domain.Reserva;

@Controller
public class PaginaController {

    private final List<Plato> platos = new ArrayList<>();
    private final List<Reserva> reservas = new ArrayList<>();

    public PaginaController() {
        platos.add(new Plato("Conchas Negras", "Conchas negras al natural con limón y salsa criolla.", 28.00));
        platos.add(new Plato("Leche de Tigre", "Jugo cítrico de ceviche con trozos de pescado, ají y cebolla.", 12.00));
        platos.add(new Plato("Ceviche Mixto", "Mezcla de pescado, mariscos y conchas marinados en limón.", 30.00));
        platos.add(new Plato("Arroz con Mariscos", "Arroz cremoso con mariscos frescos y salsa especial de la casa.", 35.00));
        platos.add(new Plato("Seco de Chabelo", "Plato norteño típico de Piura: carne seca guisada con yuca frita.", 28.00));
        platos.add(new Plato("Parihuela", "Sopa marina espesa con mariscos, pescado fresco y ají panca.", 32.00));
        platos.add(new Plato("Chicha Morada", "Bebida tradicional peruana de maíz morado con frutas y canela.", 8.00));
        platos.add(new Plato("Limonada", "Limonada natural con menta y hierbabuena, preparada al momento.", 7.00));
    }

    @GetMapping("/")
    public String portada(Model model) {
        model.addAttribute("totalPlatos", platos.size());
        return "portada";
    }

    @GetMapping("/carta")
    public String carta(Model model) {
        model.addAttribute("platos", platos);
        return "carta";
    }

    @GetMapping("/reservas")
    public String reservas(@RequestParam(defaultValue = "false") boolean ok, Model model) {
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("reservaConfirmada", ok);
        return "reservas";
    }

    @PostMapping("/reservas/guardar")
    public String guardarReserva(@ModelAttribute Reserva reserva) {
        reservas.add(reserva);
        return "redirect:/reservas?ok=true";
    }
}
