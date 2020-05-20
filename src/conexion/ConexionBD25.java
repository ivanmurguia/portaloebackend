package conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD25 {
	   private static Connection con=null;
	   public static Connection getConnection(){
			try {
		         if( con == null || con.isClosed()){
		        	Properties properties = new Properties();
	 				try {
						properties.load(ConexionBD25.class.getResourceAsStream("/resources/config.properties"));
						
						System.out.println(properties.getProperty("databaseName"));
						
						System.out.println("Conectado a la base de datos " + properties.getProperty("databaseName"));
						
			            String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			            String url="jdbc:sqlserver://192.168.1.56;databaseName="+properties.getProperty("databaseName")+";";
			            String pwd="B1Admin";
			            String usr="sa";
		            
					Class.forName(driver);
					
		            con = java.sql.DriverManager.getConnection(url,usr,pwd);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	         	 } 
	         } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     return con;
	   }
}
