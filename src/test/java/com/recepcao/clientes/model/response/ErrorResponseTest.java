package com.recepcao.clientes.model.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {
    private ErrorResponse errorResponse;

    @BeforeEach
    void setUp() {
        errorResponse = new ErrorResponse(404, "Not Found", "The requested resource was not found");
    }

    @Test
    @DisplayName("Deve criar uma instância de ErrorResponse com o construtor com parâmetros")
    void testErrorResponseConstructor() {
        assertEquals(404, errorResponse.getStatus(), "O status não está correto");
        assertEquals("Not Found", errorResponse.getError(), "O erro não está correto");
        assertEquals("The requested resource was not found", errorResponse.getMessage(), "A mensagem não está correta");
        assertEquals(true, errorResponse.getTimestamp() != null, "O timestamp deve ser preenchido");
    }

    @Test
    @DisplayName("Deve criar uma instância de ErrorResponse com o construtor padrão e setter")
    void testErrorResponseDefaultConstructor() {
        errorResponse = new ErrorResponse();
        errorResponse.setStatus(500);
        errorResponse.setError("Internal Server Error");
        errorResponse.setMessage("An unexpected error occurred");
        errorResponse.setTimestamp(System.currentTimeMillis());

        assertEquals(500, errorResponse.getStatus(), "O status não está correto");
        assertEquals("Internal Server Error", errorResponse.getError(), "O erro não está correto");
        assertEquals("An unexpected error occurred", errorResponse.getMessage(), "A mensagem não está correta");
        assertEquals(true, errorResponse.getTimestamp() != null, "O timestamp deve ser preenchido");
    }
}