package Banking_System;
import java.sql.SQLException;
import java.util.*;
public class Main {

	public static void main(String[] args) throws SQLException {
		Scanner scan = new Scanner(System.in);
		boolean bool = true;
		
		 
		while(bool) {
			bank.printMainMenu(); 
			int choise = scan.nextInt();
			switch(choise) {
			case 1 :
			{
				bank.CreateAccount();
				break;
			}
			case 2 :
			{
				bank.deposit();
				break;
			}
			case 3 :
			{
				bank.withdraw();
				break;
			}
			case 4 :
			{
				bank.checkBalance();
				break;
			}
			case 5 :
			{
				bank.transferAmount();
				break;
			}
			case 6 :
			{
				bank.displayDetails();
				break;
			}
			case 7 :
			{
				System.out.println("Thank You...");
				bool = false;
				break;
			}
			default :
				System.err.println("InValid Input");
			
			}
			
		}

	}

}
