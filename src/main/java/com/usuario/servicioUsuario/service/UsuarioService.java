package com.usuario.servicioUsuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; // Importa la clase Autowired que sirve para inyectar dependencias
import org.springframework.stereotype.Service; // Importa la anotación Service que indica que esta clase es un servicio

import com.usuario.servicioUsuario.model.Usuario;
import com.usuario.servicioUsuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional; // Importa la anotación Transactional que indica que los métodos de esta clase están en una transacción

@Service // Indica que esta clase es un servicio
@Transactional // Indica que los métodos de esta clase están en una transacción
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarios;

    public List<Usuario> findAll() { 
        return usuarios.findAll();
    }

    public Optional<Usuario> findById(Integer id) {
        return usuarios.findById(id);
    }

    public Optional<Usuario> findByNombreUsuario(String nombreUsuario) {
        return usuarios.findByNombreUsuario(nombreUsuario);
    }

    public Usuario save(Usuario usuario) {
        return usuarios.save(usuario);
    }

    public void delete(Integer id) {
        usuarios.deleteById(id);
    }
}

