package com.project.bank;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
	static Connection con;
	public static Connection databaseConnect(){

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech";
			 con =DriverManager.getConnection(url,"scott","tiger");
//			if(con != null)
//			{
//				System.out.println("success");
//			}
		} 
		catch (ClassNotFoundException e) {
			System.out.println("Error Occurrred: "+e.getMessage());
		}
		catch(Exception e)
		{
			System.out.println("Some Error Occurred! Please try again");
		}
		return con;
	}
}
