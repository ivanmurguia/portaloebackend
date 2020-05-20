package dao;

import java.util.ArrayList;
import java.util.List;

import modelo.Articulo;

public final class DataBase {
	private final static DataBase dataBase = new DataBase();
	private final List<Articulo> listado = new ArrayList<>();
	
	private DataBase() {
		Articulo a1 = new Articulo(1,"primer articulo", "primer contenido","yordy" );
		Articulo a2 = new Articulo(2,"segundo articulo", "segundo contenido","yordy2" );
		Articulo a3 = new Articulo(3,"tercer articulo", "segundo contenido","yordy2" );
		listado.add(a1);
		listado.add(a2);
		listado.add(a3);
	}
	
	public static DataBase getInstancia() {
		return dataBase;
	}
	
	public List<Articulo> getListado(){
		return listado;
	}
}
