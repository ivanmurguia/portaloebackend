package dao;

import java.sql.SQLException;

import conexion.ConexionBD;
import modelo.Sociedad;

public class DaoSociedad {
	ConexionBD db;
	
	public DaoSociedad() {
		db = new ConexionBD();
	}
	
	public Sociedad buscarSociedad(int id) {
		Sociedad sociedad = new Sociedad();
		db.consultar("select * from sociedades where id = " + id);
		try {
			if(db.resultado.next()){
				sociedad.setId(id);
				sociedad.setNombre(db.resultado.getString("nombre"));
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return sociedad;
	}
	
	public Sociedad buscarSociedadNombre(String nombre) {
		Sociedad sociedad = new Sociedad();
		db.consultar("select * from sociedades where nombre = '" + nombre + "'");
		try {
			if(db.resultado.next()){
				sociedad.setId(db.resultado.getInt("id"));
				sociedad.setNombre(db.resultado.getString("nombre"));
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return sociedad;
	}
}
