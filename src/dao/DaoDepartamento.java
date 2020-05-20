package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modelo.Departamento;
import modelo.Usuario;

public class DaoDepartamento {
	private final static DaoDepartamento dataBase = new DaoDepartamento();
	private final List<Departamento> listado = new ArrayList<>();
	private DaoUsuarios daousuarios = new DaoUsuarios();
	
	public DaoDepartamento() {
		ConexionBD db = new ConexionBD();

		try {
			db.consultar("SELECT * FROM departamentos");
			while(db.resultado.next()) {
				Departamento departamento = new Departamento();
				departamento.setId(db.resultado.getInt("id"));
				departamento.setNombre(db.resultado.getString("nombre"));
				departamento.setMonto(db.resultado.getFloat("monto"));
				listado.add(departamento);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Departamento buscarDepartamento(int id) {
		System.out.println("entro en metodo");
		ConexionBD db = new ConexionBD();
		System.out.println("conectado a la db");
		db.consultar("select * from departamentos where id = " + id);
		System.out.println("Se hizo la consulta: \n" + "select * from departamentos where id = " + id);
		try {
			if(db.resultado.next()) {
				Departamento departamento = new Departamento();
				DaoUsuarios daousuario = new DaoUsuarios();
				departamento.setId(db.resultado.getInt("id"));
				departamento.setNombre(db.resultado.getString("nombre"));
				departamento.setMonto(db.resultado.getFloat("monto"));
				departamento.setDirector(daousuario.buscarEncargado(db.resultado.getInt("idUsuario")));
				return departamento;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Departamento> importarDepartamentos(){
		ConexionBD db = new ConexionBD();
		List<Departamento> listadod = new ArrayList<>();
		try {
			System.out.println("SELECT t0.id 't0.id', t0.nombre 't0.nombre', t0.monto 't0.monto', t1.id 't1.id', t1.nombres 't1.nombres', t1.apellidop 't1.apellidop', t1.apellidom 't1.apellidom', t1.email 't1.email', t1.usuario 't1.usuario'  FROM departamentos t0 left join usuarios t1 on t0.idUsuario = t1.id");
			db.consultar("SELECT t0.id 't0.id', t0.nombre 't0.nombre', t0.monto 't0.monto', t1.id 't1.id', t1.nombres 't1.nombres', t1.apellidop 't1.apellidop', t1.apellidom 't1.apellidom', t1.email 't1.email', t1.usuario 't1.usuario'  FROM departamentos t0 left join usuarios t1 on t0.idUsuario = t1.id");
			while(db.resultado.next()) {
				Departamento departamento = new Departamento();
				departamento.setId(db.resultado.getInt("t0.id"));
				departamento.setNombre(db.resultado.getString("t0.nombre"));
				departamento.setMonto(db.resultado.getFloat("t0.monto"));
				departamento.getDirector().setId(db.resultado.getInt("t1.id"));
				departamento.getDirector().setNombres(db.resultado.getString("t1.nombres"));
				departamento.getDirector().setApellidop(db.resultado.getString("t1.apellidop"));
				departamento.getDirector().setApellidom(db.resultado.getString("t1.apellidom"));
				departamento.getDirector().setEmail(db.resultado.getString("t1.email"));
				departamento.getDirector().setUsuario(db.resultado.getString("t1.usuario"));
				listadod.add(departamento);
			}
			return listadod;
		} catch (SQLException e) {
			System.out.println("Error SQL: " + e);
			return null;
		}
	}
	
	public List<Departamento> buscarDepartamentosPorEncargado(int idEncargado){
		ConexionBD db = new ConexionBD();
		List<Departamento> listadod = new ArrayList<>();
		try {
			System.out.println("SELECT t0.id 't0.id', t0.nombre 't0.nombre', t0.monto 't0.monto', t1.id 't1.id', t1.nombres 't1.nombres', t1.apellidop 't1.apellidop', t1.apellidom 't1.apellidom', t1.email 't1.email', t1.usuario 't1.usuario' FROM departamentos t0 left join usuarios t1 on t0.idUsuario = t1.id where t0.idUsuario = " + idEncargado);
			db.consultar("SELECT t0.id 't0.id', t0.nombre 't0.nombre', t0.monto 't0.monto', t1.id 't1.id', t1.nombres 't1.nombres', t1.apellidop 't1.apellidop', t1.apellidom 't1.apellidom', t1.email 't1.email', t1.usuario 't1.usuario' FROM departamentos t0 left join usuarios t1 on t0.idUsuario = t1.id where t0.idUsuario = " + idEncargado);
			while(db.resultado.next()) {
				Departamento departamento = new Departamento();
				departamento.setId(db.resultado.getInt("t0.id"));
				departamento.setNombre(db.resultado.getString("t0.nombre"));
				departamento.setMonto(db.resultado.getFloat("t0.monto"));
				departamento.getDirector().setId(db.resultado.getInt("t1.id"));
				departamento.getDirector().setNombres(db.resultado.getString("t1.nombres"));
				departamento.getDirector().setApellidop(db.resultado.getString("t1.apellidop"));
				departamento.getDirector().setApellidom(db.resultado.getString("t1.apellidom"));
				departamento.getDirector().setEmail(db.resultado.getString("t1.email"));
				departamento.getDirector().setUsuario(db.resultado.getString("t1.usuario"));
				listadod.add(departamento);
			}
			return listadod;
		} catch (SQLException e) {
			return null;
		}
	}
	
	public boolean actualizarDepartamento(int id, String usuario) {
		ConexionBD db = new ConexionBD();
		Usuario user = new Usuario();
		user = daousuarios.buscarUsuarioU(usuario);
		if(user != null) {
			db.ejecutar("update departamentos set idUsuario = " + user.getId() +" where id = " + id);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean actualizarMonto(int id, float monto) {
		ConexionBD db = new ConexionBD();
		db.ejecutar("update departamentos set monto = " + monto +" where id = " + id);
		return true;
	}
	
	public float getMonto(int id) {
		ConexionBD db = new ConexionBD();
		db.consultar("select monto from departamentos where id = " + id);
		try {
			if(db.resultado.next()) {
				return db.resultado.getFloat("monto");
			}else {
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public static DaoDepartamento getInstancia() {
		return dataBase;
	}
	
	public List<Departamento> getListado(){
		return listado;
	}
}
