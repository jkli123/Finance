package com.chee.finance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A functional class that holds static methods for operating on .txt files that stores data
 * on the expense, income, transfer and account aspects of the Finance applet
 * 
 * @author Chee Peng
 * @version 1.00
 * @since 19/5/2017
 *
 */
public class BankIO {

	/**
	 * A public empty constructor that should not be called.
	 */
	private BankIO() {
		
	}
	
	/**
	 * Use a reading chain that chains a FileReader to a BufferedReader for reading of the Account.txt file
	 * that holds the data on the 3 different types of account
	 * Called by the initialise method in Account when the Finance applet first starts up
	 * 
	 * @see Account.initialise
	 * @throws IOException
	 */
	public static void readAccountFile() {
		try {
			FileReader fr = new FileReader("Account.txt");
			BufferedReader br = new BufferedReader(fr);
			while(br.readLine() !=  null) {	
				String bank = br.readLine();
				String[] banksAndAmount = bank.split("/");
				Account addAccount = new Account(banksAndAmount[0], Double.parseDouble(banksAndAmount[1]));
				Account.banksList.add(addAccount);
			}
			br.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * An overloaded method of the readAccountFile that takes a File object and attempts to read data off the file
	 * The data read from the file is stored in the Account class ArrayList of accounts as new accounts
	 * 
	 * @param file The file that needs to be read holding account data
	 * @see BankIO.readAccountFile
	 * @throws IOException
	 */
	public static void readAccountFile(File file) {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while(br.readLine() !=  null) {	
				String bank = br.readLine();
				String[] banksAndAmount = bank.split("/");
				Account addAccount = new Account(banksAndAmount[0], Double.parseDouble(banksAndAmount[1]));
				Account.banksList.add(addAccount);
			}
			br.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Updates the Account class ArrayList of accounts with a whole new set of Accounts 
	 * that is stored in the Account.txt file
	 * Note that the method does not add more accounts to the ArrayList 
	 * 
	 * @see BankIO.readAccountFile()
	 */
	public static void updateAccountFile() {
		Account.banksList.clear();
		readAccountFile();
	}
	
	/**
	 * An overloaded method of the updateAccountFile that will take a File object holding 
	 * Account data and attempt to read the data and finally storing it in the ArrayList in Account
	 * Note that the data read does not add any more accounts to the ArrayList in Account
	 * 
	 * @param file The file that needs to be read holding account data
	 * @see BankIO.readAccountFile(File file)
	 */
	public static void updateAccountFile(File file) {
		Account.banksList.clear();
		readAccountFile(file);
	}
	
	/**
	 * Use a writing chain that chains a FileWriter to a BufferedWriter for improved writing performance
	 * Writes the data from the Account class ArrayList of accounts to a the file Account.txt
	 * This method acts as a way to store account data 
	 * 
	 * @throws IOException
	 */
	public static void writeAccountFile() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("Account.txt"));
			for(int i = 0; i < Account.banksList.size(); i++) {
				bw.newLine();
				bw.write(Account.banksList.get(i).getAccountName());
				bw.write("/");
				bw.write(Double.toString(Account.banksList.get(i).getAmount()));
				bw.newLine();
			}
			bw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * An overloaded method of the writeAccountFile that takes a File object that need to be written to
	 * Account data from the ArrayList is written to the file. 
	 * 
	 * @param file The file object that needs to be written to
	 * @throws IOException
	 */
	public static void writeAccountFile(File file) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < Account.banksList.size(); i++) {
				bw.newLine();
				bw.write(Account.banksList.get(i).getAccountName());
				bw.write("/");
				bw.write(Double.toString(Account.banksList.get(i).getAmount()));
				bw.newLine();
			}
			bw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Uses a writing chain that chained a FileWriter to a BufferedWriter to write data to a file
	 * Method takes the fileName that must be present in the folder of Finance otherwise an IOException is thrown
	 * The data array to be written needs to be formatted to separate out the different portions to be written
	 * 
	 * @param fileName The name of the file that needs to be written to. Must already exist in the Finance folder
	 * @param data The array of data that needs to be written to the file
	 * @throws IOException
	 */
	public static void writeToFile(String fileName, String[] data) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
			bw.newLine();
			for(String datawrite : data) {
				bw.write(datawrite);
				bw.write(":");
			}
			bw.newLine();
			bw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Uses a reading chain that chained a FileReader to a BufferedReader to read data from a file
	 * The name of the file being passed must be present in the Finance folder otherwise the exception will be thrown
	 * Method will return back a ArrayList containing strings of the data
	 * 
	 * @param fileName The name of the file being read. Must already exist in the Finance folder
	 * @return ArrayList<String> that holds all the data read from the file
	 * @throws IOException
	 */
	public static ArrayList<String> readFromFile(String fileName) {
		ArrayList<String> result = new ArrayList<String> ();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while(br.readLine() != null) {
				String line = br.readLine();
				result.add(line);
			}
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
