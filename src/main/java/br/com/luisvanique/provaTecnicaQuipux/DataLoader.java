package br.com.luisvanique.provaTecnicaQuipux;

import br.com.luisvanique.provaTecnicaQuipux.domain.Usuario;
import br.com.luisvanique.provaTecnicaQuipux.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public DataLoader(UsuarioRepository repository, PasswordEncoder encoder){
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.findByUsername("admin").isEmpty()) {
            Usuario user = new Usuario();
            user.setUsername("admin");
            user.setPassword(encoder.encode("admin"));
            user.setRole("ROLE_USER");
            repository.save(user);
        }
    }
}
