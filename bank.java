package Banking_System;

 
import java.util.*;

import java.sql.*;

 

public class bank {
	static Scanner scan = new Scanner(System.in);
	public static void printMainMenu() { // For Print Main Menu
		System.out.println();
		System.out.println("                                               ________________\n                                              |                |");
		System.out.println("                                              |   UCO Bank     |");
		System.out.println("                                              |________________|\n\n");
		
		System.out.println("                         Create Account ----- > 1            2 < ------ Deposite Amount \n");
		System.out.println("                         Withdraw Amount ---- > 3            4 < ------ Check Balance \n");
		System.out.println("                         Transfer Amount ---- > 5            6 < ------ Account Details \n\n");
		System.out.println("                                                 Exit --- > 7\n");
	}
	//-------------------------------------------------------------------------------------------------------------

	public static void CreateAccount() throws SQLException { // create AC
		user currUser = user.getUserInfo();
		//CustomerList.add(currUser);
		String query = "Insert into CustomerList Values (?,?,?,?,?,?,?,?)";
 		Connection con = DBConnection.getConnection(); // Get Connection from database 
		PreparedStatement pst = con.prepareStatement(query);
		
		pst.setString(1, currUser.ACNo);
		pst.setString(2 , currUser.name);
		pst.setLong(3, currUser.phoneNo);
		pst.setInt(4, currUser.PIN);
		pst.setString(5, currUser.ifsc_Code);
		pst.setString(6, currUser.bank_Name);
		pst.setString(7, currUser.branch_Name);
		pst.setInt(8, currUser.ACBalance);
		
		int row = pst.executeUpdate(); 
		if(row > 0) {
			System.out.println("Your Account was succssfully created.\nThank you for using UCO Bank...");
			System.out.println("Name :"+currUser.name);
			System.out.println("A/C No :"+currUser.ACNo);
			System.out.println("IFSC Code : "+currUser.ifsc_Code);
		}
		else
		System.err.println("Server Down! Try Again Later");
		con.close();
	}
	//-------------------------------------------------------------------------------------------------------------
	
