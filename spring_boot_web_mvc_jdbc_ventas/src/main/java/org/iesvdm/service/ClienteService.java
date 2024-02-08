package org.iesvdm.service;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.modelo.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	private ClienteDAO clienteDAO;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteService(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}
	
	public List<Cliente> listAll() {
		
		return clienteDAO.getAll();
		
	}

	public Cliente one(Integer id) {
		//Utiliza el método find del ClienteDAO para obtener un Optional de Cliente basado en el id.
		Optional<Cliente> optCli = clienteDAO.find(id);

		//Verifica si el Optional contiene un cliente.
		if (optCli.isPresent())
			//Si hay un cliente, devuelve el objeto Cliente contenido en el Optional.
			return optCli.get();
		else
			//Si el Optional está vacío, devuelve null.
			return null;
	}

	/**
	 * Agrega un nuevo cliente a la base de datos.
	 * @param cliente El objeto Cliente que se va a agregar.
	 */
	public void newCliente(Cliente cliente) {
		clienteDAO.create(cliente);
	}

	/**
	 * Reemplaza los datos de un cliente existente en la base de datos.
	 * @param cliente El objeto Cliente con los nuevos datos.
	 */
	public void replaceCliente(Cliente cliente) {
		clienteDAO.update(cliente);
	}

	/**
	 * Elimina un cliente de la base de datos según su identificador único.
	 * @param id El identificador único del cliente que se va a eliminar.
	 */
	public void deleteCliente(int id) {
		clienteDAO.delete(id);
	}


}