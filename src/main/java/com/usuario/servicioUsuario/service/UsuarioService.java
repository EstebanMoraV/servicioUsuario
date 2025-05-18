package com.usuario.servicioUsuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.servicioUsuario.model.Usuarios;
import com.usuario.servicioUsuario.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarios;
    
    public List<Usuarios> findAll() {
        return usuarios.findAll();
    }
    public Usuarios findById(Long id) {
        return usuarios.findById(id).get();
    }
    public Usuarios save (Usuarios user) {
        return usuarios.save(user);
    }
    public void delete(Long id) {
        usuarios.deleteById(id);
    }
}