	public static void deposit() throws SQLException { // For Deposit
		String ACNo = login();
		if(ACNo.length() < 1) return;
		System.out.println("Enter Amount");
		long amount = scan.nextLong();
		String query = "Select AC_Balance From CustomerList where ACNo = '"+ACNo+"';";
		String query1 = "Update CustomerList set AC_Balance = ? where ACNo = '"+ACNo+"';";
 		Connection con = DBConnection.getConnection(); // Get Connection from database 
		PreparedStatement pst = con.prepareStatement(query1);
		Statement  st = con.createStatement();
		ResultSet res = st.executeQuery(query);
		res.next();
		long currAmount = res.getLong(1);
		pst.setLong(1, amount + currAmount);
		pst.executeUpdate(); 
		System.out.println("Your Transection Complete..."); 
		con.close();
	}	
	//-------------------------------------------------------------------------------------------------------------
	public static void withdraw() throws SQLException {		// For Withdrawal
		String ACNo = login(); 
		if(ACNo.length() < 1) return;
		System.out.println("Enter Amount");
		long amount = scan.nextLong();
		String query = "Select AC_Balance From CustomerList where ACNo = '"+ACNo+"';";
		String query1 = "Update CustomerList set AC_Balance = ? where ACNo = '"+ACNo+"';";

 		Connection con = DBConnection.getConnection(); // Get Connection from database 
		PreparedStatement pst = con.prepareStatement(query1);
		Statement  st = con.createStatement();
		ResultSet res = st.executeQuery(query);
		res.next();
		long currAmount = res.getLong(1);
		while(currAmount < amount) {
			System.err.println("Insufficiant Balance");
			System.out.println("Current Balance Rs : "+res.getLong(1));
			System.out.println("Reduios Amount ---- > 1");
			System.out.println("Cancel ------------ > 2");
			int choise = scan.nextInt();
			if(choise == 1) {
				System.out.println("Enter Amount");
				  amount = scan.nextLong();
			}
			else {
				System.err.println("Transection canceld");
				return;
			}
		}
		pst.setLong(1, currAmount - amount );
		 pst.executeUpdate(); 
		System.out.println("Your Transection Complete...");
		con.close();
	}
	//-------------------------------------------------------------------------------------------------------------
	public static void checkBalance() throws SQLException { // For Check Balance
		String ACNo = login(); 
		if(ACNo.length() < 1) return;
		String query = "Select AC_Balance From CustomerList where ACNo = '"+ACNo+"';";
 		Connection con = DBConnection.getConnection(); // Get Connection from database 
 		Statement  st = con.createStatement();
		ResultSet res = st.executeQuery(query);
		res.next();
		System.out.println("Current Balance Rs : "+res.getLong(1));
		con.close(); 
	}
	//-------------------------------------------------------------------------------------------------------------
	public static void transferAmount() throws SQLException {
		String ACNo = login(); 
		if(ACNo.length() < 1) return;
		boolean bool = false; String ReciveAC = "";
		while(!bool) {
		System.out.println("Transfer by using Acount Number --- > 1");
		System.out.println("Transfer by using Phone Number ---- > 2");
		System.out.println("Cancel Transection ---------------- > 3");
		int choise = scan.nextInt();
		if(choise == 1) { // Bank Transfer
			System.out.println("Enter Reciver Account Number :");
			String ReciverACNo = scan.next();
			if(!checkValidACNo(ReciverACNo)) {
				System.err.println("Reciver Account Not Found");
				System.out.println("Re Enter Reciver Account Number :");
				ReciverACNo = scan.next();
				if(!checkValidACNo(ReciverACNo)) {
					System.err.println("Reciver Account Not Found");
				}
			}
			else {
				ReciveAC = ReciverACNo;
				bool = true;
			}
		}
		else if(choise == 2) { // Phone Transfer
			System.out.println("Enter Reciver Phone Number :");
			long phone = scan.nextLong();
			if(!checkValidACNo(phone)) {
				System.err.println("Reciver Account Not Found");
				System.out.println("Re Enter Reciver Phone Number :");
				phone = scan.nextLong(); 
				if(!checkValidACNo(phone)) {
					System.err.println("Reciver Account Not Found");
				}
			}
			else {
				 String query = "Select AcNo From CustomerList where phone_Number = "+phone+";";
				Connection con = DBConnection.getConnection(); // Get Connection from database 
			 	Statement  st = con.createStatement();
			 	ResultSet res = st.executeQuery(query);
			 	res.next();
			 	ReciveAC = res.getString(1); 
				bool = true;
				con.close(); 
			} 
		}
		else { // cancel transfer
			System.err.println("Transection canceld..."); 
			return;
		}
	}
		System.out.println("Enter Amount");
		long amount = scan.nextLong();		
		String query = "Select AC_Balance From CustomerList where ACNo = '"+ACNo+"';";
		Connection con = DBConnection.getConnection(); // Get Connection from database 
		Statement  st = con.createStatement();
		ResultSet res = st.executeQuery(query);
		res.next();
		long currAmount = res.getLong(1);
		while(currAmount < amount) {
			System.err.println("Insufficiant Balance");
			System.out.println("Current Balance Rs : "+res.getLong(1));
			System.out.println("Reduios Amount ---- > 1");
			System.out.println("Cancel ------------ > 2");
			int choise = scan.nextInt();
			if(choise == 1) {
				System.out.println("Enter Amount");
				  amount = scan.nextLong();
			}
			else {
				System.err.println("Transection canceld");
				return;
			}
		}
		query = "Update CustomerList Set AC_Balance = ? where ACNo = '"+ACNo+"';"; 
		PreparedStatement pst = con.prepareStatement(query); 
		pst.setLong(1, currAmount - amount);
		pst.executeUpdate();
		
		query = "Select AC_Balance From CustomerList where ACNo = '"+ReciveAC+"';"; 
		res = st.executeQuery(query);
		res.next();
		currAmount = res.getLong(1);
		query = "Update CustomerList Set AC_Balance = ? where ACNo = '"+ReciveAC+"';"; 
		pst = con.prepareStatement(query);		
		pst.setLong(1, currAmount + amount);
		pst.executeUpdate();
		con.close(); 
		System.out.println("Transection Complete...");
	}
	//-------------------------------------------------------------------------------------------------------------
	public static void displayDetails() throws SQLException {
		String ACNo = login(); 
		if(ACNo.length() < 1) return;
		String query = "Select * From CustomerList where ACNo = '"+ACNo+"';";
		Connection con = DBConnection.getConnection(); // Get Connection from database 
		Statement  st = con.createStatement();
		ResultSet res = st.executeQuery(query);
		res.next();
		System.out.println("Account Number : "+res.getString(1));
		System.out.println("User Name      : "+res.getString(2));
		System.out.println("Phone Number   : "+res.getLong(3));
		System.out.println("PIN Number     : "+res.getInt(4)); 
		System.out.println("IFSC Code      : "+res.getString(5));
		System.out.println("Bank Name      : "+res.getString(6));
		System.out.println("Branch Name    : "+res.getString(7));
		System.out.println("AC Balance     : "+res.getLong(8));
		con.close();
	}
	//-------------------------------------------------------------------------------------------------------------

