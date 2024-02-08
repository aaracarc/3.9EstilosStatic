package org.iesvdm.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Pedido {

    //Atributos
    private long id;
    private double total;
    private Date fecha;
    private Cliente cliente;
    private Comercial Comercial;

    public Pedido() {
    }
}
