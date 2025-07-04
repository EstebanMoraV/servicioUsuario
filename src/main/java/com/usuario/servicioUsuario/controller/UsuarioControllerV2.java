package com.usuario.servicioUsuario.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.servicioUsuario.Assemblers.UsuarioModelAssembler;
import com.usuario.servicioUsuario.model.Usuario;
import com.usuario.servicioUsuario.service.UsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/usuarios")
@Tag(name = "Usuarios V2", description = "Operaciones relacionadas con los usuarios con HATEOAS")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    // Aquí puedes agregar los métodos del controlador que necesites, utilizando
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        List<EntityModel<Usuario>> usuarioModels = usuarios.stream()
                .map(usuarioModelAssembler::toModel)
                .toList();

        return CollectionModel.of(usuarioModels,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Integer id) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<Usuario> usuarioModel = usuarioModelAssembler.toModel(usuarioOptional.get());
        return ResponseEntity.ok(usuarioModel);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@RequestBody Usuario usuario) {
        Usuario createdUsuario = usuarioService.save(usuario);
        EntityModel<Usuario> usuarioModel = usuarioModelAssembler.toModel(createdUsuario);
        return ResponseEntity.created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(createdUsuario.getId())).toUri())
                .body(usuarioModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuario.setNombreUsuario(usuarioOptional.get().getNombreUsuario());
        usuario.setContrasena(usuarioOptional.get().getContrasena());
        usuario.setCorreo(usuarioOptional.get().getCorreo());
        usuario.setFechaNacimiento(usuarioOptional.get().getFechaNacimiento());
        usuario.setFechaCreacion(usuarioOptional.get().getFechaCreacion());

        Usuario updatedUsuario = usuarioService.save(usuario);
        EntityModel<Usuario> usuarioModel = usuarioModelAssembler.toModel(updatedUsuario);
        return ResponseEntity.ok(usuarioModel);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
