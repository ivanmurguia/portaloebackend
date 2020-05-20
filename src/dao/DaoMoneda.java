package dao;

import java.sql.SQLException;

import conexion.ConexionBD;
import modelo.Moneda;

public class DaoMoneda {
	private ConexionBD db;
	
	public DaoMoneda() {
		db = new ConexionBD();
	}
	
	public Moneda buscarMoneda(int id) {
		db.consultar("select * from moneda where id = " + id);
		try {
			if(db.resultado.next()) {
				Moneda moneda = new Moneda();
				moneda.setId(db.resultado.getInt("id"));
				moneda.setNombre(db.resultado.getString("nombre"));
				return moneda;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
