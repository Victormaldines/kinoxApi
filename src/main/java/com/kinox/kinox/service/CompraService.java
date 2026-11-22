package com.kinox.kinox.service;

import com.kinox.kinox.entity.Cliente;
import com.kinox.kinox.entity.Compra;
import com.kinox.kinox.entity.Ingresso;
import com.kinox.kinox.entity.SessaoAssento;
import com.kinox.kinox.repository.ClienteRepository;
import com.kinox.kinox.repository.CompraRepository;
import com.kinox.kinox.repository.SessaoAssentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ClienteRepository clienteRepository;
    private final SessaoAssentoRepository sessaoAssentoRepository;

    public List<Compra> findAllCompras() {
        return compraRepository.findAll();
    }

    public List<Compra> findComprasByClienteId(Long clienteId) {
        return compraRepository.findByClienteId(clienteId);
    }

    public Optional<Compra> findCompraById(Long id) {
        return compraRepository.findById(id);
    }

    @Transactional
    public Compra saveCompra(Compra compra) {
        Cliente cliente = clienteRepository.findById(compra.getCliente().getId()).orElseThrow();

        if (compra.getIngressos() == null || compra.getIngressos().isEmpty()) {
            throw new IllegalArgumentException("A compra deve conter ao menos um ingresso.");
        }

        BigDecimal valorTotal = BigDecimal.ZERO;
        List<Ingresso> ingressosProcessados = new ArrayList<>();

        for (Ingresso item : compra.getIngressos()) {
            SessaoAssento sessaoAssento = sessaoAssentoRepository
                    .findById(item.getSessaoAssento().getId())
                    .orElseThrow();

            if (sessaoAssento.isOcupado()) {
                throw new IllegalStateException("Assento já está ocupado: " + sessaoAssento.getId());
            }

            sessaoAssento.setOcupado(true);
            sessaoAssentoRepository.save(sessaoAssento);

            BigDecimal precoBase = sessaoAssento.getSessao().getSala().getPrecoDoIngresso();
            if (precoBase == null) {
                precoBase = BigDecimal.ZERO;
            }

            BigDecimal valorPago = item.isInteira()
                    ? precoBase
                    : precoBase.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);

            Ingresso ingresso = Ingresso.builder()
                    .compra(compra)
                    .sessaoAssento(sessaoAssento)
                    .isInteira(item.isInteira())
                    .valorPago(valorPago)
                    .build();

            ingressosProcessados.add(ingresso);
            valorTotal = valorTotal.add(valorPago);
        }

        compra.setCliente(cliente);
        compra.setIngressos(ingressosProcessados);
        compra.setValorTotal(valorTotal);

        return compraRepository.save(compra);
    }

    @Transactional
    public void deleteCompraById(Long id) {
        compraRepository.findById(id).ifPresent(compra -> {
            if (compra.getIngressos() != null) {
                for (Ingresso ingresso : compra.getIngressos()) {
                    ingresso.getSessaoAssento().setOcupado(false);
                    sessaoAssentoRepository.save(ingresso.getSessaoAssento());
                }
            }
            compraRepository.delete(compra);
        });
    }

}
