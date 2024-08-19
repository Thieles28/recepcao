package com.recepcao.clientes.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para a classe Pedido")
class PedidoTest {

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
    }

    @Test
    @DisplayName("Deve verificar valores padrão quando a entidade é criada")
    void testDefaultValues() {
        assertNull(pedido.getId());
        assertNull(pedido.getNumeroControle());
        assertNull(pedido.getDataCadastro());
        assertNull(pedido.getNome());
        assertNull(pedido.getValor());
        assertNull(pedido.getQuantidade());
        assertNull(pedido.getValorTotal());
        assertNull(pedido.getCodigoCliente());
    }

    @Test
    @DisplayName("Deve definir e obter corretamente os valores dos atributos")
    void testSettersAndGetters() {
        pedido.setNumeroControle("C123456");
        pedido.setDataCadastro(LocalDate.now());
        pedido.setNome("Produto Teste");
        pedido.setValor(BigDecimal.valueOf(99.99));
        pedido.setQuantidade(3);
        pedido.setValorTotal(BigDecimal.valueOf(299.97));
        pedido.setCodigoCliente(1);

        assertEquals("C123456", pedido.getNumeroControle());
        assertEquals(LocalDate.now(), pedido.getDataCadastro());
        assertEquals("Produto Teste", pedido.getNome());
        assertEquals(BigDecimal.valueOf(99.99), pedido.getValor());
        assertEquals(3, pedido.getQuantidade());
        assertEquals(BigDecimal.valueOf(299.97), pedido.getValorTotal());
        assertEquals(1, pedido.getCodigoCliente());
    }

    @Test
    @DisplayName("Deve definir e obter corretamente o código do cliente")
    void testSetAndGetCodigoCliente() {
        pedido.setCodigoCliente(10);
        assertEquals(10, pedido.getCodigoCliente());
    }

    @Test
    @DisplayName("Deve definir e obter corretamente o valor")
    void testSetAndGetValor() {
        pedido.setValor(BigDecimal.valueOf(150.75));
        assertEquals(BigDecimal.valueOf(150.75), pedido.getValor());
    }

    @Test
    @DisplayName("Deve definir e obter corretamente a quantidade")
    void testSetAndGetQuantidade() {
        pedido.setQuantidade(7);
        assertEquals(7, pedido.getQuantidade());
    }

    @Test
    @DisplayName("Deve definir e obter corretamente o valor total")
    void testSetAndGetValorTotal() {
        pedido.setValorTotal(BigDecimal.valueOf(1055.25));
        assertEquals(BigDecimal.valueOf(1055.25), pedido.getValorTotal());
    }
}