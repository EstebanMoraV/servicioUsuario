package com.usuario.servicioUsuario;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.usuario.servicioUsuario.model.Usuario;
import com.usuario.servicioUsuario.repository.UsuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;


@Component
public class DataLoader  implements CommandLineRunner {
    private UsuarioRepository usuarioRepository;
    @SuppressWarnings("deprecation")
    private final Faker faker = new Faker(new java.util.Locale("es"));
    private final Random random = new Random();

    @PersistenceContext
    private EntityManager em;

    public DataLoader(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    @SuppressWarnings("deprecation")
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() > 0) {
            System.out.println("Ya hay datos. No se insertan fakes");
            return;
        }


        for (int i = 0; i < 10; i++) {
            Usuario u = new Usuario();
            u.setNombreUsuario(faker.name().username() + random.nextInt(999));
            u.setContrasena(faker.internet().password());
            u.setCorreo(faker.internet().emailAddress());
            u.setFechaNacimiento(faker.date().birthday());
            em.persist(u);

        }
        System.out.println("Datos de prueba insertados correctamente.");
    }
}
