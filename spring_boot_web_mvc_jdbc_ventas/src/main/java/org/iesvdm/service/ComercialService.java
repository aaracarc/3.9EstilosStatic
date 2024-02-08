package org.iesvdm.service;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComercialService {

    private ComercialDAO comercialDAO;
    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired
    //@Autowired
    public ComercialService(ComercialDAO comercialDAO, PedidoDAO pedidoDAO, ClienteDAO clienteDAO) {
        this.comercialDAO = comercialDAO;
        this.pedidoDAO = pedidoDAO;
        this.clienteDAO = clienteDAO;
    }

    public List<Comercial> listAll() {

        return comercialDAO.getAll();
    }

    public Comercial one(Integer id) {
        Optional<Comercial> optCor = comercialDAO.find(id);
        if (optCor.isPresent())
            return optCor.get();
        else
            return null;
    }

    public void newComercial(Comercial comercial) {

        comercialDAO.create(comercial);

    }

    public void replaceComercial(Comercial comercial) {

        comercialDAO.update(comercial);
    }

    public void deleteComercial(int id) {

        comercialDAO.delete(id);
    }

    //Pedido
    public List<Pedido> pedidosComercial(int id){

        return pedidoDAO.pedidosComercialID(id);
    }

    public ComercialDTO estadisticasPedidoComercial(int id)
    {
        return comercialDAO.estadisticasComercial(id);
    }

    public List<Cliente> clienteCuantiaPedidos(int id){

        return clienteDAO.clienteCuantiaPedidos(id);
    }

}