package com.eventos.servicioEventos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "eventos")
public class Eventos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El campo evento es obligatorio")
    private String tituloEvento;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "El campo descripcion es obligatorio")
    @Size(min = 10, max = 500, message = "El campo descripcion debe tener entre 10 y 500 caracteres")
    private String descripcionEvento;

    @Column(nullable = false)
    @Future(message = "La fecha del evento debe ser futura")
    private Date fechaEvento;

    @Column(nullable = false)
    @min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacidad;

    @Column(nullable = false)
    @NotBlank(message = "El ID de ubicacion no puede estar vacio")
    private Integer ubicacionId;

    @Column(nullable = false)
    private Integer asistentesConfirmados = 0;

    @CreationTimestamp
    @Column(updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    private Date fechaModificacion;

}