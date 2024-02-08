package org.iesvdm.service;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private PedidoDAO pedidoDAO;

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired
    //@Autowired
    public PedidoService(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    public List<Pedido> listAll() {

        return pedidoDAO.getAll();

    }

    public Pedido one(Integer id) {
        Optional<Pedido> optPed = pedidoDAO.find(id);
        if (optPed.isPresent())
            return optPed.get();
        else
            return null;
    }

    public void newPedido(Pedido pedido) {

        pedidoDAO.create(pedido);

    }

    public void replacePedido(Pedido pedido) {

        pedidoDAO.update(pedido);

    }

    public void deletePedido(int id) {

        pedidoDAO.delete(id);

    }

}