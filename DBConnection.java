package Banking_System;
import java.sql.*;
public class DBConnection {
	private static final String url = "jdbc:mysql://localhost:3306/Bank_Application_Datas";
	private static final String name = "root";
	private static final String password= "vasanroja@333";
	
	public static Connection getConnection() throws SQLException { // Get Connection
		 
		Connection con = DriverManager.getConnection(url , name , password);		
		return con;
	}
	
	
}
