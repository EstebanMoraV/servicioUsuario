package com.usuario.servicioUsuario.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; // Importa la clase HttpStatus que sirve para manejar los códigos de estado HTTP
import org.springframework.http.ResponseEntity; // Importa la clase ResponseEntity que sirve para construir respuestas HTTP
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.servicioUsuario.model.Usuario;
import com.usuario.servicioUsuario.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Si la lista está vacía, devuelve un código 204 No Content
        }
        return new ResponseEntity<>(usuarios, HttpStatus.OK); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/username/{nombreUsuario}")
    public ResponseEntity<Usuario> getUsuarioByNombreUsuario(@PathVariable String nombreUsuario) { 
        Optional<Usuario> usuario = usuarioService.findByNombreUsuario(nombreUsuario);
        return usuario
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK)) //.map hace lo mismo que el if
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario createdUsuario = usuarioService.save(usuario);
            return new ResponseEntity<>(createdUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        // PathVariable se usa para obtener el id de la URL
        // RequestBody se usa para obtener el objeto usuario del cuerpo de la petición
        Optional<Usuario> userData = usuarioService.findById(id);

        if (userData.isPresent()) {
            Usuario user = userData.get();
            user.setNombreUsuario(usuario.getNombreUsuario());
            user.setContrasena(usuario.getContrasena());
            user.setCorreo(usuario.getCorreo());
            user.setFechaNacimiento(usuario.getFechaNacimiento());
            // No vamos a modificar fecha de  creacion por obvias razones xddd
            Usuario updatedUsuario = usuarioService.save(user);
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable Integer id) {
        try {
            usuarioService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

