package conexion;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
	  public int numeroColumnas;
	  public Statement sentencia;
	  public ResultSet resultado;
	  
	  public ResultSet consultar(String sql) {
	      try {
	          sentencia = ConexionBD25.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	          resultado = sentencia.executeQuery(sql);
	      } catch (SQLException e) {
	    	  System.out.println("Error Sql: " + e);
	      }
	      return resultado;
	  }
	  public String[] columnas() {
		  try {
			  ResultSetMetaData metaDatos;
			  metaDatos = resultado.getMetaData();
			  numeroColumnas = metaDatos.getColumnCount();
			  String[] columnas = new String[numeroColumnas];
			  for (int i = 0; i < numeroColumnas; i++)
			  {
				  columnas[i] = metaDatos.getColumnLabel(i + 1); 
			  }
			  return columnas;
		  } catch (SQLException e) {
			  return null;
		  }
	  }
	  public int filas() {
		  int filas = 0;
		  try {
			while(resultado.next()) {
				filas++;
			}
		} catch (SQLException e) {

		}
			return filas;
	  }
	  public boolean ejecutar(String sql) {
	      try {
	          sentencia = ConexionBD25.getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	          System.out.println(sql);
	          sentencia.executeUpdate(sql);
	          sentencia.close();
	      } catch (SQLException e) {
	    	  return false;
	      }
	      return true;
	  }
}