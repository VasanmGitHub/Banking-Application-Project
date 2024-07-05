package Banking_System;

import java.sql.*;
import java.util.*;
public class user {
	static Scanner scan = new Scanner(System.in);
	static Random random = new Random();
	 String ACNo = "321100";
	static int ACNoGenerate = random.nextInt(1000 , 9999);
	String name;
	long phoneNo;																		
	int PIN;																		
	String branch_Name;																											
	String ifsc_Code;
	final String bank_Name;
	int ACBalance;
	 
	user(String name , long phoneNo , int PIN , String branch_Name ,String ifsc_Code){
		ACNo =  ifsc_Code.substring(7) + ACNo + ACNoGenerate;  		
		this.name = name; 
		this.phoneNo = phoneNo;
		this.PIN = PIN; 
		this.ifsc_Code = ifsc_Code; 
		this.branch_Name = branch_Name; 
		this.bank_Name = "UCO";  
		ACBalance = 500; 
	}                   
						// Get Information from user
	public static user getUserInfo() throws SQLException {
		System.out.println("Enter your Name :"); // name
		String name = scan.next();
		System.out.println("Enter your phone No :"); // phone number
		long phoneNo = scan.nextLong();
		System.out.println("Set you 4 digit secret PIN : ( Dont forget it and Dont share to anyone )\nNote : your PIN must have 4 digit");
		int PIN = scan.nextInt(); // PIN
		System.out.println("Select Branch :"); // Select Branch
		 System.out.println("1 . Trichy");
		 System.out.println("2 . Chennai");
		 System.out.println("3 . Jeyakondam");
		 System.out.println("4 . Ariyalur");
	 	int i = scan.nextInt();
		 		
	 	String query = "select branch_Name , ifsc_Code from branchList where branch_id = "+i+";";
				 
 		Connection con = DBConnection.getConnection(); // Get Connection from database 
 		Statement st = con.createStatement();
 		ResultSet res = st.executeQuery(query);
 		res.next(); 	 
 		String branch_Name = res.getString(1);
 		String ifsc_Code = res.getString(2);
		return new user(name, phoneNo, PIN, branch_Name ,ifsc_Code);
	}
}
			