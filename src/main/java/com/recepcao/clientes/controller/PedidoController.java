package com.recepcao.clientes.controller;

import com.recepcao.clientes.model.request.PedidoRequest;
import com.recepcao.clientes.model.response.PedidoResponse;
import com.recepcao.clientes.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PedidoResponse> criarPedido(@RequestBody PedidoRequest pedidoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criarPedido(pedidoRequest));
    }

    @PostMapping(value = "pedidos", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<PedidoResponse>> criarPedidos(@RequestBody List<PedidoRequest> pedidoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criarPedidos(pedidoRequest));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarPedidos(@RequestParam(required = false) String numeroControle,
             @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataCadastro) {
        return ResponseEntity.ok(pedidoService.listarPedidos(numeroControle, dataCadastro));
    }

    @GetMapping("{numeroControle}")
    public ResponseEntity<PedidoResponse> buscarPorNumeroControle(@PathVariable String numeroControle) {
        return ResponseEntity.ok(pedidoService.buscarPorNumeroControle(numeroControle));
    }
}
