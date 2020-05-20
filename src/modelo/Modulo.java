package modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;

public class Modulo {
	private int id;
	private String nombre;
	private List<Submodulo> submodulos = new ArrayList<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Submodulo> getSubmodulos() {
		return submodulos;
	}
	public void setSubmodulos(List<Submodulo> submodulos) {
		this.submodulos = submodulos;
	}
	public void buscar(){
		ConexionBD db = new ConexionBD();
		db.consultar("select nombre from modulo where id = " + getId());
		try {
			if(db.resultado.next()) {
				setNombre(db.resultado.getString("nombre"));
			}
			db.consultar("select id, nombre, ver from submodulos where idmodulo = " + getId() + " order by idModulo");
			while(db.resultado.next()) {
				Submodulo submodulo = new Submodulo();
				submodulo.setId(db.resultado.getInt("int"));
				submodulo.setNombre(db.resultado.getString("nombre"));
				submodulo.setVer(db.resultado.getBoolean("ver"));
				submodulos.add(submodulo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<Modulo> buscar(int idUsuario) {
		ConexionBD db = new ConexionBD();
		List<Modulo> modulos = new ArrayList<>();
		db.consultar("select t0.id moduloId, t0.nombre moduloNombre, t1.id submoduloId, t1.nombre submoduloNombre, t1.href, t1.icon, t1.ver , t3.permiso \r\n" + 
				"from modulos t0\r\n" + 
				"inner join submodulos t1 on t0.id = t1.idmodulo\r\n" + 
				"inner join submodulosdetalle t3 on t3.idsubmodulo = t1.id\r\n" + 
				"where t3.idusuario = " + idUsuario + " and permiso = 1 order by t0.id");
		try {
			while(db.resultado.next()) {
				if(modulos.size() == 0) {
					Modulo modulo = new Modulo();
					modulo.setId(db.resultado.getInt("moduloId"));
					modulo.setNombre(db.resultado.getString("moduloNombre"));
					modulos.add(modulo);
					
					Submodulo submodulo = new Submodulo();
					
					submodulo.setId(db.resultado.getInt("submoduloId"));
					submodulo.setNombre(db.resultado.getString("submodulonombre"));
					submodulo.setHref(db.resultado.getString("href"));
					submodulo.setIcon(db.resultado.getString("icon"));
					submodulo.setVer(db.resultado.getBoolean("ver"));
					submodulo.setPermiso(db.resultado.getBoolean("permiso"));
					
					modulos.get(0).getSubmodulos().add(submodulo);
				} else {
					if(db.resultado.getInt("moduloid") == modulos.get(modulos.size() - 1).getId()) {
						Submodulo submodulo = new Submodulo();
						
						submodulo.setId(db.resultado.getInt("submoduloId"));
						submodulo.setNombre(db.resultado.getString("submodulonombre"));
						submodulo.setHref(db.resultado.getString("href"));
						submodulo.setIcon(db.resultado.getString("icon"));	
						submodulo.setVer(db.resultado.getBoolean("ver"));
						submodulo.setPermiso(db.resultado.getBoolean("permiso"));
						
						modulos.get(modulos.size() - 1).getSubmodulos().add(submodulo);
					} else {
						Modulo modulo = new Modulo();
						modulo.setId(db.resultado.getInt("moduloId"));
						modulo.setNombre(db.resultado.getString("moduloNombre"));
						modulos.add(modulo);
						
						Submodulo submodulo = new Submodulo();
						
						submodulo.setId(db.resultado.getInt("submoduloId"));
						submodulo.setNombre(db.resultado.getString("submodulonombre"));
						submodulo.setHref(db.resultado.getString("href"));
						submodulo.setIcon(db.resultado.getString("icon"));		
						submodulo.setVer(db.resultado.getBoolean("ver"));
						submodulo.setPermiso(db.resultado.getBoolean("permiso"));
						
						modulos.get(modulos.size() - 1).getSubmodulos().add(submodulo);
					}
				}
			}
			return modulos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
