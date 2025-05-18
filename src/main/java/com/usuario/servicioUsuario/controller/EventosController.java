package com.evento.servicioEvento.controller;

import com.evento.servicioEvento.model.Evento;
import com.evento.servicioEvento.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<Evento> crearEvento(@Valid @RequestBody Evento evento) {
        Evento nuevoEvento = eventoService.crearEvento(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEvento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerEvento(@PathVariable Long id) {
        Evento evento = eventoService.obtenerPorId(id);
        return ResponseEntity.ok(evento);
    }

    @GetMapping
    public ResponseEntity<List<Evento>> listarEventosProximos() {
        List<Evento> eventos = eventoService.listarEventosProximos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/paginados")
    public ResponseEntity<Page<Evento>> listarEventosProximos(Pageable pageable) {
        Page<Evento> eventos = eventoService.listarEventosProximos(pageable);
        return ResponseEntity.ok(eventos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizarEvento(
            @PathVariable Long id,
            @Valid @RequestBody Evento detalles) {
        Evento actualizado = eventoService.actualizarEvento(id, detalles);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/incrementar-asistentes")
    public ResponseEntity<Void> incrementarAsistentes(@PathVariable Long id) {
        eventoService.incrementarAsistentes(id);
        return ResponseEntity.ok().build();
    }

   
}