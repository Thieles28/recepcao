package com.recepcao.clientes.service.impl;

import com.recepcao.clientes.model.entity.Pedido;
import com.recepcao.clientes.model.request.PedidoRequest;
import com.recepcao.clientes.model.response.PedidoResponse;
import com.recepcao.clientes.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @BeforeEach
    void setUp() {
        pedidoService = new PedidoServiceImpl(pedidoRepository, modelMapper);
    }

    @Test
    void testListarPedidosPedidoRepository() {
        when(pedidoRepository.findAll()).thenReturn(Collections.emptyList());
        final List<PedidoResponse> result = pedidoService.listarPedidos("numeroControle",
                LocalDate.of(2020, 1, 1));

        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testBuscarPorNumeroControlePedidoRepository() {
        when(pedidoRepository.findByNumeroControle("numeroControle")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.buscarPorNumeroControle("numeroControle"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void criarPedidoDeveRetornarPedidoResponseQuandoPedidoValido() {
        PedidoRequest pedidoRequest = new PedidoRequest();
        pedidoRequest.setCodigoCliente(5);
        pedidoRequest.setNumeroControle("12345");
        pedidoRequest.setValor(BigDecimal.valueOf(100));
        pedidoRequest.setQuantidade(10);

        Pedido pedido = new Pedido();
        pedido.setCodigoCliente(5);
        pedido.setNumeroControle("12345");
        pedido.setValor(BigDecimal.valueOf(100));
        pedido.setQuantidade(10);
        pedido.setValorTotal(BigDecimal.valueOf(900));

        PedidoResponse expectedResponse = new PedidoResponse();
        expectedResponse.setNumeroControle("12345");
        expectedResponse.setValorTotal(BigDecimal.valueOf(900));

        when(modelMapper.map(pedidoRequest, Pedido.class)).thenReturn(pedido);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(modelMapper.map(pedido, PedidoResponse.class)).thenReturn(expectedResponse);
        when(pedidoRepository.findByNumeroControle("12345")).thenReturn(Optional.empty());

        PedidoResponse actualResponse = pedidoService.criarPedido(pedidoRequest);

        assertEquals(expectedResponse.getValorTotal(), actualResponse.getValorTotal());
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    void criarPedido_DeveLancarExcecao_QuandoCodigoClienteInvalido() {
        PedidoRequest pedidoRequest = new PedidoRequest();
        pedidoRequest.setCodigoCliente(15);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                pedidoService.criarPedido(pedidoRequest));

        assertEquals("Código do cliente inválido. Deve estar entre 1 e 10.", exception.getMessage());
    }

    @Test
    void criarPedidoDeveLancarExcecaoQuandoNumeroControleDuplicado() {
        PedidoRequest pedidoRequest = new PedidoRequest();
        pedidoRequest.setNumeroControle("C7890151");
        pedidoRequest.setCodigoCliente(1);

        Pedido pedidoExistente = new Pedido();
        when(pedidoRepository.findByNumeroControle("C7890151")).thenReturn(Optional.of(pedidoExistente));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                pedidoService.criarPedido(pedidoRequest));

        assertEquals("Número de controle já cadastrado", exception.getMessage());
    }

    @Test
    void criarPedidoDeveAtribuirValorPadraoQuandoDataCadastroENula() {
        // Arrange
        PedidoRequest pedidoRequest = new PedidoRequest();
        pedidoRequest.setCodigoCliente(5);
        pedidoRequest.setNumeroControle("12345");

        Pedido pedido = new Pedido();
        pedido.setCodigoCliente(5);
        pedido.setNumeroControle("12345");
        pedido.setValor(BigDecimal.valueOf(100));
        pedido.setQuantidade(10);

        when(modelMapper.map(pedidoRequest, Pedido.class)).thenReturn(pedido);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(modelMapper.map(pedido, PedidoResponse.class)).thenReturn(new PedidoResponse());
        when(pedidoRepository.findByNumeroControle("12345")).thenReturn(Optional.empty());

        pedidoService.criarPedido(pedidoRequest);
        assertNotNull(pedido.getDataCadastro());
    }

    @Test
    void criarPedidoDeveAtribuirQuantidadePadraoQuandoQuantidadeENula() {
        PedidoRequest pedidoRequest = new PedidoRequest();
        pedidoRequest.setCodigoCliente(5);
        pedidoRequest.setNumeroControle("12345");

        Pedido pedido = new Pedido();
        pedido.setCodigoCliente(5);
        pedido.setNumeroControle("12345");
        pedido.setValor(BigDecimal.valueOf(100));
        pedido.setQuantidade(null);

        when(modelMapper.map(pedidoRequest, Pedido.class)).thenReturn(pedido);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(modelMapper.map(pedido, PedidoResponse.class)).thenReturn(new PedidoResponse());
        when(pedidoRepository.findByNumeroControle("12345")).thenReturn(Optional.empty());

        pedidoService.criarPedido(pedidoRequest);
        assertEquals(1, pedido.getQuantidade());
    }

    @Test
    void criarPedidosDeveLancarExcecaoQuandoQuantidadeDePedidosExcedeLimite() {
        // Arrange
        List<PedidoRequest> pedidoRequests = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            PedidoRequest pedidoRequest = new PedidoRequest();
            pedidoRequest.setCodigoCliente(5);
            pedidoRequest.setNumeroControle("12345-" + i);
            pedidoRequest.setValor(BigDecimal.valueOf(100));
            pedidoRequest.setQuantidade(2);
            pedidoRequests.add(pedidoRequest);
        }

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                pedidoService.criarPedidos(pedidoRequests));

        assertEquals("Não é permitido processar mais de 10 pedidos por vez.", exception.getMessage());
    }
}
