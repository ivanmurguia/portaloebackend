package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modelo.Area;
import modelo.Usuario;

public class DaoArea {
	private List<Area> listado = new ArrayList<>();
	private DaoUsuarios daousuarios = new DaoUsuarios();
	
	public DaoArea() {
		ConexionBD db = new ConexionBD();
		try {
			db.consultar("SELECT * FROM [PortalOe].[dbo].[areas]");
			while(db.resultado.next()) {
				Area area = new Area();
				area.setId(db.resultado.getInt("id"));
				area.setNombre(db.resultado.getString("nombre"));
				area.setDimension(db.resultado.getString("dimension"));
				area.setNormaReparto(db.resultado.getString("normaReparto"));
				area.getDepartamento().setId(db.resultado.getInt("idDepartamento"));
				listado.add(area);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DaoArea(int departamento) {
		ConexionBD db = new ConexionBD();
		try {
			db.consultar("SELECT * FROM [PortalOe].[dbo].[areas] where idDepartamento = " + departamento);
			while(db.resultado.next()) {
				Area area = new Area();
				area.setId(db.resultado.getInt("id"));
				area.setNombre(db.resultado.getString("nombre"));
				area.setDimension(db.resultado.getString("dimension"));
				area.setNormaReparto(db.resultado.getString("normaReparto"));
				area.getDepartamento().setId(db.resultado.getInt("idDepartamento"));
				listado.add(area);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Area buscarArea(int id) {
		ConexionBD db = new ConexionBD();
		try {
			db.consultar("SELECT * FROM [PortalOe].[dbo].[areas] where id = " + id);
			Area area = new Area();
			while(db.resultado.next()) {
				area.setId(db.resultado.getInt("id"));
				area.setNombre(db.resultado.getString("nombre"));
				area.setDimension(db.resultado.getString("dimension"));
				area.setNormaReparto(db.resultado.getString("normaReparto"));
				area.getDepartamento().setId(db.resultado.getInt("idDepartamento"));
				area.getGerente().setId(db.resultado.getInt("idUsuario"));
			}
			return area;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Area buscarAreaPOO(int id) {
		ConexionBD db = new ConexionBD();
		try {
			db.consultar("SELECT * FROM [PortalOe].[dbo].[areas] where id = " + id);
			
			if(db.resultado.next()) {
				Area area = new Area();
				DaoDepartamento daodepartamento = new DaoDepartamento();
				DaoUsuarios daousuario = new DaoUsuarios();
				area.setId(db.resultado.getInt("id"));
				area.setNombre(db.resultado.getString("nombre"));
				area.setDimension(db.resultado.getString("dimension"));
				area.setNormaReparto(db.resultado.getString("normaReparto"));
				area.setDepartamento(daodepartamento.buscarDepartamento(db.resultado.getInt("idDepartamento")));
				area.setGerente(daousuario.buscarEncargado(db.resultado.getInt("idUsuario")));
				return area;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean actualizarArea(int id, String usuario) {
		ConexionBD db = new ConexionBD();
		Usuario user = new Usuario();
		user = daousuarios.buscarUsuarioU(usuario);
		if(user != null) {
			db.ejecutar("update areas set idUsuario = " + user.getId() +" where id = " + id);
			return true;
		}else {
			return false;
		}
	}
	
	public List<Area> getListado(){
		return listado;
	}
	
	public List<Area> importarAreas(){
		ConexionBD db = new ConexionBD();
		List<Area> listaAreas = new ArrayList<>();
		
		db.consultar("SELECT t0.id, t0.nombre , t0.normaReparto, t0.dimension,\r\n" + 
				"t1.id, t1.nombre,\r\n" + 
				"t2.id, t2.nombres, t2.apellidop, t2.apellidom, t2.email, t2.usuario\r\n" + 
				"FROM areas t0 \r\n" + 
				"left join departamentos t1 on t0.idDepartamento = t1.id \r\n" + 
				"left join usuarios t2 on t0.idUsuario = t2.id");
		
		try {
			while(db.resultado.next()) {
				Area area = new Area();
				area.setId(db.resultado.getInt(1));
				area.setNombre(db.resultado.getString(2));
				area.setNormaReparto(db.resultado.getString(3));
				area.setDimension(db.resultado.getString(4));
				area.getDepartamento().setId(db.resultado.getInt(5));
				area.getDepartamento().setNombre(db.resultado.getString(6));
				area.getGerente().setId(db.resultado.getInt(7));
				area.getGerente().setNombres(db.resultado.getString(8));
				area.getGerente().setApellidop(db.resultado.getString(9));
				area.getGerente().setApellidom(db.resultado.getString(10));
				area.getGerente().setEmail(db.resultado.getString(11));
				area.getGerente().setUsuario(db.resultado.getString(12));
				listaAreas.add(area);
			}
			return listaAreas;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
