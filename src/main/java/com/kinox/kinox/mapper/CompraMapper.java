package com.kinox.kinox.mapper;

import com.kinox.kinox.controller.request.CompraRequest;
import com.kinox.kinox.controller.response.CompraResponse;
import com.kinox.kinox.entity.Cliente;
import com.kinox.kinox.entity.Compra;
import com.kinox.kinox.entity.Ingresso;

public class CompraMapper {

    public static Compra toCompra(CompraRequest request) {
        Ingresso ingresso = Ingresso.builder().id(request.ingressoId()).build();
        Cliente cliente = Cliente.builder().id(request.clienteId()).build();

        return Compra.builder()
                .ingresso(ingresso)
                .cliente(cliente)
                .build();
    }

    public static CompraResponse toCompraResponse(Compra compra) {
        return CompraResponse.builder()
                .id(compra.getId())
                .ingresso(IngressoMapper.toIngressoResponse(compra.getIngresso()))
                .cliente(ClienteMapper.toClienteResponse(compra.getCliente()))
                .build();
    }
}