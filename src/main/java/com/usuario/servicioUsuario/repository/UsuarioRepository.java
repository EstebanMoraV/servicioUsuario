package com.usuario.servicioUsuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; // Importa la interfaz JpaRepository
import org.springframework.stereotype.Repository; // Importa la anotación Repository

import com.usuario.servicioUsuario.model.Usuario; // Importa la clase Optional

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> { // Interfaz que extiende JpaRepository para operaciones CRUD
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
