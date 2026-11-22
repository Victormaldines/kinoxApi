package com.kinox.kinox.service;

import com.kinox.kinox.entity.Cliente;
import com.kinox.kinox.entity.Compra;
import com.kinox.kinox.entity.Ingresso;
import com.kinox.kinox.repository.ClienteRepository;
import com.kinox.kinox.repository.CompraRepository;
import com.kinox.kinox.repository.IngressoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final IngressoRepository ingressoRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public Compra saveCompra(Compra compra) {
        Ingresso ingresso = ingressoRepository.findById(compra.getIngresso().getId()).orElseThrow();
        Cliente cliente = clienteRepository.findById(compra.getCliente().getId()).orElseThrow();

        compra.setIngresso(ingresso);
        compra.setCliente(cliente);
        return compraRepository.save(compra);
    }

    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    public List<Compra> findByClienteId(Long clienteId) {
        return compraRepository.findByClienteId(clienteId);
    }

    public Optional<Compra> findCompraById(Long id) {
        return compraRepository.findById(id);
    }

    public void deleteCompraById(Long id) {
        compraRepository.deleteById(id);
    }
}