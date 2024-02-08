package org.iesvdm.controlador;

import jakarta.validation.Valid;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.ComercialService;
import org.iesvdm.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
@Controller
public class ComercialController {

    private ComercialService comercialService;

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired
    //@Autowired
    public ComercialController(ComercialService comercialService) {
        this.comercialService = comercialService;
    }

    //@RequestMapping(value = "/clientes", method = RequestMethod.GET)
    //equivalente a la siguiente anotación
    @GetMapping("/comerciales") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
    public String listar(Model model) {

        List<Comercial> listaComerciales =  comercialService.listAll();
        model.addAttribute("listaComerciales", listaComerciales);

        return "comerciales";

    }
    @GetMapping("/comerciales/{id}")
    public String detalle(Model model, @PathVariable Integer id ) {

        Comercial comercial = comercialService.one(id);
        List<Pedido> listaPedidos = comercialService.pedidosComercial(id);
        ComercialDTO comercialDTO = comercialService.estadisticasPedidoComercial(id);
        List<Cliente> listaCliPed = comercialService.clienteCuantiaPedidos(id);

        model.addAttribute("comercial", comercial);
        model.addAttribute("listaPedidos", listaPedidos);
        model.addAttribute("comercialDTO", comercialDTO);
        model.addAttribute("listaCliPed", listaCliPed);

        return "detalle-comercial";

    }

    @GetMapping("/comerciales/crear")
    public String crear(Model model) {

        Comercial comercial = new Comercial();
        model.addAttribute("comercial", comercial);

        return "crear-comercial";

    }

    @PostMapping("/comerciales/crear")
    public String submitCrear(@Valid @ModelAttribute("comercial") Comercial comercial, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "crear-comercial";
        }

        comercialService.newComercial(comercial);

        return "redirect:/comerciales";

    }

    @GetMapping("/comerciales/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        return "editar-comercial";

    }

    @PostMapping("/comerciales/editar/{id}")
    public String submitEditar(@Valid @ModelAttribute("comercial") Comercial comercial, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "editar-comercial";
        }

        comercialService.replaceComercial(comercial);

        return "redirect:/comerciales";
    }

    @PostMapping("/comerciales/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        comercialService.deleteComercial(id);

        return new RedirectView("/comerciales");
    }
}