package com.project.bank;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class HomePage {
	
	//ABSTRACTION
	abstract void checkBalance(ResultSet rc);
	abstract void withdrawAmount(ResultSet rw);
	abstract void depositAmount(ResultSet rd);
	
	//PUBLIC STATIC - Available for entire application
	public static Scanner scan=new Scanner(System.in);
	static Connection con=DatabaseConnection.databaseConnect();
	
	//Method to open a new account
	public static void openAccount()
	{
		System.out.println("********************************Account Opening Portal********************************");
		System.out.println("\nEnter your name:");
		String name=scan.nextLine();
		System.out.println("\nEnter PIN you want to set:");
		String password=scan.nextLine();
		Random random=new Random();
		long account_number;
		account_number=Math.abs(random.nextLong());
		try {
			if(con==null)
			{
				//Custom Exception thrown
				throw new BankingExceptions("There might be some error in connection, Please try again");
			}
			java.sql.Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from users");
			while(rs.next())
			{
				if(rs.getString(1).equals(String.valueOf(account_number)))
				{
					account_number=Math.abs(random.nextLong());
				}
			}
			final String sqlas="INSERT INTO users values(?,?,?,?)";
			PreparedStatement statement=con.prepareStatement(sqlas);
			statement.setLong(1, account_number);
			statement.setString(2, name);
			statement.setString(3, password);
			statement.setLong(4, 0);
			statement.executeUpdate();
			System.out.println("\nAccount created successfully");
			System.out.println("\nYour account no is: "+account_number);
			System.out.println("Note: Please note down your account number immediately");
			
		} catch (SQLException e) {
			System.out.println("Something went wrong! Please try again");
		}
		catch(InputMismatchException e)
		{
			System.out.println("\nPlease enter details in correct format");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	//Method to login into the application
	public static void loginAccount()
	{
		
		try 
		{
			System.out.println("\nEnter your Account Number");
			long account_no=scan.nextLong();
			System.out.println("\nEnter your PIN");
			String pin=scan.next();
			PreparedStatement pstmt = con.prepareStatement("select * from users where account_number=? and password=?");
			pstmt.setLong(1, account_no);
			pstmt.setString(2, pin);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{
				System.out.println("\nLogin Successfully! Welcome to AMDOCS BANKING SERVICES");
				while(true)
				{
				try
				{
				System.out.println("\n1. Check account balance");
				System.out.println("2. Withdraw the amount");
				System.out.println("3. Deposit the amount");
				System.out.println("4. Logout");
				System.out.println("\nPlease select given option ");
				int input=scan.nextInt();
				scan.nextLine();//to bring cursor to the next line
				System.out.println();
				HomePage obj=new BankingFunctions();
				 switch(input)
					{
						case 1 : obj.checkBalance(rs);break;
						case 2 : obj.withdrawAmount(rs);break;
						case 3 : obj.depositAmount(rs);break;
						case 4 : System.out.println("Thank you! Visit us again anytime");
							     System.exit(0);
						default : System.out.println("Wrong Input");
					}
			}
				catch(InputMismatchException e)
				{
					System.out.println("Only numbers allowed ");
					scan.next();//clear scanner wrong input
				}
				catch(Exception e)
				{
					System.out.println("Please try again some error occured!");
					scan.next();//clear scanner wrong input
				}
			}
			}
			else
			{
			    throw new BankingExceptions("Invalid credentials! Please check your credentials and try again");
			}
		}
		catch(InputMismatchException e)
		{
			System.out.println("\nPlease enter details in correct format");
			scan.next();
		}
		catch(Exception e){
			System.out.println("\nError occurred: " + e.getMessage());
		}
		 
	}

	
	public static void main(String[] args) {
	
	System.out.println("******************************************AMDOCS BANKING******************************************");
	System.out.println("\nWelcome to AMDOCS BANKING facility ");
	
	while(true)
	{
		try
		{
			System.out.println("\n1. Open a new account");
			System.out.println("2. Login to your account");
			System.out.println("3. Exit");
			System.out.println("\nPlease select given option ");
			int input=scan.nextInt();
			scan.nextLine();//to bring cursor to the next line
			
			 switch(input)
				{
					case 1 : HomePage.openAccount();break;
					case 2 : HomePage.loginAccount();break;
					case 3 : System.out.println("\nThank you! Visit us again anytime");
						     System.exit(0);
					default : System.out.println("\nPlease select given options only(1,2,3)");
				}
		}
		catch(InputMismatchException e)
		{
			System.out.println("\nOnly numbers allowed! Please select given options only(1,2,3)");
			scan.next();//clear scanner wrong input
		}
		catch(Exception e)
		{
			System.out.println("Please try again some error occured!");
			System.exit(0);
			//scan.next();//clear scanner wrong input
		}
	 
	}
	}

}
