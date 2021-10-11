package Proyecto;

import Proyecto.models.*;
import java.sql.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;


public class BaseJava {
	private String driver;
	private String url;
	private String username;
	private String password;
	private String name_bd;
	private static final String INITIALIZATION_STRING  = "";
	private static final String DRIVER  = "driver";
	private static final String URL  = "url";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String NOMBRE_DATABASE = "name_database";
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
		driver = dbProps.getProperty(DRIVER);
		url = dbProps.getProperty(URL);
		username = dbProps.getProperty(USERNAME);
		password = dbProps.getProperty(PASSWORD);
		name_bd = dbProps.getProperty(NOMBRE_DATABASE);
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
  /**
  *Crea la Estrutura de la Base de Datos
  *@throws SQLException si nuestra consulta es erronea o falla alguna funcion de metadata
  *@throws Connection para capturar los datos de nuestra bd
  *@return La base de datos
  */
  public Database crearBaseDatos(Connection connection) throws SQLException {
	  DatabaseMetaData metaData = connection.getMetaData();
	  Database database = new Database(this.name_bd);
	  ResultSet resultSetTables = metaData.getTables(this.name_bd,null, null,  new String[]{"TABLE"});
	  String query = INITIALIZATION_STRING;
	  String nombreTable = INITIALIZATION_STRING;
	  String colummName = INITIALIZATION_STRING;
	  String colummType = INITIALIZATION_STRING;
	  while(resultSetTables.next()) {
		  nombreTable =resultSetTables.getString(3);
		  Table table = new Table();
		  table.setName(nombreTable);
		  ResultSet result_columns_table = metaData.getColumns(null,null, nombreTable, null);
		  while (result_columns_table.next()) {
	    	 Column columna = new Column();
             colummName = result_columns_table.getString("COLUMN_NAME");
             colummType = result_columns_table.getString("TYPE_NAME");
             columna.setName(colummName);
             columna.setType(colummType);
             table.addColumn(columna);
		  }
		 
		  query= "select CONSTRAINT_NAME,CONSTRAINT_TYPE,COLUMN_NAME,REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME from information_schema.KEY_COLUMN_USAGE NATURAL JOIN "
		  		+ "information_schema.table_constraints where table_name = '"+nombreTable+"'";  
		  ResultSet result_key_table = connection.createStatement().executeQuery(query);
		  while(result_key_table.next()){
			 Key key = new Key();
			 key.setKeyName(result_key_table.getString("CONSTRAINT_NAME"));
			 if (result_key_table.getString("CONSTRAINT_TYPE").equals("FOREIGN KEY")) {
				 key.setKeyType(2);
				 key.setColumnAssoc(result_key_table.getString("COLUMN_NAME"));
				 key.referenceTo(result_key_table.getString("REFERENCED_COLUMN_NAME"), result_key_table.getString("REFERENCED_TABLE_NAME"));
	    		 	 
	    	 }
	    	 if (result_key_table.getString("CONSTRAINT_TYPE").equals("PRIMARY KEY")) {
	    		 key.setKeyType(1);
				 key.setColumnAssoc(result_key_table.getString("COLUMN_NAME"));
	    	 }
	    	 if (result_key_table.getString("CONSTRAINT_TYPE").equals("UNIQUE KEY")) {
		    	 key.setKeyType(3); 
		    	 key.setColumnAssoc(result_key_table.getString("COLUMN_NAME"));
	    	 }	
		  table.addKey(key);
		  }
		 
		  query = "SELECT * FROM information_schema.TRIGGERS WHERE trigger_schema = '"+this.name_bd+"' "
		  		+ "AND EVENT_OBJECT_TABLE = '"+nombreTable+"'";
		  ResultSet resutl_trigger_table = connection.createStatement().executeQuery(query);
		  while(resutl_trigger_table.next()){
			  Trigger tigger = new Trigger(resutl_trigger_table.getString("TRIGGER_NAME"),
					  resutl_trigger_table.getString("ACTION_TIMING"),resutl_trigger_table.getString("EVENT_MANIPULATION"));
			  table.addTrigger(tigger);
		  }
		  
		  database.addTable(table);
	  }
	  
	  ResultSet resultSetProcedure = metaData.getProcedures(connection.getCatalog(), null, null);
		Procedure procedure;
		while (resultSetProcedure.next()) {
			procedure = new Procedure(resultSetProcedure.getString("PROCEDURE_NAME"),resultSetProcedure.getString("PROCEDURE_TYPE"));
			ResultSet pp = metaData.getProcedureColumns(connection.getCatalog(), connection.getSchema(), procedure.getName(), null);
			List<String[]> list_parameters = procedure.getParameters();
			while (pp.next()) {
				list_parameters.add(new String[]{pp.getString("COLUMN_NAME"),pp.getString("COLUMN_TYPE")});
			}
			procedure.setParameters(list_parameters);
			database.addProcedure(procedure);
		}
		
	 return database; 
  }
	 

}