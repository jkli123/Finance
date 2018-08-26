package com.chee.finance;

/**
 * A concrete class  that will be holding transfer details
 * between banks. Once initialised, it will include all 
 * relevant details, including, date, from, to and amount
 * 
 * @author Chee Peng
 * @version 1.00
 * @since 17/5/2017
 */
public class Transfer {

	private String date;
	private String from;
	private String to;
	private double amount;
	
	/**
	 * Initialises a transfer transaction holding all relevant details
	 * like date of the transaction, the two banks to transfer from and to 
	 * and the amount to transfer
	 * @param date Date of the transfer
	 * @param from Bank the amount comes from
	 * @param to Bank to transfer to amount to
	 * @param amount Amount to transfer
	 */
	public Transfer(String date, String from, String to, double amount) {
		this.date = date;
		this.from = from;
		this.to = to;
		this.amount = amount;
	}
	
	/**
	 * Gets the date of the transfer 
	 * 
	 * @return Date of the transfer
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * Gets the bank to transfer from
	 * 
	 * @return Bank to transfer from
	 */
	public String getFrom() {
		return from;
	}
	
	/**
	 * Gets the bank to transfer to
	 * 
	 * @return Bank to transfer to
	 */
	public String getTo() {
		return to;
	}
	
	/**
	 * Gets the amount to transfer
	 * 
	 * @return Amount to transfer
	 */
	public double getAmount() {
		return amount;
	}
}
