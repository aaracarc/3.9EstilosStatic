package org.iesvdm.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
@Repository
//Utilizo lombok para generar el constructor
@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO {

	//JdbcTemplate se inyecta por el constructor de la clase automáticamente
	//
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void create(Comercial comercial) {
		String sqlInsert = """
							INSERT INTO comercial (nombre, apellido1, apellido2, comisión) 
							VALUES  (     ?,         ?,         ?,       ?)
						   """;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		//Con recuperación de id generado
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
			int idx = 1;
			ps.setString(idx++, comercial.getNombre());
			ps.setString(idx++, comercial.getApellido1());
			ps.setString(idx++, comercial.getApellido2());
			ps.setBigDecimal(idx++, comercial.getComision());
			return ps;
		},keyHolder);

		comercial.setId(keyHolder.getKey().intValue());
	}

	@Override
	public List<Comercial> getAll() {
		
		List<Comercial> listComercial = jdbcTemplate.query(
                "SELECT * FROM comercial",
                (rs, rowNum) -> new Comercial(rs.getInt("id"), 
                							  rs.getString("nombre"), 
                							  rs.getString("apellido1"),
                							  rs.getString("apellido2"), 
                							  rs.getBigDecimal("comisión"))

        );
		
		log.info("Devueltos {} registros.", listComercial.size());
		
        return listComercial;
	}

	@Override
	public Optional<Comercial> find(int id) {
		Comercial fab =  jdbcTemplate
				.queryForObject("SELECT * FROM comercial WHERE id = ?"
						, (rs, rowNum) -> new Comercial(rs.getInt("id"),
								rs.getString("nombre"),
								rs.getString("apellido1"),
								rs.getString("apellido2"),
								rs.getBigDecimal("comisión"))
						, id
				);

		if (fab != null) {
			return Optional.of(fab);}
		else {
			log.info("Comercial no encontrado.");
			return Optional.empty(); }
	}

	@Override
	public void update(Comercial comercial) {
		int rows = jdbcTemplate.update("""
										UPDATE comercial SET 
														nombre = ?, 
														apellido1 = ?, 
														apellido2 = ?,
														comisión = ?  
												WHERE id = ?
										""", comercial.getNombre()
				, comercial.getApellido1()
				, comercial.getApellido2()
				, comercial.getComision()
				, comercial.getId());

		log.info("Update de Comercial con {} registros actualizados.", rows);

	}

	@Override
	public void delete(long id) {
		int rows = jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);

		log.info("Delete de Comercial con {} registros eliminados.", rows);
	}

	//Estadisticas de pedidos del comercial
	@Override
	public ComercialDTO estadisticasComercial(int id) {
		ComercialDTO comercialDTO = new ComercialDTO();
		try {

			BigDecimal total = jdbcTemplate.queryForObject(
					"SELECT SUM(total) FROM pedido WHERE id_comercial = ?",
					BigDecimal.class,
					id
			);

			BigDecimal media = jdbcTemplate.queryForObject(
					"SELECT AVG(total) FROM pedido WHERE id_comercial = ?",
					BigDecimal.class,
					id
			);

			int idPedidoMinimo = jdbcTemplate.queryForObject(
					"SELECT id FROM pedido WHERE total = (SELECT MIN(total) FROM pedido WHERE id_comercial = ?)",
					Integer.class,
					id
			);

			int idPedidoMaximo = jdbcTemplate.queryForObject(
					"SELECT id FROM pedido WHERE total = (SELECT MAX(total) FROM pedido WHERE id_comercial = ?)",
					Integer.class,
					id
			);
		comercialDTO = new ComercialDTO(total.setScale(2, RoundingMode.HALF_UP), media.setScale(2, RoundingMode.HALF_UP), idPedidoMinimo, idPedidoMaximo);
		}catch (EmptyResultDataAccessException e) {
			log.info("El comercial " + id + " no tiene pedidos");
		}

		return comercialDTO;
	}
}