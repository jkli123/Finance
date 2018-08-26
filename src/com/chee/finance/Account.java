package com.chee.finance;

import java.io.*;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * A concrete class that holds the different accounts that store value
 * Holds three accounts, Posb, Cimb and Cash account held in an ArrayList
 * All methods adding and subtracting from account is static and only one
 * ArrayList is initialised
 * 
 * @author Chee Peng
 * @version 1.00
 * @since 19/5/2017
 *
 */
public class Account implements Serializable {
	
	private String accountName;
	private double amount;
	public static ArrayList<Account> banksList = new ArrayList<Account>();
	
	/**
	 * Initialise a Account object holding a accountName and the amount in it
	 * 
	 * @param accountName Name of the account
	 * @param amount Amount of money present in the account
	 */
	public Account(String accountName, double amount) {
		this.accountName = accountName;
		this.amount = amount;
	}
	
	/**
	 * An empty constructor to initialise the Account class and allow ArrayList of account
	 * to be initialised
	 */
	public Account() {
		
	}
	
	/**
	 * Displays the accountName and amount in format "accountName , amount"
	 * 
	 * @see Object.toString()
	 */
	@Override
	public String toString() {
		return String.format(accountName + " , " + amount);
	}

	/**
	 * Sets the amount of money in the account
	 * 
	 * @param amount Amount to set the account to
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * Gets the name of the account 
	 *  
	 * @return Name of the account
	 */
	public String getAccountName() {
		return accountName;
	}
	
	/**
	 * Sets the name of the account
	 * 
	 * @param accountName Name of the account to set the account to
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	/**
	 * Gets the amount of money in the account
	 * 
	 * @return Amount in the account
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * Called by the Finance class in initialising the applet when it starts up
	 * Calls the readAccountFile() method in BankIO class to store the 3 accounts
	 * into the class variable ArrayList
	 * 
	 * @see Finance, BankIO.readAccountFile()
	 */
	public void initialise() {
		BankIO.readAccountFile();
	}
	
	/**
	 * Adds a income to the account specified in the variable type of income 
	 * Amount added to the account is formatted up to 2 decimal places padded with zeroes
	 * This prevents any overflow/underflow of the amount that will be displayed on the 
	 * Finance GUI. Method is exactly the same as addExpense except this is adding income
	 * 
	 * @param income The income object that holds relevant data that needs to be added to the account
	 * @see Account.addExpense
	 */
	public static void addIncome(Income income) {
		String bank = income.getType();
		if(bank.equals("Posb")) {
			double initial = banksList.get(0).getAmount();
			double added = initial + income.getAmount();
			String addString = String.format("%.02f", added);
			added = Double.parseDouble(addString);
			banksList.get(0).setAmount(added);
		}
		
		if(bank.equals("Cimb")) {
			double initial = banksList.get(1).getAmount();
			double added = initial + income.getAmount();
			String addString = String.format("%.02f", added);
			added = Double.parseDouble(addString);
			banksList.get(1).setAmount(added);
		}
		
		if(bank.equals("Cash")) {
			double initial = banksList.get(2).getAmount();
			double added = initial + income.getAmount();
			String addString = String.format("%.02f", added);
			added = Double.parseDouble(addString);
			banksList.get(2).setAmount(added);
		}
	}
	
	/**
	 * Adds a expense to the account specified by the variable type in the expense object
	 * Amount added to account is formatted to 2 decimal places padded with zeroes
	 * This prevents any overflow/underflow of the amount that will be displayed on the
	 * Finance GUI. Method is exactly the same as addIncome except this is adding expense
	 * 
	 * @param expense The expense object that holds relevant data that needs to be added to the account
	 * @see Account.addIncome
	 */
	public static void addExpense(Expense expense) {
		String bank = expense.getType();
		if(bank.equals("Posb")) {
			double initial = banksList.get(0).getAmount();
			double subtracted = initial - expense.getAmount();
			String subtractString = String.format("%.02f", subtracted);
			subtracted = Double.parseDouble(subtractString);
			banksList.get(0).setAmount(subtracted);
		}
		
		if(bank.equals("Cimb")) {
			double initial = banksList.get(1).getAmount();
			double subtracted = initial - expense.getAmount();
			String subtractString = String.format("%.02f", subtracted);
			subtracted = Double.parseDouble(subtractString);
			banksList.get(1).setAmount(subtracted);
		}
		
		if(bank.equals("Cash")) {
			double initial = banksList.get(2).getAmount();
			double subtracted = initial - expense.getAmount();
			String subtractString = String.format("%.02f", subtracted);
			subtracted = Double.parseDouble(subtractString);
			banksList.get(2).setAmount(subtracted);
		}
	}
	
	/**
	 * Adds a transfer from an account to another as specified by the variable from and to in the transfer object
	 * Amount added to account is formatted to 2 decimal places as specified in addExpense and addIncome
	 * Method calls addExpense on the account to transfer from
	 * while method calls addIncome method on the account to transfer to
	 * 
	 * @param transfer The transfer object that holds relevant data and which account to transfer from and to
	 * @see Account.addIncome, Account.addExpense
	 */
	public static void addTransfer(Transfer transfer) {
		Expense transferFrom = new Expense(transfer.getDate(), transfer.getFrom(), 
				transfer.getTo(), transfer.getAmount());
		addExpense(transferFrom);
		Income transferTo = new Income(transfer.getFrom(), transfer.getDate(), 
				transfer.getTo(), transfer.getAmount());
		addIncome(transferTo);
	}
}
