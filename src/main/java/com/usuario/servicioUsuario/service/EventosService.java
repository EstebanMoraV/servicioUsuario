package com.eventos.servicioEventos.service;

import com.evento.servicioEvento.exception.BusinessException;
import com.evento.servicioEvento.exception.ResourceNotFoundException;
import com.evento.servicioEvento.model.Evento;
import com.evento.servicioEvento.repository.EventoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EventosService {

    @Autowired
    private EventoRepository eventoRepository;

    @Transactional
    public Evento crearEvento(Evento evento) {
        validarEvento(evento);
        evento.setAsistentesConfirmados(0);
        Evento nuevoEvento = repository.save(evento);
        log.info("Evento creado con ID: {}", nuevoEvento.getId());
        return nuevoEvento;
    }
    @Transactional(readOnly = true)
    public Evento obtenerPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el evento con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Evento> ListarEventosProximos(){
        return repository.findByFechaEventoBetween(Localdatetime.now(), Pageable.unpaged());
    }
    @Transactional(readOnly = true)
    public List<Evento> ListarEventosProximos(Pageable pageable){
        return repository.findByFechaEventoBetween(Localdatetime.now(), pageable());

    }
    @Transactional
    public Evento actualizarEvento(Evento evento) {
        validarEvento(evento);
        Evento actualizadoEvento = repository.save(evento);
        log.info("Evento actualizado con ID: {}", actualizadoEvento.getId());
        return actualizadoEvento;
    }
    @Transactional
    public void eliminarEvento(Long id) {
        Evento evento = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el evento con ID: " + id));
        repository.delete(evento);
        log.info("Evento eliminado con ID: {}", id);
    }
    @Transactional
    public void incrementarAsistentes(Long id) {
        Evento evento = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el evento con ID: " + id));
        evento.setAsistentesConfirmados(evento.getAsistentesConfirmados() + 1);
        repository.save(evento);
        log.info("Asistentes confirmados incrementados con ID: {}", id);

        if (evento.getAsistentesConfirmados() >= evento.getCapacidad()) {
            log.info("Asistentes confirmados superados el numero de capacidad con ID: {}", id);
            eliminarEvento(id);
        }
        log.info("Asistentes confirmados actualizados con ID: {}", id);
    
    }
    
}
