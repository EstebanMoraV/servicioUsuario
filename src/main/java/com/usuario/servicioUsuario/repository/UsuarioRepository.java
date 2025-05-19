package com.usuario.servicioUsuario.repository;

import java.util.Optional; // Importa la clase Optional

import org.springframework.data.jpa.repository.JpaRepository; // Importa la interfaz JpaRepository
import org.springframework.stereotype.Repository; // Importa la anotación Repository

import com.usuario.servicioUsuario.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> { // Interfaz que extiende JpaRepository para operaciones CRUD
    // JpaRepository<Usuario, Integer> indica que la entidad es Usuario y el tipo de ID es Integer
    Optional<Usuario> findByNombreUsuario(String nombreUsuario); // Método para encontrar un usuario por su Nombre
}
