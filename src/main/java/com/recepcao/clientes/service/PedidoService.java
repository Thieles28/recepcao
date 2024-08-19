package com.recepcao.clientes.service;

import com.recepcao.clientes.model.request.PedidoRequest;
import com.recepcao.clientes.model.response.PedidoResponse;

import java.time.LocalDate;
import java.util.List;

public interface PedidoService {
    PedidoResponse criarPedido(PedidoRequest pedidoRequest);
    List<PedidoResponse> criarPedidos(List<PedidoRequest> pedidoRequests);
    List<PedidoResponse> listarPedidos(String numeroControle, LocalDate dataCadastro);
    PedidoResponse buscarPorNumeroControle(String numeroControle);
}
