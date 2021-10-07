package Proyecto;

import Proyecto.models.*;
import java.sql.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


public class BaseJava {
	private String driver;
	private String url;
	private String username;
	private String password;
	private String name_bd;
/**
*Asigna los atributos de nuestra base de datos a los atributos de la clase,
*Estos datos los encuentra en un archivo llamado "database.properties"
*@throws IOException cuando el archivo no es encontrado
*@param path El camino donde esta el archivo database.properties, si esta en el mismo directorio, es vacio
*/
	public BaseJava(String path) throws IOException{
		Properties dbProps = new Properties();
		try{
			InputStream input = new FileInputStream(path);
			dbProps.load(input);
		}
		catch(IOException e){
			throw new IOException(e);
		}
		driver = dbProps.getProperty("driver");
		url = dbProps.getProperty("url");
		username = dbProps.getProperty("username");
		password = dbProps.getProperty("password");
		name_bd = dbProps.getProperty("name_database");
	}

/**
*Se conecta a la base de datos
*@throws SQLException si nuestra base de datos no existe o nuestro usuario o clave son incorrectos
*@throws ClassNotFoundException si el driver no es encontrado
*@return La conexion con la base de datos
*/
  public Connection setUpConnection() throws SQLException,ClassNotFoundException{
  	try{
  			// Load database driver if not already loaded.
   			Class.forName(this.driver);
  			Connection connection =	DriverManager.getConnection(this.url,this.username,this.password);
			connection.setAutoCommit(false);
  			return connection;
  			
      }
      catch(ClassNotFoundException cnfe) {
    	       cnfe.printStackTrace();
				throw new ClassNotFoundException();
      }
      catch(SQLException e){
    	  e.printStackTrace();
      	throw new SQLException(e);
      }
	
  }

  public Database crearBaseDatos(Connection connection) throws SQLException {
	  DatabaseMetaData metaData = connection.getMetaData();
		 String[] tipo = {"TABLE"};
	  Database database = new Database(this.name_bd);
	  ResultSet resultSetTables = metaData.getTables(null,this.name_bd, null, tipo);
	  while(resultSetTables.next()) {
		  String nombreTable =resultSetTables.getString(3);
		  Table table = new Table();
		  table.setName(nombreTable);
		  ResultSet rs = metaData.getColumns(null,null, nombreTable, null);
		  String colummName ="";
		  String colummType ="";
		  while (rs.next()) {
	    	 Column columna = new Column();
             colummName = rs.getString("COLUMN_NAME");
             colummType = rs.getString("COLUMN_TYPE");
             columna.setName(colummName);
             columna.setName(colummType);
             table.addColumn(columna);
		  }
		 
		  /*select f.constraint_name,c.constraint_type,COLUMN_NAME,REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME from information_schema.KEY_COLUMN_USAGE f 
		   * inner join (select constraint_name,constraint_type from information_schema.table_constraints) as c on c.constraint_name = f.constraint_name 
		   * where f.TABLE_NAME = 'ITEM_FACTURA'*/
		  String query = " select constraint_name,constraint_type from information_schema.table_constraints where table_name = '"+nombreTable;   
		  ResultSet res = connection.createStatement().executeQuery(query);
		  while(res.next()){
			 Key key = new Key();
			 key.setKeyName(res.getString("constraint_name"));
			 if (res.getString("constraint_type").equals("FOREIGN KEY")) {
				 key.setKeyType(2);
				 query = " select COLUMN_NAME,REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME from"
				 		+ " information_schema.KEY_COLUMN_USAGE where CONSTRAINT_NAME = '"+key.getKeyName()+"' AND table_name = '"+nombreTable;
				 ResultSet res2 = connection.createStatement().executeQuery(query);
				 key.setColumnAssoc(res2.getString("COLUMN_NAME"));
				 key.referenceTo(res2.getString("REFERENCED_COLUMN_NAME "), res2.getString("REFERENCED_TABLE_NAME"));
	    		 	 
	    	 }
	    	 if (res.getString("constraint_type").equals("PRIMARY KEY")) {
	    		key.setKeyType(1);
	    		 query = " select COLUMN_NAME from  information_schema.KEY_COLUMN_USAGE where CONSTRAINT_NAME = '"+key.getKeyName()+"' AND table_name = '"+nombreTable;
				 ResultSet res2 = connection.createStatement().executeQuery(query);
				 key.setColumnAssoc(res2.getString("COLUMN_NAME"));
	    	 }
	    	 if (res.getString("constraint_type").equals("UNIQUE KEY")) {
		    	 key.setKeyType(3); 
		    	 query = " select COLUMN_NAME from  information_schema.KEY_COLUMN_USAGE where CONSTRAINT_NAME = '"+key.getKeyName()+"' AND table_name = '"+nombreTable;
				 ResultSet res2 = connection.createStatement().executeQuery(query);
				 key.setColumnAssoc(res2.getString("COLUMN_NAME"));
	    	 }	
		  table.addKey(key);
		  }
		  //FALTAN LOS TRIGGERS Y PROCEDURES
		  //********************************
		  database.addTable(table);
	  }
	  
	 return database; 
  }
	 

}
