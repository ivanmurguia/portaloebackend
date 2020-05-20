package recursos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import conexion.ConexionBD;
import dao.DaoProducto;

@Path("/fotografiasb2c")
public class Fotografias {
	@POST
	@Path("/fotografiasFTP")
	public String enviarFTP(String producto) {
		ConexionBD db = new ConexionBD();
		DaoProducto daoproducto = new DaoProducto();
		
		JSONObject respuesta = new JSONObject();
		
		String nombreNuevo = "";
		
		db.ejecutar("insert into fotografias_log (sku,[log],comment,date) values ('"+producto+"','process','Se va a procesar el producto,getdate())");
		
		if(daoproducto.cambioSKU(producto).length() > 7) {
			nombreNuevo = daoproducto.cambioSKU(producto).substring(0,7);
		} else {
			nombreNuevo = daoproducto.cambioSKU(producto);
		}
		
		db.ejecutar("insert into fotografias_log (sku,[log],comment,date) values ('"+producto+"','process','El nombre del archivo es: "+nombreNuevo+"',getdate())");
		
		File foto = new File("\\\\192.168.1.34\\FTP_Fotografias\\"+producto,producto+"_1.jpg");
		File fotocopiauaux = new File("\\\\192.168.1.19\\Comun\\FotografiasProcesadas\\"+nombreNuevo+"AAAA_1.jpg");
		
		if(foto.exists()) {
			try {
				respuesta.put("foto", foto.getPath().replace("\\\\192.168.1.34\\FTP_Fotografias\\", "http:\\\\201.163.87.236\\"));
				if(!fotocopiauaux.exists()) {
					db.ejecutar("insert into fotografias_log (sku,[log],comment,date) values ('"+producto+"','process','Se va a copiar la foto',getdate())");
					File fotocopia = new File("\\\\192.168.1.19\\Comun\\FotografiasProcesadas\\"+nombreNuevo+"AAAA_1.jpg");
					InputStream in = new FileInputStream(foto);
					OutputStream out = new FileOutputStream(fotocopia);
					
					byte[] buf = new byte[1024];
					int len;
					
					while ((len = in.read(buf)) > 0) {
					  out.write(buf, 0, len);
					}
					
					in.close();
					out.close();
					
					respuesta.put("mensaje", "Se copio con exito");
					db.ejecutar("insert into fotografias_log (sku,[log],comment,date) values ('"+producto+"','success','La copia se genero con exito',getdate())");
					
				}else {
					db.ejecutar("insert into fotografias_log (sku,[log],comment,date) values ('"+producto+"','warning','Ya existia esta foto en la carpeta Fotografias Procesadas',getdate())");
					respuesta.put("mensaje", "Ya existia la copia de la foto");
					respuesta.put("fotocopia", foto.getPath().replace("\\\\192.168.1.34\\FTP_Fotografias\\", "http:\\\\201.163.87.236\\"));
				}
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			try {
				respuesta.put("error", "No existe la foto");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return respuesta.toString();
	}
	
	public void uploadToFTP() {
        String server = " ftp.wcaas.com ";
        int port = 5539;
        String user = "cloeb2bmx";
        String pass = "BBXXaEz@Ved)*8Bv";
        
        FTPClient ftpClient = new FTPClient();

        try {
			ftpClient.connect(server, port);
			
			if(ftpClient.isConnected()) {
				ftpClient.login(user, pass);
				
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@POST
	public String copiarFotografias(String productos) {
		ConexionBD db = new ConexionBD();
		DaoProducto daoproducto = new DaoProducto();
		JSONArray respuesta = new JSONArray();
		try {
			JSONArray lista = new JSONArray(productos);
			
			for(int i = 0; i < lista.length(); i++) {
				JSONObject producto = new JSONObject();
				JSONObject prod = lista.getJSONObject(i);
				String nombre = prod.get("Producto").toString();
				String nombreNuevo = "";
				
				db.ejecutar("insert into fotografias_log (sku,[log],comment,date) values ('"+nombre+"','process','Se va a procesar el producto #"+(i+1)+"',getdate())");
				
				if(daoproducto.cambioSKU(nombre).length() > 7) {
					nombreNuevo = daoproducto.cambioSKU(nombre).substring(0,7);
				} else {
					nombreNuevo = daoproducto.cambioSKU(nombre);
				}
				
				db.ejecutar("insert into fotografias_log (sku,[log],comment,date) values ('"+nombre+"','process','El nombre del archivo es: "+nombreNuevo+"',getdate())");
				
				System.out.println("nombreNuevo: " + nombreNuevo);
				producto.put("nombre", nombre);
				File foto = new File("\\\\192.168.1.34\\FTP_Fotografias\\"+nombre,nombre+"_1.jpg");
				File fotocopiauaux = new File("\\\\192.168.1.19\\Comun\\FotografiasProcesadas\\"+nombreNuevo+"AAAA_1.jpg");
//				File carpetaNueva = new File("\\\\192.168.1.19\\Comun\\FotografiasProcesadas\\"+nombre);
				if(foto.exists()) {
					producto.put("foto", foto.getPath().replace("\\\\192.168.1.34\\FTP_Fotografias\\", "http:\\\\201.163.87.236\\"));
					if(!fotocopiauaux.exists()) {
						db.ejecutar("insert into fotografias_log (sku,[log],comment,date) values ('"+nombre+"','process','Se va a copiar la foto',getdate())");
						File fotocopia = new File("\\\\192.168.1.19\\Comun\\FotografiasProcesadas\\"+nombreNuevo+"AAAA_1.jpg");
						InputStream in = new FileInputStream(foto);
						OutputStream out = new FileOutputStream(fotocopia);
						
						byte[] buf = new byte[1024];
						int len;
						
						while ((len = in.read(buf)) > 0) {
						  out.write(buf, 0, len);
						}
						
						in.close();
						out.close();
						producto.put("fotocopia", fotocopia.getPath().replace("\\\\192.168.1.19\\Comun\\FotografiasProcesadas\\", "http:\\\\201.163.87.236\\"));
						
						producto.put("mensaje", "Se copio con exito");
						
						db.ejecutar("insert into fotografias_log (sku,[log],comment,date) values ('"+nombre+"','success','La copia se genero con exito',getdate())");
					}else {
						db.ejecutar("insert into fotografias_log (sku,[log],comment,date) values ('"+nombre+"','warning','Ya existia esta foto en la carpeta Fotografias Procesadas',getdate())");
						producto.put("mensaje", "Ya existia la copia de la foto");
						producto.put("fotocopia", foto.getPath().replace("\\\\192.168.1.34\\FTP_Fotografias\\", "http:\\\\201.163.87.236\\"));
					}
				} else {
					db.ejecutar("insert into fotografias_log (sku,[log],comment,date) values ('"+nombre+"','error','No existe la foto en el FTP',getdate())");
					producto.put("mensaje", "No existe la foto");
				}
				respuesta.put(producto);
			}
			return respuesta.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}



