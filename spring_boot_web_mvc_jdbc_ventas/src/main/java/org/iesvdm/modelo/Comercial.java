package org.iesvdm.modelo;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Comercial {

	//Atributos

	private int id;

	@NotBlank(message = "{msg.valid.not.blank}")
	@Size(max=30, message = "{msg.valid.size.max}")
	private String nombre;

	@NotBlank(message = "{msg.valid.not.blank}")
	@Size(max=30, message = "{msg.valid.size.max}")
	private String apellido1;

	private String apellido2;

	@DecimalMin(value = "0.276", inclusive = true, message = "{msg.valid.min}")
	@DecimalMax(value = "0.946", inclusive = true, message = "{msg.valid.max}")
	private BigDecimal comision;

	public Comercial() {
	}
}
