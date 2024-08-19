package com.recepcao.clientes.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "pedido")
@XmlAccessorType(XmlAccessType.FIELD)
public class PedidoRequest {

    @XmlElement(name = "numeroControle")
    private String numeroControle;

    @XmlElement(name = "dataCadastro")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataCadastro;

    @XmlElement(name = "nome")
    private String nome;

    @XmlElement(name = "valor")
    private BigDecimal valor;

    @XmlElement(name = "quantidade")
    private Integer quantidade;

    @XmlElement(name = "valorTotal")
    private BigDecimal valorTotal;

    @XmlElement(name = "codigoCliente")
    private Integer codigoCliente;
}