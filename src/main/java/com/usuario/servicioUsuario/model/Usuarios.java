package com.usuario.servicioUsuario.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "usuarios") 
@Data // Genera automáticamente los métodos getters y setters
@AllArgsConstructor // Genera un constructor con todos los atributos
@NoArgsConstructor // Genera un constructor sin parámetros
public class Usuarios {
    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el valor automáticamente
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String nombreUsuario;

    @Column(nullable = false, length = 50)
    private String contrasena;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private Date fechaNacimiento;

    @Column(nullable = false)
    private Date fechaCreacion;
    
}
