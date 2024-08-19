package com.recepcao.clientes.controller;

import com.recepcao.clientes.model.request.PedidoRequest;
import com.recepcao.clientes.model.response.PedidoResponse;
import com.recepcao.clientes.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PedidoControllerTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void criarPedido() {
        PedidoRequest pedidoRequest = new PedidoRequest();
        PedidoResponse pedidoResponse = new PedidoResponse();

        when(pedidoService.criarPedido(any(PedidoRequest.class))).thenReturn(pedidoResponse);

        ResponseEntity<PedidoResponse> response = pedidoController.criarPedido(pedidoRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pedidoResponse, response.getBody());
    }

    @Test
    void criarPedidos() {
        List<PedidoRequest> pedidoRequests = List.of(new PedidoRequest());
        List<PedidoResponse> pedidoResponses = List.of(new PedidoResponse());

        when(pedidoService.criarPedidos(any(List.class))).thenReturn(pedidoResponses);

        ResponseEntity<List<PedidoResponse>> response = pedidoController.criarPedidos(pedidoRequests);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pedidoResponses, response.getBody());
    }

    @Test
    void listarPedidos() {
        List<PedidoResponse> pedidoResponses = List.of(new PedidoResponse());
        String numeroControle = "123";
        LocalDate dataCadastro = LocalDate.now();

        when(pedidoService.listarPedidos(numeroControle, dataCadastro)).thenReturn(pedidoResponses);

        ResponseEntity<List<PedidoResponse>> response = pedidoController.listarPedidos(numeroControle, dataCadastro);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidoResponses, response.getBody());
    }

    @Test
    void buscarPorNumeroControle() {
        PedidoResponse pedidoResponse = new PedidoResponse();
        String numeroControle = "123";

        when(pedidoService.buscarPorNumeroControle(numeroControle)).thenReturn(pedidoResponse);

        ResponseEntity<PedidoResponse> response = pedidoController.buscarPorNumeroControle(numeroControle);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidoResponse, response.getBody());
    }
}