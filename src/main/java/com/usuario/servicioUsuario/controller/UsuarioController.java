package com.usuario.servicioUsuario.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuario Controller", description = "Controlador para gestionar usuarios OAS 3.0")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados")
    // @Operation es una anotación de Swagger que permite documentar la API
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Si la lista está vacía, devuelve un código 204 No Content
        }
        return new ResponseEntity<>(usuarios, HttpStatus.OK); 
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Devuelve un usuario específico por su ID")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/usuario/{nombreUsuario}")
    @Operation(summary = "Obtener usuario por nombre de usuario", description = "Devuelve un usuario específico por su nombre de usuario")
    public ResponseEntity<Usuario> getUsuarioByNombreUsuario(@PathVariable String nombreUsuario) { 
        Optional<Usuario> usuario = usuarioService.findByNombreUsuario(nombreUsuario);
        return usuario
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK)) //.map hace lo mismo que el if
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario y lo guarda en la base de datos")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario newUsuario = usuarioService.save(usuario);
            return new ResponseEntity<>(newUsuario, HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente por su ID")
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
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario existente por su ID")
    public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable Integer id) {
        try {
            usuarioService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
