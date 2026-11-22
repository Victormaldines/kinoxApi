package com.kinox.kinox.service;

import com.kinox.kinox.entity.Administrador;
import com.kinox.kinox.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final AdministradorRepository administradorRepository;

    public List<Administrador> findAllAdministradores() {
        return administradorRepository.findAll();
    }

    public Optional<Administrador> findAdministradorById(Long id) {
        return administradorRepository.findById(id);
    }

    public Administrador saveAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public void deleteAdministradorById(Long id) {
        administradorRepository.deleteById(id);
    }

}
