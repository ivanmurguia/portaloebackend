package dao;

import java.sql.SQLException;

import conexion.ConexionBD;
import modelo.Estatus;

public class DaoEstatus {
	ConexionBD db;
	
	public DaoEstatus() {
		db = new ConexionBD();
	}
	
	public Estatus buscarEstatus(int id) {
		db.consultar("select * from status where id = " + id);
		try {
			if(db.resultado.next()) {
				Estatus estatus = new Estatus();
				estatus.setId(db.resultado.getInt("id"));
				estatus.setNombre(db.resultado.getString("nombre"));
				return estatus;
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
