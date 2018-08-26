package com.chee.finance;

/**
 * The main class that is being used to start up the Finance Applet.
 * Initialises the GUI for the applet and loads up the Account details 
 * found in Account.txt file
 * 
 * @see Account.initialise, FinanceGui.initialise
 * @author Chee Peng
 * @version 1.00
 * @since 17/5/2017
 *
 */
public class Finance {

	public static void main(String[] args) {

		Account initialiseAccount = new Account();
		initialiseAccount.initialise();
		FinanceGui buildGui = new FinanceGui();
		buildGui.initialise();
	}

}
