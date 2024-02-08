package org.iesvdm.controlador;

import java.util.List;

import jakarta.validation.Valid;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /clientes como
//prefijo.
//@RequestMapping("/clientes")
public class ClienteController {

	private ClienteService clienteService;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	/**
	 * @param model se utiliza para agregar atributos que se pasarán a la vista.
	 * @return Una cadena, el nombre de la vista que se debe renderizar.
	 */
	//@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	//equivalente a la siguiente anotación
	@GetMapping("/clientes") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa

	public String listar(Model model) {
		
		List<Cliente> listaClientes =  clienteService.listAll();
		//Se agrega la lista de clientes al modelo con el nombre "listaClientes". Ya está disponible para ser utilizada en la vista.
		model.addAttribute("listaClientes", listaClientes);
				
		return "clientes";
		
	}
	//El parámetro @PathVariable Integer id indica que el valor de la variable de ruta {id} en la URL se asignará a la variable id en el método.
	@GetMapping("/clientes/{id}")
	public String detalle(Model model, @PathVariable Integer id ) {

		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);

		return "detalle-cliente";

	}
	//Pueden tener la misma ruta porque realizan diferentes acciones: la visualización del formulario de creación y el procesamiento del formulario enviado
	@GetMapping("/clientes/crear")
	public String crear(Model model) {

		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);

		return "crear-cliente";

	}

	@PostMapping("/clientes/crear")
	public String submitCrear(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "crear-cliente";
		}

		clienteService.newCliente(cliente);

		return "redirect:/clientes" ;

	}
	//El id se pasa como parámetro para realizar la consulta sobre él
	@GetMapping("/clientes/editar/{id}")
	public String editar(Model model, @PathVariable Integer id) {

		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);

		return "editar-cliente";

	}


	@PostMapping("/clientes/editar/{id}")
	public String submitEditar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "editar-cliente";
		}
		clienteService.replaceCliente(cliente);

		return "redirect:/clientes";
	}

	@PostMapping("/clientes/borrar/{id}")
	public RedirectView submitBorrar(@PathVariable Integer id) {

		clienteService.deleteCliente(id);

		return new RedirectView("/clientes");
	}
}