package com.kinox.kinox.service;

import com.kinox.kinox.entity.Cliente;
import com.kinox.kinox.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    public void deleteClienteById(Long id) {
        clienteRepository.deleteById(id);
    }
}