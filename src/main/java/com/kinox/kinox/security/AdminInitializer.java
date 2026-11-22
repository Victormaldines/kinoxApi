package com.kinox.kinox.security;

import com.kinox.kinox.entity.Administrador;
import com.kinox.kinox.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${kinox.security.admin.login}")
    private String adminLogin;

    @Value("${kinox.security.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (administradorRepository.findByLogin(adminLogin).isEmpty()) {
            Administrador admin = Administrador.builder()
                    .login(adminLogin)
                    .senha(passwordEncoder.encode(adminPassword))
                    .build();
            administradorRepository.save(admin);
        }
    }

}
