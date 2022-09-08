package com.project.bank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class BankingFunctions extends HomePage{

	private long amount;
	private long account_number;
	
	
	//Method to check balance
	public void checkBalance(ResultSet rc) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Bank balance fetched successfully: "+rc.getString(4));
			System.out.println("\nThank you! Visit us again anytime");
			System.exit(0);
			
		} catch (Exception e) {
			System.out.println("\nSomething went wrong please try again");
		}
	}
	
	//Method to withdraw amount
	public  void withdrawAmount(ResultSet rw) {
		
		try {
			amount = rw.getLong(4);
			account_number=rw.getLong(1);
			System.out.println("Enter amount you want to withdraw:");
			long withdrawAmount=scan.nextLong();
			if(withdrawAmount>amount)
			{
				throw new BankingExceptions("Insufficient Balance");//custom exception
			}
			else if(withdrawAmount<=0)
			{
				throw new BankingExceptions("Please enter amount in correct fromat");//custom exception
			}
			else
			{
			PreparedStatement updateStatement=con.prepareStatement("UPDATE users set amount=? WHERE account_number=?");
			updateStatement.setLong(1,amount-withdrawAmount);
			updateStatement.setLong(2,account_number);
			updateStatement.executeUpdate();
			System.out.println("\nAmount withrawn successfully");
			System.out.println("Remaining balance: "+(amount-withdrawAmount));
			System.out.println("\nThank you! Visit us again anytime");
			System.exit(0);
			}
		} catch (SQLException e) {
			System.out.println("\nSomething went wrong please try again");
		}
		catch(InputMismatchException e)
		{
			System.out.println("\nPlease enter amount in correct format");
			scan.next();
		}
		catch(Exception e)
		{
			System.out.println();
			System.out.println(e.getMessage());
			System.out.println("Account balance: "+(amount));
			System.out.println("\nThank you! Visit us again anytime");
			System.exit(0);
		}
		
	}
	
	//Method to deposit amount
	public void depositAmount(ResultSet rd) {
		try {
			amount = rd.getLong(4);
			account_number=rd.getLong(1);
			System.out.println("Enter amount you want to deposit:");
			long depositAmount=scan.nextLong();
			PreparedStatement updateStatement=con.prepareStatement("UPDATE users set amount=? where account_number=?");
			updateStatement.setLong(1,amount+depositAmount);
			updateStatement.setLong(2,account_number);
			updateStatement.executeUpdate();
			System.out.println("\nAmount deposited successfully");
			System.out.println("Account balance: "+(amount+depositAmount));
			System.out.println("\nThank you! Visit us again anytime");
			System.exit(0);
		} catch (SQLException e) {
			System.out.println("\nSomething went wrong please try again");
		}
		catch(InputMismatchException e)
		{
			System.out.println("\nPlease enter amount in correct format");
			scan.next();
		}
		
		
	}
}
