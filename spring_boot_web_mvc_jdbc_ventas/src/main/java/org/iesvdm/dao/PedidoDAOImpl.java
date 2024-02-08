package org.iesvdm.dao;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class PedidoDAOImpl implements PedidoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public synchronized void create(Pedido pedido) {
		String sqlInsert = """
                INSERT INTO pedido (total, fecha, id_cliente, id_comercial)
                VALUES (?, ?, ?, ?)
                """;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[]{"id"});
			int idx = 1;
			ps.setDouble(idx++, pedido.getTotal());

			java.sql.Date fechaSql = new java.sql.Date(pedido.getFecha().getTime());
			ps.setDate(idx++, fechaSql);

			ps.setInt(idx++, pedido.getCliente().getId());
			ps.setInt(idx++, pedido.getComercial().getId());
			return ps;
		}, keyHolder);

		pedido.setId(keyHolder.getKey().intValue());

		log.info("Insertados {} registros.", rows);
	}

	@Override
	public List<Pedido> getAll() {
		List<Pedido> listaPedido = this.jdbcTemplate.query("""
                SELECT * FROM pedido P 
                LEFT JOIN cliente C ON P.id_cliente = C.id
                LEFT JOIN comercial CO ON P.id_comercial = CO.id
                """, (rs, rowNum) -> UtilDAO.newPedido(rs));

		log.info("Devueltos {} registros.", listaPedido.size());

		return listaPedido;
	}

	@Override
	public Optional<Pedido> find(int id) {
		Pedido pedido = this.jdbcTemplate.queryForObject("""
                SELECT * FROM pedido P 
                LEFT JOIN cliente C ON P.id_cliente = C.id
                LEFT JOIN comercial CO ON P.id_comercial = CO.id
                WHERE P.id = ?
                """, (rs, rowNum) -> UtilDAO.newPedido(rs), id);

		if (pedido != null) return Optional.of(pedido);
		log.debug("No encontrado pedido con id {} devolviendo Optional.empty()", id);
		return Optional.empty();
	}

	@Override
	public void update(Pedido pedido) {
		int rows = jdbcTemplate.update("""
                UPDATE pedido SET 
                total = ?, 
                fecha = ?, 
                id_cliente = ?,
                id_comercial = ?  
                WHERE id = ?
                """, pedido.getTotal(), pedido.getFecha(), pedido.getCliente().getId(),
				pedido.getComercial().getId(), pedido.getId());

		log.info("Update de Pedido con {} registros actualizados.", rows);
	}

	@Override
	public void delete(long id) {
		int rows = jdbcTemplate.update("DELETE FROM pedido WHERE id = ?", id);

		log.info("Delete de Pedido con {} registros eliminados.", rows);
	}

	@Override
	public List<Pedido> pedidosComercialID(long idComercial) {
		List<Pedido> listaPedido = jdbcTemplate.query("""
                SELECT * FROM pedido P 
                LEFT JOIN cliente C ON P.id_cliente = C.id
                LEFT JOIN comercial CO ON P.id_comercial = CO.id
                WHERE CO.id = ?
                """, (rs, rowNum) -> UtilDAO.newPedido(rs), idComercial);

		return listaPedido;
	}
}
