package com.usuario.servicioUsuario.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import  io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class UsuarioConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Servicio Usuario")
                        .version("1.0.0")
                        .description("API para gestionar a los usuarios"));

    }
}
