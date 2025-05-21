package com.usuario.servicioUsuario.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String nombreUsuario;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Evita que la contraseña se muestre al serializar el objeto
    @Column(nullable = false, length = 50)
    private String contrasena;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Santiago")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Santiago")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @PrePersist //PrePersist sirve para ejecutar código antes de insertar en la base de datos
    protected void onCreate() { 
        this.fechaCreacion = new Date(); // Se setea automáticamente antes de insertar en DB
    }
}