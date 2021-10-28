package Proyecto;

import java.sql.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import Proyecto.models.Database;
import Proyecto.models.Procedure;
import Proyecto.models.Table;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class Main{
	public static String PATH_BD1 = "Base_De_Datos/database.properties.mysql1";
	public static String PATH_BD2 = "Base_De_Datos/database.properties.mysql2";
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException{
		try{
	   
  			BaseJava base = new BaseJava(PATH_BD1);
  			BaseJava base2 = new BaseJava(PATH_BD2);
  
  			Connection connection= base.setUpConnection();
  			Connection connection2= base2.setUpConnection();
  			
  			Database bd1 = base.crearBaseDatos(connection);
  			Database bd2 = base2.crearBaseDatos(connection2);
  			
  			/*System.out.println("Informacion de la base de datos 1");
  			//Mostramos Base de datos 1;
  			System.out.println("Tablas:  \n");
  			Set<Table> tablas = bd1.getTables();
  			for (Iterator iterator = tablas.iterator(); iterator.hasNext();) {
				Table table = (Table) iterator.next();
				System.out.println(table.toString());
			}
  			System.out.println("Procedimientos:  \n");
  			Set<Procedure> procedimientos = bd1.getProcedures();
  			for (Iterator iterator = procedimientos.iterator(); iterator.hasNext();) {
				Procedure procedure = (Procedure) iterator.next();
				System.out.println(procedure.toString());
			}*/
  			
  			String resultado_informe = bd1.compare(bd2);
  		    BufferedWriter bufferedWriter = null;
  		    FileWriter f1 = null;
  		  try {
  			  f1 = new FileWriter("Resultados/informe");
              bufferedWriter = new BufferedWriter(f1);
              bufferedWriter.write(resultado_informe);
              bufferedWriter.close();
              f1.close();
              
          } catch (IOException e) {
              System.out.println("Exception occurred: " + e.getMessage());

          } 
       		
     	}catch(SQLException e){
     		System.err.println(""+e);
				System.exit(1);
     	
     	}catch(IOException ex){
     		System.err.println(""+ex);
				System.exit(1);
     	
     	}catch(ClassNotFoundException cnfe){
			System.err.println("Error loading driver: " + cnfe);
			System.exit(1);
		}
			
    }
}
