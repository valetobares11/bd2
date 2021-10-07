package Proyecto;

import java.sql.*;
import java.util.Scanner;
import java.io.IOException;
public class Main{
	public static String PATH_BD1 = "../Base_De_Datos/database.properties.mysql1";
	public static String PATH_BD2 = "../Base_De_Datos/database.properties.mysql2";
	public static void main(String[] args){
		try{
	   
  			BaseJava base = new BaseJava(PATH_BD1);
  			BaseJava base2 = new BaseJava(PATH_BD2);
  			
  			Connection connection= base.setUpConnection();
  			//ACA SE COMPARARIAN LAS DOS BD 
       		
     	}
     	catch(SQLException e){
     		System.err.println(""+e);
				System.exit(1);
     	}
     	catch(IOException ex){
     		System.err.println(""+ex);
				System.exit(1);
     	}
			catch(ClassNotFoundException cnfe){
				System.err.println("Error loading driver: " + cnfe);
				System.exit(1);
			}
    }
}
