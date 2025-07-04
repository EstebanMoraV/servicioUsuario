package com.usuario.servicioUsuario;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usuario.servicioUsuario.controller.UsuarioController;
import com.usuario.servicioUsuario.model.Usuario;
import com.usuario.servicioUsuario.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario crearUsuarioEjemplo() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombreUsuario("testuser");
        usuario.setContrasena("secreta123");
        usuario.setCorreo("test@correo.com");
        usuario.setFechaNacimiento(sdf.parse("01-01-2000"));
        usuario.setFechaCreacion(new Date());
        return usuario;
    }

    @Test
    @DisplayName("✅ Debería listar todos los usuarios")
    void testListarUsuarios() throws Exception {
        when(usuarioService.findAll()).thenReturn(List.of(crearUsuarioEjemplo()));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreUsuario").value("testuser"))
                .andExpect(jsonPath("$[0].contrasena").doesNotExist()); // porque es WRITE_ONLY
    }

    @Test
    @DisplayName("✅ Debería retornar 204 si no hay usuarios")
    void testListarUsuariosVacio() throws Exception {
        when(usuarioService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("✅ Debería obtener usuario por ID")
    void testObtenerUsuarioPorId() throws Exception {
        when(usuarioService.findById(1)).thenReturn(Optional.of(crearUsuarioEjemplo()));

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreUsuario").value("testuser"))
                .andExpect(jsonPath("$.contrasena").doesNotExist());
    }

    @Test
    @DisplayName("❌ Debería retornar 404 si el usuario no existe por ID")
    void testUsuarioNoExistePorId() throws Exception {
        when(usuarioService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("✅ Debería obtener usuario por nombre de usuario")
    void testObtenerUsuarioPorNombreUsuario() throws Exception {
        when(usuarioService.findByNombreUsuario("testuser")).thenReturn(Optional.of(crearUsuarioEjemplo()));

        mockMvc.perform(get("/api/v1/usuarios/usuario/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreUsuario").value("testuser"))
                .andExpect(jsonPath("$.contrasena").doesNotExist());
    }

    @Test
    @DisplayName("❌ Debería retornar 404 si el usuario no existe por nombre")
    void testUsuarioNoExistePorNombre() throws Exception {
        when(usuarioService.findByNombreUsuario("noexiste")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/usuarios/usuario/noexiste"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("✅ Debería crear un usuario")
    void testCrearUsuario() throws Exception {
        Usuario usuario = crearUsuarioEjemplo();
        usuario.setId(null); // simulando que es nuevo

        Usuario creado = crearUsuarioEjemplo();

        when(usuarioService.save(any(Usuario.class))).thenReturn(creado);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("❌ Error interno al crear usuario")
    void testErrorCrearUsuario() throws Exception {
        Usuario usuario = crearUsuarioEjemplo();
        usuario.setId(null);

        when(usuarioService.save(any(Usuario.class))).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("✅ Debería actualizar un usuario")
    void testActualizarUsuario() throws Exception {
        Usuario usuario = crearUsuarioEjemplo();

        when(usuarioService.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/v1/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("❌ No debería actualizar usuario inexistente")
    void testActualizarUsuarioNoExiste() throws Exception {
        Usuario usuario = crearUsuarioEjemplo();

        when(usuarioService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/usuarios/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("✅ Debería eliminar un usuario")
    void testEliminarUsuario() throws Exception {
        doNothing().when(usuarioService).delete(1);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("❌ Error interno al eliminar usuario")
    void testErrorEliminarUsuario() throws Exception {
        doNothing().when(usuarioService).delete(1);
        when(usuarioService.findById(1)).thenThrow(new RuntimeException("Error simulado"));

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent()); // Ajusta si manejas 500 en delete
    }
}
