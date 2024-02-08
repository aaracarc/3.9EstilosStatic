package org.iesvdm.dao;

import java.util.List;
import java.util.Optional;

import org.iesvdm.modelo.Cliente;

/**
 * Interfaz que define las operaciones de acceso a datos para la entidad Cliente.
 * Proporciona métodos para la creación, recuperación, actualización y eliminación de clientes en la base de datos.
 * Las implementaciones concretas de esta interfaz deben manejar las interacciones con el almacenamiento de datos,
 * como una base de datos relacional o cualquier otro mecanismo de persistencia.
 */
public interface ClienteDAO {

	/**
	 * Crea un nuevo cliente en la base de datos.
	 * @param cliente El objeto Cliente que se va a crear.
	 */
	void create(Cliente cliente);

	/**
	 * Recupera todos los clientes almacenados en la base de datos.
	 * @return Una lista de objetos Cliente que representan a todos los clientes en la base de datos.
	 */
	List<Cliente> getAll();

	/**
	 * Recupera un cliente específico basado en su identificador único.
	 * @param id El identificador único del cliente a recuperar.
	 * @return Un objeto Optional<Cliente> que contiene el cliente recuperado, o está vacío si no se encuentra.
	 */
	Optional<Cliente> find(int id);

	/**
	 * Actualiza la información de un cliente existente en la base de datos.
	 * @param cliente El objeto Cliente con la información actualizada.
	 */
	void update(Cliente cliente);

	/**
	 * Elimina un cliente de la base de datos según su identificador único.
	 * @param id El identificador único del cliente a eliminar.
	 */
	void delete(long id);

	List<Cliente> clienteCuantiaPedidos(int id);
}
