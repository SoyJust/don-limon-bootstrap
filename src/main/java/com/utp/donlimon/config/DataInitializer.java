package com.utp.donlimon.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.utp.donlimon.model.Categoria;
import com.utp.donlimon.model.Plato;
import com.utp.donlimon.repository.CategoriaRepository;
import com.utp.donlimon.repository.PlatoRepository;

/** Carga la carta inicial solo si la base de datos está vacía. */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner cargarCarta(CategoriaRepository categorias, PlatoRepository platos) {
        return args -> {
            if (categorias.count() > 0) {
                return;
            }
            Categoria entradas = nuevaCategoria(categorias, "Entradas");
            Categoria fondos = nuevaCategoria(categorias, "Platos de fondo");
            Categoria sopas = nuevaCategoria(categorias, "Sopas");
            Categoria bebidas = nuevaCategoria(categorias, "Bebidas");

            nuevoPlato(platos, "Conchas Negras", "Conchas negras al natural con limón y salsa criolla.", "28.00", entradas);
            nuevoPlato(platos, "Leche de Tigre", "Jugo cítrico de ceviche con trozos de pescado, ají y cebolla.", "12.00", entradas);
            nuevoPlato(platos, "Ceviche Mixto", "Mezcla de pescado, mariscos y conchas marinados en limón.", "30.00", fondos);
            nuevoPlato(platos, "Arroz con Mariscos", "Arroz cremoso con mariscos frescos y salsa especial de la casa.", "35.00", fondos);
            nuevoPlato(platos, "Seco de Chabelo", "Plato norteño típico de Piura: carne seca guisada con yuca frita.", "28.00", fondos);
            nuevoPlato(platos, "Parihuela", "Sopa marina espesa con mariscos, pescado fresco y ají panca.", "32.00", sopas);
            nuevoPlato(platos, "Chicha Morada", "Bebida tradicional peruana de maíz morado con frutas y canela.", "8.00", bebidas);
            nuevoPlato(platos, "Limonada", "Limonada natural con menta y hierbabuena, preparada al momento.", "7.00", bebidas);
        };
    }

    private Categoria nuevaCategoria(CategoriaRepository repo, String nombre) {
        Categoria c = new Categoria();
        c.setNombre(nombre);
        return repo.save(c);
    }

    private void nuevoPlato(PlatoRepository repo, String nombre, String descripcion, String precio, Categoria categoria) {
        Plato p = new Plato();
        p.setNombre(nombre);
        p.setDescripcion(descripcion);
        p.setPrecio(new BigDecimal(precio));
        p.setCategoria(categoria);
        repo.save(p);
    }
}
