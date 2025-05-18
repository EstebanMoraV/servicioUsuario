package com.eventos.servicioEventos.repository;

import com.eventos.servicioEventos.model.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.Date;

@Repository
public interface EventosRepository extends JpaRepository<Eventos, Integer> {

    @Query("SELECT e FROM Eventos e WHERE e.fechaEvento >= ?1 AND e.fechaEvento <= ?2 ORDER BY e.fechaEvento ASC")
    Page<Eventos> findByFechaEventoBetween(Date fechaInicio, Date fechaFin, Pageable pageable);

    @Query("SELECT e FROM Eventos e WHERE e.fechaEvento >= ?1 AND e.fechaEvento <= ?2 AND e.ubicacionId = ?3 ORDER BY e.fechaEvento ASC")
    Page<Eventos> findByFechaEventoBetweenAndUbicacionId(Date fechaInicio, Date fechaFin, Integer ubicacionId, Pageable pageable);

    @Query("SELECT e FROM Eventos e WHERE e.fechaEvento >= ?1 AND e.fechaEvento <= ?2 AND e.ubicacionId = ?3 AND e.asistentesConfirmados >= ?4 ORDER BY e.fechaEvento ASC")
    Page<Eventos> findByFechaEventoBetweenAndUbicacionIdAndAsistentesConfirmados(Date fechaInicio, Date fechaFin, Integer ubicacionId, Integer asistentesConfirmados, Pageable pageable);
    
    Optional<Eventos> findById(Integer id);

}