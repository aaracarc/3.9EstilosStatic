package org.iesvdm.controlador;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
@Controller
public class PedidoController {

    private PedidoService pedidoService;

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired
    //@Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    //@RequestMapping(value = "/clientes", method = RequestMethod.GET)
    //equivalente a la siguiente anotación
    @GetMapping("/pedidos") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
    public String listar(Model model) {

        List<Pedido> listaPedidos =  pedidoService.listAll();
        model.addAttribute("listaPedidos", listaPedidos);

        return "pedidos";

    }
    @GetMapping("/pedidos/{id}")
    public String detalle(Model model, @PathVariable Integer id ) {

        Pedido pedido = pedidoService.one(id);
        model.addAttribute("pedido", pedido);

        return "detalle-pedido";

    }

    @GetMapping("/pedidos/crear")
    public String crear(Model model) {

        Pedido pedido = new Pedido();
        model.addAttribute("pedido", pedido);

        return "crear-pedido";

    }

    @PostMapping("/pedidos/crear")
    public RedirectView submitCrear(@ModelAttribute("pedido") Pedido pedido) {

        pedidoService.newPedido(pedido);

        return new RedirectView("/pedidos") ;

    }

    @GetMapping("/pedidos/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Pedido pedido = pedidoService.one(id);
        model.addAttribute("pedido", pedido);

        return "editar-pedido";

    }

    @PostMapping("/pedidos/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("pedido") Pedido pedido) {

        pedidoService.replacePedido(pedido);

        return new RedirectView("/pedidos");
    }

    @PostMapping("/pedidos/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        pedidoService.deletePedido(id);

        return new RedirectView("/pedidos");
    }
}