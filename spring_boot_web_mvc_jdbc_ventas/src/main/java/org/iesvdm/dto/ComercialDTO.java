package org.iesvdm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ComercialDTO {

    private BigDecimal total;
    private BigDecimal media;
    private int idPedidoMinimo;
    private int idPedidoMaximo;

    public ComercialDTO(){}
}