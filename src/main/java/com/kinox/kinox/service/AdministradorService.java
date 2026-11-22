package com.kinox.kinox.service;

import com.kinox.kinox.entity.Administrador;
import com.kinox.kinox.repository.AdministradorRepository;
import com.kinox.kinox.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Administrador> findAllAdministradores() {
        return administradorRepository.findAll();
    }

    public Optional<Administrador> findAdministradorById(Long id) {
        return administradorRepository.findById(id);
    }

    @Transactional
    public Administrador saveAdministrador(Administrador administrador) {
        if (administradorRepository.findByLogin(administrador.getLogin()).isPresent()
                || clienteRepository.existsByEmail(administrador.getLogin())) {
            throw new IllegalStateException("Login já cadastrado.");
        }
        administrador.setSenha(passwordEncoder.encode(administrador.getSenha()));
        return administradorRepository.save(administrador);
    }

    public void deleteAdministradorById(Long id) {
        administradorRepository.deleteById(id);
    }

}
