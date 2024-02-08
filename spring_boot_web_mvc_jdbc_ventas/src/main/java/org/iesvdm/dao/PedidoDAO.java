package org.iesvdm.dao;

import org.iesvdm.modelo.Pedido;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones de acceso a datos para la entidad Pedido.
 * Proporciona métodos para la creación, recuperación, actualización y eliminación de pedidos en la base de datos.
 * Las implementaciones concretas de esta interfaz deben manejar las interacciones con el almacenamiento de datos,
 * como una base de datos relacional o cualquier otro mecanismo de persistencia.
 */
public interface PedidoDAO {

	/**
	 * Crea un nuevo pedido en la base de datos.
	 * @param pedido El objeto Pedido que se va a crear.
	 */
	void create(Pedido pedido);

	/**
	 * Recupera todos los pedidos almacenados en la base de datos.
	 * @return Una lista de objetos Pedido que representan a todos los pedidos en la base de datos.
	 */
	List<Pedido> getAll();

	/**
	 * Recupera un pedido específico basado en su identificador único.
	 * @param id El identificador único del pedido a recuperar.
	 * @return Un objeto Optional<Pedido> que contiene el pedido recuperado, o está vacío si no se encuentra.
	 */
	Optional<Pedido> find(int id);

	/**
	 * Actualiza la información de un pedido existente en la base de datos.
	 * @param pedido El objeto Pedido con la información actualizada.
	 */
	void update(Pedido pedido);

	/**
	 * Elimina un pedido de la base de datos según su identificador único.
	 * @param id El identificador único del pedido a eliminar.
	 */
	void delete(long id);

	/**
	 * Recupera una lista de pedidos asociados a un comercial específico según su ID.
	 * @param id El ID del comercial del cual se quieren obtener los pedidos.
	 * @return Una lista de objetos Pedido asociados al comercial con el ID proporcionado.
	 *         Si no se encuentran pedidos para el comercial, la lista estará vacía.
	 */
	public List<Pedido> pedidosComercialID(long id);
}
