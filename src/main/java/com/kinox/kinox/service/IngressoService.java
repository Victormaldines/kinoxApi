package com.kinox.kinox.service;

import com.kinox.kinox.entity.Compra;
import com.kinox.kinox.entity.Ingresso;
import com.kinox.kinox.entity.SessaoAssento;
import com.kinox.kinox.repository.CompraRepository;
import com.kinox.kinox.repository.IngressoRepository;
import com.kinox.kinox.repository.SessaoAssentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngressoService {

    private final IngressoRepository ingressoRepository;
    private final CompraRepository compraRepository;
    private final SessaoAssentoRepository sessaoAssentoRepository;

    public List<Ingresso> findAllIngressos() {
        return ingressoRepository.findAll();
    }

    public Optional<Ingresso> findIngressoById(Long id) {
        return ingressoRepository.findById(id);
    }

    @Transactional
    public void deleteIngressoById(Long id) {
        ingressoRepository.findById(id).ifPresent(ingresso -> {
            SessaoAssento sessaoAssento = ingresso.getSessaoAssento();
            sessaoAssento.setOcupado(false);
            sessaoAssentoRepository.save(sessaoAssento);

            Compra compra = ingresso.getCompra();
            if (compra != null) {
                compra.setValorTotal(compra.getValorTotal().subtract(ingresso.getValorPago()));
                compra.getIngressos().remove(ingresso);
                compraRepository.save(compra);
            } else {
                ingressoRepository.delete(ingresso);
            }
        });
    }

}
