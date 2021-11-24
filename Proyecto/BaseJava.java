package Proyecto;

import Proyecto.models.*;


import java.sql.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.lang.reflect.Field;

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
 * @throws IllegalAccessException 
 * @throws IllegalArgumentException 
  */
  public Database crearBaseDatos(Connection connection) throws SQLException, IllegalArgumentException, IllegalAccessException {
	  DatabaseMetaData metaData = connection.getMetaData();
	  Database database = new Database(this.name_bd);
	  ResultSet resultSetTables = metaData.getTables(this.name_bd,null, null,  new String[]{"TABLE"});
	  String query = INITIALIZATION_STRING;
	  String nombreTable = INITIALIZATION_STRING;
	  String colummName = INITIALIZATION_STRING;
	  String colummType = INITIALIZATION_STRING;
	  int colummSize = 0;
	  //Map<Integer, String> jdbcMappings = getAllJdbcTypeNames();
	  
	  while(resultSetTables.next()) {
		  nombreTable =resultSetTables.getString(3);
		  Table table = new Table();
		  table.setName(nombreTable);
		  
		  //ACA LLENAMOS LAS COLUMNAS DE LAS TABLAS
		  ResultSet result_columns_table = metaData.getColumns(this.name_bd,null, nombreTable, null);
		  while (result_columns_table.next()) {
	    	 Column columna = new Column();
             colummName = result_columns_table.getString("COLUMN_NAME");
             colummType = result_columns_table.getString("TYPE_NAME");
             if (colummType.equals("VARCHAR")) {
            	 colummSize = result_columns_table.getInt("COLUMN_SIZE");
            	 columna.setSize(colummSize);
             }
             columna.setName(colummName);
             columna.setType(colummType);
             table.addColumn(columna);
		  }
		

		  ResultSet PK = metaData.getPrimaryKeys(connection.getCatalog(), this.name_bd, table.getName());
		  ResultSet FK = metaData.getImportedKeys(connection.getCatalog(), this.name_bd, table.getName());
	
		  Key k = null;
		  Column column = null;
			while (PK.next()) {
				k = new Key();
				column = new Column();
				k.setKeyType(1);
				column.setName(PK.getString("COLUMN_NAME"));
				column.setType(table.getColumn(column.getName()).getType());
				k.setColumn(column);
				k.setKeyName(PK.getString("PK_NAME"));
				k.setSeqNumber(PK.getInt("KEY_SEQ"));
				table.addKey(k);
			}

			
			while (FK.next()) {
				k = new Key();
				k.setKeyType(2);
				column = new Column();
				column.setName(FK.getString("FKCOLUMN_NAME"));
				column.setType(table.getColumn(column.getName()).getType());
				k.setColumn(column);
				k.setKeyName(FK.getString("FK_NAME"));
				// de que tabla y columna es importada
				k.referenceTo(FK.getString("PKCOLUMN_NAME"), FK.getString("PKTABLE_NAME"));
				table.addKey(k);
			}
			//PARA EXTRAER LAS CLAVES SECUNDARIAS
			query = "select CONSTRAINT_NAME,CONSTRAINT_TYPE,COLUMN_NAME,REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME from "
		+ " information_schema.KEY_COLUMN_USAGE NATURAL JOIN information_schema.table_constraints where table_name = '"+table.getName()+"'";
			ResultSet result_key_secundarias_table = connection.createStatement().executeQuery(query);
			 while(result_key_secundarias_table.next()){
				 if(result_key_secundarias_table.getString("CONSTRAINT_TYPE").equals("UNIQUE")) {
					 k = new Key();
						column = new Column();
						k.setKeyType(3);
						column.setName(result_key_secundarias_table.getString("COLUMN_NAME"));
						column.setType(table.getColumn(column.getName()).getType());
						k.setColumn(column);
						k.setKeyName(result_key_secundarias_table.getString("CONSTRAINT_NAME"));
						table.addKey(k);
				 }	
			  }

		  //ACA LLENAMOS LA TABLA CON SUS TRIGGERS
		  query = "SELECT * FROM information_schema.TRIGGERS WHERE trigger_schema = '"+this.name_bd+"' "
		  		+ "AND EVENT_OBJECT_TABLE = '"+nombreTable+"'";
		  ResultSet resutl_trigger_table = connection.createStatement().executeQuery(query);
		  while(resutl_trigger_table.next()){
			  Trigger tigger = new Trigger(resutl_trigger_table.getString("TRIGGER_NAME"),
					  resutl_trigger_table.getString("ACTION_TIMING"),resutl_trigger_table.getString("EVENT_MANIPULATION"));
			  table.addTrigger(tigger);
		  }
		  
		  //ACA LLENAMOS LA TABLA CON LOS INDICES
		  ResultSet resutl_index_table = metaData.getIndexInfo(connection.getCatalog(), null, table.getName(), false, false);

		  Index index =null;
		  while(resutl_index_table.next()){
			  if (!resutl_index_table.getString("COLUMN_NAME").equals("null")) {
				  index = new Index(resutl_index_table.getString("INDEX_NAME"),resutl_index_table.getString("COLUMN_NAME"));
				  table.addIndex(index);  
			  }
			  
		  }
		  database.addTable(table);
	  }
	  
	
	  ResultSet resultSetProcedure = metaData.getProcedures(connection.getCatalog(), null, null);
	  Procedure procedure;
	 
	  while (resultSetProcedure.next()) {
			
			//int typeName = Integer.valueOf(resultSetProcedure.getString("PROCEDURE_TYPE")).intValue();
			//System.out.println("procedure"+typeName);
			procedure = new Procedure(resultSetProcedure.getString("PROCEDURE_NAME"));
			ResultSet pp = metaData.getProcedureColumns(connection.getCatalog(), connection.getSchema(), procedure.getName(), null);
			List<String[]> l = procedure.getParameters();
			while (pp.next()) {
				l.add(new String[]{pp.getString("COLUMN_NAME"), 
						getTipoParams(Integer.valueOf(pp.getString("COLUMN_TYPE")).intValue()),pp.getString("TYPE_NAME") });
			}
			database.addProcedure(procedure);
		}

	Procedure function; //a las funciones las tratamos como procedimientos tambien
		ResultSet resultSetFunction = metaData.getFunctions(connection.getCatalog(), null, null);
		while (resultSetFunction.next()) {
			//int name = Integer.valueOf(resultSetFunction.getString("FUNCTION_TYPE")).intValue();
			//System.out.println("funciones "+ name);
			function = new Procedure(resultSetFunction.getString("FUNCTION_NAME"));
			ResultSet pp = metaData.getFunctionColumns(connection.getCatalog(), connection.getSchema(), function.getName(), null);
			List<String[]> list_parameters = function.getParameters();
			while (pp.next()) {
				list_parameters.add(new String[]{pp.getString("COLUMN_NAME"), 
						getTipoParams(Integer.valueOf(pp.getString("COLUMN_TYPE")).intValue()),pp.getString("TYPE_NAME") });
			}
			function.setParameters(list_parameters);
			database.addProcedure(function);
		}
	return database; 
  }
	 
  public String getTipoParams(int tipo) {
	  if (tipo == 0) return "UNKNOWN";
	  if (tipo == 1) return "IN";
	  if (tipo == 2) return "INOUT";
	  if (tipo == 3) return "RESULT";
	  if (tipo == 4) return "OUT";
	  if (tipo == 5) return "RETURN";
	  return "Error";
  }
  
 /* public Map<Integer, String> getAllJdbcTypeNames() throws IllegalArgumentException, IllegalAccessException {
      Map<Integer, String> result = new HashMap<Integer, String>();
      for (Field field : Types.class.getFields()) {
          result.put((Integer)field.get(null), field.getName());
      }
      return result;
  }*/
  
}
