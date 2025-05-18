package com.usuario.servicioUsuario.service;

import com.usuario.servicioUsuario.model.Usuarios;
import com.usuario.servicioUsuario.repository.UsuarioRepository

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private InterfaceUsuarios usuarios;
    
    public List<Usuarios> findAll() {
        return UsuarioRepository.findAll();
    }
    public Usuarios findById(Long id) {
        return UsuarioRepository.findById(id).get();
    }
    public void save(Usuarios usuarios) {
        return UsuarioRepository.save(usuarios);
    }
    public void delete(Long id) {
        return UsuarioRepository.deleteById(id);
    }
}
