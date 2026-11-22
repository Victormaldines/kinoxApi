package com.kinox.kinox.service;

import com.kinox.kinox.entity.Cliente;
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
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Cliente> findAllClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente saveCliente(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())
                || administradorRepository.findByLogin(cliente.getEmail()).isPresent()) {
            throw new IllegalStateException("Email já cadastrado.");
        }
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new IllegalStateException("CPF já cadastrado.");
        }
        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente updateCliente(Long id, Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteRepository.findById(id).orElseThrow();
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setNascimento(clienteAtualizado.getNascimento());
        clienteExistente.setEmail(clienteAtualizado.getEmail());

        if (clienteAtualizado.getSenha() != null && !clienteAtualizado.getSenha().isBlank()) {
            clienteExistente.setSenha(passwordEncoder.encode(clienteAtualizado.getSenha()));
        }

        return clienteRepository.save(clienteExistente);
    }

    public void deleteClienteById(Long id) {
        clienteRepository.deleteById(id);
    }

}