	public static String login() throws SQLException {
		String ACNo = "";
		System.out.println("Enter Your Account Number :");
		ACNo = scan.next();
		if(!checkValidACNo(ACNo)) {
			System.err.println("In Valid Account Number");
			System.out.println("ReEnter Your Account Number ");
			ACNo = scan.next();
			while(!checkValidACNo(ACNo)) {
				System.err.println("In Valid Account Number");
				System.out.println("Forget Account Number --- > 1");
				System.out.println("Create Account  --------- > 2");
				System.out.println("Exit -------------------- > 0");
				int choise = scan.nextInt();
				switch(choise) {
				case 1 :
					FindACNo();
					return "";
				case 2 :
					CreateAccount();
					return "";
				case 0 :
					return "";
					default :
						System.err.println("Invalid Option");
				}
			}
			
		}
		
			System.out.println("Enter your PIN :");
			int PIN = scan.nextInt();
			String query = "Select PIN From CustomerList where ACNo = '"+ACNo+"';";
	 		Connection con = DBConnection.getConnection(); // Get Connection from database 
	 		Statement st = con.createStatement();
	 		ResultSet res = st.executeQuery(query);
	 		res.next();
	 		if(PIN != res.getInt(1)) {
	 			System.err.println("In Valid PIN \nTransection Canceld");
	 			return "";
	 		}
		
			 		
		return ACNo;
	}
	//------------------------------------------------------------------------------------------------------------
	public static boolean checkValidACNo(String ACNo) throws SQLException {
		String query = " SELECT COUNT(*) FROM CustomerList where ACNo = '"+ACNo+"';";
 		Connection con = DBConnection.getConnection(); // Get Connection from database 
 		Statement st = con.createStatement();
 		ResultSet res = st.executeQuery(query);
 		res.next();
 		
 		return res.getInt(1) > 0;
 		
	}
	//------------------------------------------------------------------------------------------------------------
		public static boolean checkValidACNo(long phone) throws SQLException {
			String query = " SELECT COUNT(*) FROM CustomerList where phone_Number = "+phone+";";
	 		Connection con = DBConnection.getConnection(); // Get Connection from database 
	 		Statement st = con.createStatement();
	 		ResultSet res = st.executeQuery(query);
	 		res.next();
	 		
	 		return res.getInt(1) > 0;
	 		
		}
	//----------------------------------------------------------------------------------------------------------------
	public static void FindACNo() throws SQLException {
		System.out.println("Enter your Name ");
		String name =  scan.next();
		String query = " select * From CustomerList where User_Name = '"+name+"';";
 		Connection con = DBConnection.getConnection(); // Get Connection from database 
 		Statement st = con.createStatement();
 		
 		ResultSet res = st.executeQuery(query);
 		res.next();
 		
 		
 		if(!res.getString(2).equalsIgnoreCase(name)) { 
 			System.err.println("There is no Account in this Name \nYou Must have to Create Your Account");
 		}
 		else {
 			System.out.println("Enter your Phone Number :");
 			long phone = scan.nextLong();
 			System.out.println("Enter you PIN");
 			int PIN = scan.nextInt();
 			if(phone == res.getLong(3) && PIN == res.getInt(4)) {
 				System.out.println("Your Account Number is : "+res.getString(1));
 			}
 			else {
 				System.err.println("Your Information Not Match \nYou Must Have to go our Branch for face Verification");
 				return;
 			}
 		}
 		
	}
	//----------------------------------------------------------------------------------------------------------------

	
	
	
	
	
	
}
