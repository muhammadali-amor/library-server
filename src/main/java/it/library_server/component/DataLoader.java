package it.library_server.component;

import it.library_server.entity.Role;
import it.library_server.entity.User;
import it.library_server.entity.enums.RoleName;
import it.library_server.repository.AuthRepository;
import it.library_server.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String intiMode;

    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (intiMode.equals("update") || intiMode.equals("create-drop")) {
            for (RoleName value : RoleName.values()) {
                roleRepository.save(new Role(value));
            }
            authRepository.save(
                    User.builder()
                            .name("Admin")
                            .surname("Adminov")
                            .email("book@gmail.com")
                            .password(passwordEncoder.encode("root1234"))
                            .roles(new HashSet<>(roleRepository.findAll()))
                            .accountNonLocked(true)
                            .accountNonExpired(true)
                            .credentialsNonExpired(true)
                            .enabled(true)
                            .build()
            );
        }
    }
}
