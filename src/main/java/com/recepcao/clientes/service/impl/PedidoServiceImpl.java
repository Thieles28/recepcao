package com.recepcao.clientes.service.impl;


import com.recepcao.clientes.model.entity.Pedido;
import com.recepcao.clientes.model.request.PedidoRequest;
import com.recepcao.clientes.model.response.PedidoResponse;
import com.recepcao.clientes.repository.PedidoRepository;
import com.recepcao.clientes.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;

    @Override
    public PedidoResponse criarPedido(PedidoRequest pedidoRequest) {
        verificaCodigoClienteValido(pedidoRequest);
        verificaNumeroControle(pedidoRequest);

        Pedido pedido = modelMapper.map(pedidoRequest, Pedido.class);

        verificaValorPadrao(pedido);
        verificaQuantidadeDePedido(pedido);

        BigDecimal valorTotal = calcularValorTotal(pedido.getValor(), pedido.getQuantidade());
        pedido.setValorTotal(valorTotal);

        Pedido salvarPedido = pedidoRepository.save(pedido);
        return modelMapper.map(salvarPedido, PedidoResponse.class);
    }

    @Override
    public List<PedidoResponse> criarPedidos(List<PedidoRequest> pedidoRequests) {
        validaQuantiadeDePedido(pedidoRequests);

        List<PedidoResponse> responses = new ArrayList<>();
        for (PedidoRequest pedidoRequest : pedidoRequests) {
            responses.add(criarPedido(pedidoRequest));
        }

        return responses;
    }

    private BigDecimal calcularValorTotal(BigDecimal valor, Integer quantidade) {
        BigDecimal total = valor.multiply(BigDecimal.valueOf(quantidade));
        if (quantidade >= 10) {
            return total.multiply(BigDecimal.valueOf(0.9));
        } else if (quantidade > 5) {
            return total.multiply(BigDecimal.valueOf(0.95));
        }
        return total;
    }

    @Override
    public List<PedidoResponse> listarPedidos(String numeroControle, LocalDate dataCadastro) {
        return pedidoRepository.findAll().stream()
                .filter(pedido -> (numeroControle == null || pedido.getNumeroControle().contains(numeroControle)))
                .filter(pedido -> (dataCadastro == null || pedido.getDataCadastro().equals(dataCadastro)))
                .map(pedido -> modelMapper.map(pedido, PedidoResponse.class))
                .toList();
    }

    @Override
    public PedidoResponse buscarPorNumeroControle(String numeroControle) {
        Pedido pedido = pedidoRepository.findByNumeroControle(numeroControle)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        return modelMapper.map(pedido, PedidoResponse.class);
    }

    private static void verificaQuantidadeDePedido(Pedido pedido) {
        if (pedido.getQuantidade() == null) {
            pedido.setQuantidade(1);
        }
    }

    private static void verificaValorPadrao(Pedido pedido) {
        if (pedido.getDataCadastro() == null) {
            pedido.setDataCadastro(LocalDate.now());
        }
    }

    private void verificaNumeroControle(PedidoRequest pedidoRequest) {
        if (pedidoRepository.findByNumeroControle(pedidoRequest.getNumeroControle()).isPresent()) {
            throw new IllegalArgumentException("Número de controle já cadastrado");
        }
    }

    private static void verificaCodigoClienteValido(PedidoRequest pedidoRequest) {
        if (pedidoRequest.getCodigoCliente() == null || pedidoRequest.getCodigoCliente() < 1 || pedidoRequest.getCodigoCliente() > 10) {
            throw new IllegalArgumentException("Código do cliente inválido. Deve estar entre 1 e 10.");
        }
    }

    private static void validaQuantiadeDePedido(List<PedidoRequest> pedidoRequests) {
        if (pedidoRequests.size() > 10) {
            throw new IllegalArgumentException("Não é permitido processar mais de 10 pedidos por vez.");
        }
    }
}
