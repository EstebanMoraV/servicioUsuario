package com.usuario.servicioUsuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usuario.servicioUsuario.model.Usuarios;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

}
