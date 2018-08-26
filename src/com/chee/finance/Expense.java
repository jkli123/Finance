package com.chee.finance;

import java.io.Serializable;

/**
 * A concrete class that holds data for expenses transactions once initialised
 * Holds date, type, name and amount required for the transaction
 * 
 * 
 * @author Chee Peng
 * @version 1.00
 * @since 17/5/2017
 */
public class Expense implements Serializable {
	
	private String date;
	private String type;
	private String name;
	private double amount;
	
	/**
	 * Initialises a expense transaction holding all relevant details
	 * required including date, type, name and amount.
	 * 
	 * @param date Date in which transaction happens
	 * @param type Account to deduct amount from
	 * @param name Name of the transaction
	 * @param amount Amount to be deducted
	 */
	public Expense(String date, String type, String name, double amount) {
		this.date = date;
		this.type = type;
		this.name = name;
		this.amount = amount;
	}
	
	/**
	 * Gets the date of the in which that expense happened
	 * 
	 * @return Date in which the expense happened
	 */
	public String getDate() {
		return date;
	}
	

	/**
	 * Gets the type of the expense happened
	 * 
	 * @return "Cimb", or "Posb", or "Cash" depending on type of the expense
	 */
	public String getType() {
		return type;
	}
	

	/**
	 * Gets the name of the expense happened
	 * 
	 * @return Name of the expense
	 */
	public String getName() {
		return name;
	}
	

	/**
	 * Gets the amount of the expense
	 * 
	 * @return Amount of expense
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * Sets the date of the expense
	 * 
	 * @param date The date to set the variable date to.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * Sets the type of expense
	 * 
	 * @param type The type to set the variable type to.
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Sets the name of the expense
	 * 
	 * @param name The name to set the variable name to.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the amount of the expense
	 * 
	 * @param amount The amount to set the variable amount to.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	

}
