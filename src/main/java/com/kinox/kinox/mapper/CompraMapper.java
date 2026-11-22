package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.CompraRequest;
import com.kinox.kinox.controller.response.CompraResponse;
import com.kinox.kinox.controller.response.IngressoResponse;
import com.kinox.kinox.entity.Cliente;
import com.kinox.kinox.entity.Compra;
import com.kinox.kinox.entity.Ingresso;

import java.util.List;

public class CompraMapper {

    public static Compra toCompra(CompraRequest compraRequest) {
        Cliente cliente = compraRequest.clienteId() != null
                ? Cliente.builder().id(compraRequest.clienteId()).build()
                : null;
        List<Ingresso> ingressos = compraRequest.ingressos() != null
                ? compraRequest.ingressos().stream()
                    .map(IngressoMapper::toIngresso)
                    .toList()
                : List.of();

        return Compra.builder()
                .cliente(cliente)
                .metPagamento(compraRequest.metPagamento())
                .ingressos(ingressos)
                .build();
    }

    public static CompraResponse toCompraResponse(Compra compra) {
        List<IngressoResponse> ingressosResponse = compra.getIngressos() != null
                ? compra.getIngressos().stream()
                    .map(IngressoMapper::toIngressoResponse)
                    .toList()
                : List.of();

        return CompraResponse.builder()
                .id(compra.getId())
                .cliente(ClienteMapper.toClienteResponse(compra.getCliente()))
                .valorTotal(compra.getValorTotal())
                .metPagamento(compra.getMetPagamento())
                .dataCompra(compra.getDataCompra())
                .ingressos(ingressosResponse)
                .build();
    }

}
