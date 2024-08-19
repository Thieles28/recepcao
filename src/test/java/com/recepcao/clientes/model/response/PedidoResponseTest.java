package com.recepcao.clientes.model.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PedidoResponseTest {
    private PedidoResponse pedidoResponse;

    @BeforeEach
    public void setUp() {
        pedidoResponse = PedidoResponse.builder()
                .id(1L)
                .numeroControle("123456")
                .dataCadastro(LocalDate.of(2024, 8, 16))
                .nome("Produto Teste")
                .valor(new BigDecimal("150.75"))
                .quantidade(10)
                .valorTotal(new BigDecimal("1507.50"))
                .codigoCliente(1001)
                .build();
    }

    @Test
    public void testGetValor() {
        String esperado = formatarParaMoeda(new BigDecimal("150.75"));
        String atual = pedidoResponse.getValor();
        assertEquals(normalizarEspacos(esperado), normalizarEspacos(atual), "O valor formatado não está correto");
    }

    @Test
    public void testGetValorTotal() {
        String esperado = formatarParaMoeda(new BigDecimal("1507.50"));
        String atual = pedidoResponse.getValorTotal();
        assertEquals(normalizarEspacos(esperado), normalizarEspacos(atual), "O valor total formatado não está correto");
    }

    private String formatarParaMoeda(BigDecimal valor) {
        NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatador.format(valor);
    }

    private String normalizarEspacos(String texto) {
        return texto.replace("\u00A0", " ").trim();
    }
}