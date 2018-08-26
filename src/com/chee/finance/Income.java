package com.chee.finance;

import java.io.Serializable;
/**
 * A concrete class to hold the income transactions that are
 * being made on an account. Once initialised, holds the
 * source, date, type and amount.
 * 
 * @author Chee Peng
 * @version 1.00
 * @since 17/5/2017
 */
public class Income implements Serializable{
	
	private String source;
	private String date;
	private String type;
	private double amount;

	/**
	 * Initialises a income transaction holding all relevant details
	 * required including source, date, type and amount
	 * 
	 * @param source The place the income came from
	 * @param date Date in which the transaction happens
	 * @param type Account to add income to
	 * @param amount Amount to be added
	 */
	public Income(String source, String date, String type, double amount) {
		this.source = source;
		this.date = date;
		this.type = type;
		this.amount = amount;
	}

	/**
	 * Gets the amount of the income
	 * 
	 * @return Amount of income
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * Gets the type of the income happened
	 * 
	 * @return "Cimb", or "Posb", or "Cash" depending on type of the expense
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the date in which the income happened
	 * 
	 * @return Date in which the income happened
	 */

	public String getDate() {
		return date;
	}
	
	/**
	 * Gets the place where the income came from
	 * 
	 * @return Description of the place the income came from
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * Sets the place where the income came from
	 * 
	 * @param source The source to set the variable source to
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Sets the date of the income 
	 * 
	 * @param date The date to set the variable date to
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Sets the type of expense
	 * 
	 * @param type The type to set the variable type to
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the amount to be added
	 * 
	 * @param amount The amount to set the income to
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
