package org.iesvdm.modelo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
//La anotación @Data de lombok proporcionará el código de: 
//getters/setters, toString, equals y hashCode
//propio de los objetos POJOS o tipo Beans
@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
public class Cliente {

	//Atributos

	private int id;

	@NotBlank(message = "{msg.valid.not.blank}")
	@Size(max=30, message = "{msg.valid.size.max}")
	private String nombre;

	@NotBlank(message = "{msg.valid.not.blank}")
	@Size(max=30, message = "{msg.valid.size.max}")
	private String apellido1;

	
	private String apellido2;

	@NotBlank(message = "{msg.valid.not.blank}")
	@Size(max=50, message = "{msg.valid.size.max}")
	private String ciudad;

	@Min(value=100, message = "{msg.valid.min}")
	@Max(value=1000, message = "{msg.valid.max}")
	private int categoria;

	@Email(message = "{msg.valid.email}")
	private String email;

	public Cliente() {
	}
}
