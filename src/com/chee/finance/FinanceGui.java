package com.chee.finance;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.filechooser.*;

import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * A java swing based class that holds all the GUI code for the GUI of the Finance Applet
 * 
 * @author Chee Peng
 * @version 1.00
 * @since 19/5/2017
 *
 */
public class FinanceGui implements Serializable {
	
	private JFrame frame;
	private JPanel posbPanel;
	private JPanel cimbPanel;
	private JPanel cashPanel;
	private JFrame transferAdder;
	private JFrame incomeAdder;
	private JFrame expenseAdder;
	private JPanel expensePanel;
	private JPanel incomePanel;
	private JPanel transferPanel;
	private JFrame messageFrame;
	private JFrame errorFrame;
	private JScrollPane expenseScrollPane;
	private JScrollPane incomeScrollPane;
	private JScrollPane transferScrollPane;

	/**
	 * Method is called by Finance class when the applet first initialises 
	 * Method calls up the buildGui() method to start building the basic GUI of the applet
	 * 
	 * @see FinanceGui.buildGui(), Finance.main()
	 */
	public void initialise() {
		buildGui();
	}
	
	/**
	 * Sets up a overall frame that will have 3 panels in the west, center and east 
	 * of the BorderLayout. The 3 panels will hold all the buttons and data including 
	 * the transactions performed on the accounts. The frame also has a JMenuBar that can
	 * be used for some quick navigation
	 */
	private void buildGui() {

		frame = new JFrame("Finance Keeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		expenseScrollPane = buildJTable("Expense.txt");
		incomeScrollPane = buildJTable("Income.txt");
		transferScrollPane = buildJTable("Transfer.txt");
		posbPanel = buildAccountPanels(0);
		cimbPanel = buildAccountPanels(1);
		cashPanel = buildAccountPanels(2);
		frame.getContentPane().add(BorderLayout.WEST, posbPanel);
		frame.getContentPane().add(BorderLayout.CENTER, cimbPanel);
		frame.getContentPane().add(BorderLayout.EAST, cashPanel);
		frame.setJMenuBar(buildJMenuBar());
		frame.setSize(1440, 300);
		frame.setVisible(true);
	}
	
	/**
	 * Builds a JPanel that will show the name of an account and the amount in an account
	 * Adds 3 buttons that will allow one to add expense, add income and add transfer to the account
	 * The last part has a JTable that will hold all data of the transactions that has happened/saved
	 * 
	 * @param index The pointer for getting data off the ArrayList in Account class
	 * @return The built JPanel
	 */
	private JPanel buildAccountPanels(int index) {
		JPanel section = new JPanel();
		BoxLayout box = new BoxLayout(section, BoxLayout.Y_AXIS);
		section.setLayout(box);
		Dimension d = new Dimension(100,300);
		section.setSize(d);
		JLabel accountLabel = new JLabel(Account.banksList.get(index).getAccountName());
		JLabel amountLabel = new JLabel("Amount: $" + 
				String.valueOf(Account.banksList.get(index).getAmount()));
		JButton expense = new JButton("Add Expense");
		expense.addActionListener(new AccountExpense());
		JButton income = new JButton("Add Income");
		income.addActionListener(new AccountIncome());
		JButton transfer = new JButton("Transfer");
		transfer.addActionListener(new AccountTransfer());
		section.add(accountLabel);
		section.add(amountLabel);
		section.add(expense);
		section.add(income);
		section.add(transfer);
		if(index == 0) {
			section.add(expenseScrollPane);
		} else if(index == 1) {
			section.add(incomeScrollPane);
		} else {
			section.add(transferScrollPane);
		}
		return section;
	}
	
	/**
	 * Builds the JMenuBar that will be attached to the main frame
	 * Options include saving and loading of accounts, viewing the JTables that hold all the 
	 * transaction that has happened and a get help section with information on the applet
	 * 
	 * @return A built JMenuBar
	 */
	private JMenuBar buildJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu help = new JMenu("Help");
		JMenu view = new JMenu("View");
		JMenuItem  save = new JMenuItem("Save");
		save.addActionListener(new SaveAccount());
		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(new LoadAccount());
		JMenuItem getHelp = new JMenuItem("Get Help!");
		getHelp.addActionListener(new GetHelp());
		JMenuItem viewExpense = new JMenuItem("Expense Table");
		JMenuItem viewIncome = new JMenuItem("Income Table");
		JMenuItem viewTransfer = new JMenuItem("Transfer Table");
		viewExpense.addActionListener(new OpenTable());
		viewIncome.addActionListener(new OpenTable());
		viewTransfer.addActionListener(new OpenTable());
		view.add(viewExpense);
		view.add(viewIncome);
		view.add(viewTransfer);
		help.add(getHelp);
		file.add(save);
		file.add(load);
		menuBar.add(file);
		menuBar.add(view);
		menuBar.add(help);
		return menuBar;
	}
	
	/**
	 * Inner class that implements ActionListener to the JMenuItem that allows the JTable 
	 * with all the transactions to be opened in a new window
	 * 
	 * @see buildJMenuBar
	 *
	 */
	private class OpenTable implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String tableToBeOpened = e.getActionCommand();
			
			switch(tableToBeOpened) {
			case("Expense Table") :
				JFrame expenseTableFrame = new JFrame();
			expenseTableFrame.getContentPane().add(buildJTable("Expense.txt"));
			expenseTableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			expenseTableFrame.setSize(770, 300);
			expenseTableFrame.setVisible(true);
				break;
			case("Income Table") :
				JFrame incomeTableFrame = new JFrame();
			incomeTableFrame.getContentPane().add(buildJTable("Income.txt"));
			incomeTableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			incomeTableFrame.setSize(770, 300);
			incomeTableFrame.setVisible(true);
				break;
			case("Transfer Table") :
				JFrame transferTableFrame = new JFrame();
			transferTableFrame.getContentPane().add(buildJTable("Transfer.txt"));
			transferTableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			transferTableFrame.setSize(770, 200);
			transferTableFrame.setVisible(true);
				break;
			}
		}
	}
	
	/**
	 * Inner class that implements ActionListener to the JMenuItem that will save 
	 * current details of the account to a file that is selected by the user
	 * A JFileChooser will pop up to ask for which file to save the data to
	 * 
	 * @see BankIO.writeAccountFile(File file)
	 *
	 */
	private class SaveAccount implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				JFileChooser saveAccountFile = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
				saveAccountFile.addChoosableFileFilter(filter);
				saveAccountFile.setFileFilter(filter);
				if(saveAccountFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String name = saveAccountFile.getName(saveAccountFile.getSelectedFile());
					String directory = saveAccountFile.getCurrentDirectory().getPath();
					File newAccountFile = new File(directory, name);
					if(newAccountFile.exists()) {
						BankIO.writeAccountFile(newAccountFile);
					} else {
						newAccountFile.createNewFile();
						BankIO.writeAccountFile(newAccountFile);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Inner class that is similar to the SaveAccount inner class that will
	 * load details of accounts from a file that is selected by a pop up
	 * JFileChooser and update it on the main frame
	 * 
	 * @see BankIO.updateAccountFile(File file);
	 *
	 */
	private class LoadAccount implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				JFileChooser loadAccountFile = new JFileChooser();
				FileNameExtensionFilter textFilter = new FileNameExtensionFilter(".txt", "txt");
				FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".File", "File");
				loadAccountFile.addChoosableFileFilter(textFilter);
				loadAccountFile.addChoosableFileFilter(fileFilter);
				loadAccountFile.setFileFilter(textFilter);
				if(loadAccountFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = loadAccountFile.getSelectedFile();
					BankIO.updateAccountFile(file);
					updateAmountOnFrame();
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Inner class  that is attached to the JMenuItem get help which will open
	 * a GetHelp.txt for details on the applet
	 * 
	 */
	private class GetHelp implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try{
				ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "GetHelp.txt");
				pb.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Inner class that will build a new JFrame that will display the window for
	 * entering of Expense data
	 *
	 */
	private class AccountExpense implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			expenseAdder = new JFrame();
			expenseAdder.setTitle("Add Expense");
			expenseAdder.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			expenseAdder.setSize(200, 300);
			JPanel expenseAdderWindowButton = buildAdditionWindowButton();
			JButton add = (JButton)expenseAdderWindowButton.getComponent(0);
			JButton cancel = (JButton)expenseAdderWindowButton.getComponent(1);
			add.addActionListener(new AddExpense());
			cancel.addActionListener(new CancelExpense());
			expensePanel = buildAdditionWindow("Date", "Type", "Name", "Amount");
			expenseAdder.getContentPane().add(expensePanel);
			expenseAdder.getContentPane().add(BorderLayout.SOUTH, expenseAdderWindowButton);
			expenseAdder.setVisible(true);
		}
	}
	
	/**
	 * Inner class that will build a new JFrame that will display the window for
	 * entering of Income data
	 *
	 */
	private class AccountIncome implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			incomeAdder = new JFrame();
			incomeAdder.setTitle("Add Income");
			incomeAdder.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			incomeAdder.setSize(200, 300);
			JPanel incomeAdderWindowButton = buildAdditionWindowButton();
			JButton add = (JButton)incomeAdderWindowButton.getComponent(0);
			JButton cancel = (JButton)incomeAdderWindowButton.getComponent(1);
			add.addActionListener(new AddIncome());
			cancel.addActionListener(new CancelIncome());
			incomePanel =  buildAdditionWindow("Source", "Date", "Type", "Amount");
			incomeAdder.getContentPane().add(BorderLayout.SOUTH, incomeAdderWindowButton);
			incomeAdder.getContentPane().add(incomePanel);
			incomeAdder.setVisible(true);
		}
	}
	
	/**
	 * Inner class that will build a new JFrame that will display the window for
	 * entering of transfer data
	 *
	 */
	private class AccountTransfer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			transferAdder = new JFrame();
			transferAdder.setTitle("Transfer");
			transferAdder.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			transferAdder.setSize(200, 300);
			JPanel transferAdderWindowButton = buildAdditionWindowButton();
			JButton add = (JButton)transferAdderWindowButton.getComponent(0);
			add.addActionListener(new AddTransfer());
			JButton cancel = (JButton)transferAdderWindowButton.getComponent(1);
			cancel.addActionListener(new CancelTransfer());
			transferPanel = buildAdditionWindow("Date", "From", "To", "Amount");
			transferAdder.getContentPane().add(transferPanel);
			transferAdder.getContentPane().add(BorderLayout.SOUTH, transferAdderWindowButton);
			transferAdder.setVisible(true);
		}
	}
	
	/*
	 * Inner class that is attached to the JButton in the AccountExpense inner class
	 * Adds the expense data that has been entered
	 * 
	 */
	private class AddExpense implements ActionListener {
		/**
		 * Calls the method for addition of expense to the account
		 * 
		 * @see FinanceGui.additionOfMoney
		 */
		public void actionPerformed(ActionEvent e) {
			additionOfMoney('e');
		}
	}
	
	/**
	 * A method that will update the amount of the account on the main frame
	 * Called by any method that will change the amount of the account be it transfer, expense or income
	 */
	private void updateAmountOnFrame() {
		JLabel amountPosb = (JLabel)posbPanel.getComponent(1);
		amountPosb.setText("Amount: $" + Double.toString(Account.banksList.get(0).getAmount()));
		JLabel amountCimb = (JLabel)cimbPanel.getComponent(1);
		amountCimb.setText("Amount: $" + Double.toString(Account.banksList.get(1).getAmount()));
		JLabel amountCash = (JLabel)cashPanel.getComponent(1);
		amountCash.setText("Amount: $" + Double.toString(Account.banksList.get(2).getAmount()));
	}
	
	/**
	 * This method is used to determine if there is enough data entered 
	 * in the income, expense or transfer(i.e. all the text fields must be filled in)
	 * If not, it will return an array length of 5 that will indicate that not enough data has been entered
	 * 
	 * @param index The type of transaction that is occurring, 'e' for expense, 'i' for income and 't' for transfer
	 * @return An array of length 4 with all data if all text fields are filled in, An array of length 5 
	 * if there is any blank text fields
	 */
	private String[] getPanelText(char index) {
		String[] result = new String[4];
		for(int i = 0; i < result.length; i++) {
			if(getPanelText(i, index).isEmpty()) {
				return new String[5];
			} else {
				result[i] = getPanelText(i, index);		
			}
		}
		return result;
	}
	
	/**
	 * Calls the getPanelText method that will determine if the data entered in the text fields are enough.
	 * If the called method returns an array of length 4, the transaction addition window will be disposed
	 * and the transaction added to the account denoted by index and the JTable updated. The updated account
	 * amount is also automatically saved to the Account.txt file and the main frame will be updated with
	 * the latest amount before displaying a message that the transaction has been successful
	 * Otherwise, an error message is shown and more data will have to be entered
	 * 
	 * @param index The type of transaction that is occurring, 'e' for expense, 'i' for income and 't' for transfer
	 */
	private void additionOfMoney(char index) {
		String[] toBeSaved = getPanelText(index);
		if(toBeSaved.length != 5) {
			switch(index) {
			case 'e' :
				expenseAdder.dispose();
				BankIO.writeToFile("Expense.txt", toBeSaved);
				Account.addExpense(new Expense(toBeSaved[0], toBeSaved[1],
						toBeSaved[2], Double.parseDouble(toBeSaved[3])));
				updateJTable(toBeSaved, expenseScrollPane);
				break;
			case 'i' :
				incomeAdder.dispose();
				BankIO.writeToFile("Income.txt", toBeSaved);
				Account.addIncome(new Income(toBeSaved[0], toBeSaved[1],
						toBeSaved[2], Double.parseDouble(toBeSaved[3])));
				updateJTable(toBeSaved, incomeScrollPane);
				break;
			case 't' :
				transferAdder.dispose();
				BankIO.writeToFile("Transfer.txt", toBeSaved);
				Account.addTransfer(new Transfer(toBeSaved[0], toBeSaved[1],
						toBeSaved[2], Double.parseDouble(toBeSaved[3])));
				updateJTable(toBeSaved, transferScrollPane);
				break;
			}
			BankIO.writeAccountFile();
			BankIO.updateAccountFile();
			updateAmountOnFrame();
			messageBox();
		} else {
			messageBoxError();
		}
		
	}
	
	/**
	 * Creates a new window that will show a transaction being successful 
	 */
	public void messageBox() {
		messageFrame = new JFrame();
		JTextArea textArea = new JTextArea("Your transaction has been added successfully.");
		JButton button = new JButton("Ok");
		button.addActionListener(new CloseMessageBox());
		messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		messageFrame.setSize(300,100);
		messageFrame.getContentPane().add(BorderLayout.CENTER, textArea);
		messageFrame.getContentPane().add(BorderLayout.SOUTH, button);
		messageFrame.setVisible(true);
	}
	
	/**
	 * Creates a new window that will show that the transaction is unsuccessful and more data has to be entered
	 */
	private void messageBoxError() {
	    errorFrame = new JFrame();
		JTextArea textArea = new JTextArea("Please fill in all the details before adding transaction.");
		JButton button = new JButton("Ok");
		button.addActionListener(new CloseErrorMessageBox());
		errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		errorFrame.setSize(300, 100);
		errorFrame.getContentPane().add(BorderLayout.CENTER, textArea);
		errorFrame.getContentPane().add(BorderLayout.SOUTH, button);
		errorFrame.setVisible(true);
	}
	
	/**
	 * Inner class that will dispose of the successful window frame
	 * 
	 */
	private class CloseMessageBox implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			messageFrame.dispose();
		}
	}

	/**
	 * Inner class that will dispose of the unsuccessful window frame asking for more data
	 * This will not dispose of the transaction addition window
	 * 
	 */
	private class CloseErrorMessageBox implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			errorFrame.dispose();
		}
	}
	
	/**
	 * Gets the data that has been entered into the text fields in the transaction addition window
	 *
	 * @param i The text field to get data from, ranging from 0 to 3 inclusive
	 * @param index The type of transaction that is occurring, 'e' for expense, 'i' for income and 't' for transfer
	 * @return The data entered in the particular text field
	 */
	private String getPanelText(int i, char index) {
		if(index == 'e') {
			JPanel ePanel = (JPanel)expensePanel.getComponent(i);
			JTextField eText = (JTextField) ePanel.getComponent(1);
			return eText.getText();
		} else if(index == 'i') {
			JPanel iPanel = (JPanel)incomePanel.getComponent(i);
			JTextField iText = (JTextField) iPanel.getComponent(1);
			return iText.getText();
		} else {
			JPanel tPanel = (JPanel)transferPanel.getComponent(i);
			JTextField tText = (JTextField) tPanel.getComponent(1);
			return tText.getText();
		}
	}
	
	/**
	 * Inner class that will dispose of the transaction window holding expense transactions
	 *
	 */
	private class CancelExpense implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			expenseAdder.dispose();
		}
	}
	
	/*
	 * Inner class that is attached to the JButton in the AccountIncome inner class
	 * Adds the income data that has been entered
	 * 
	 */
	private class AddIncome implements ActionListener {
		/**
		 * Calls the method for addition of the income to the account
		 * 
		 * @see FinanceGui.additionOfMoney
		 */
		public void actionPerformed(ActionEvent e) {
			additionOfMoney('i');
		}
	}
	
	/**
	 * Inner class that will dispose of the transaction window holding income transactions
	 *
	 */
	private class CancelIncome implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			incomeAdder.dispose();
		}
	}
	
	/*
	 * Inner class that is attached to the JButton in the AccountTransfer inner class
	 * Adds the transfer data that has been entered
	 * 
	 */
	private class AddTransfer implements ActionListener {
		/**
		 * Calls the method for addition of the transfer to the account
		 * 
		 * @see FinanceGui.additionOfMoney
		 */
		public void actionPerformed(ActionEvent e) {
			additionOfMoney('t');
		}
	}
	
	/**
	 * Inner class that will dispose of the transaction window holding transfer transactions
	 *
	 */
	private class CancelTransfer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			transferAdder.dispose();	
		}
	}
	
	/**
	 * Builds a JPanel that will hold two button with texts add and cancel
	 * 
	 * @return JPanel with two buttons add and cancel
	 */
	private JPanel buildAdditionWindowButton() {
		JPanel panel = new JPanel();
		JButton add = new JButton("Add");
		JButton cancel = new JButton("Cancel");
		panel.add(add);
		panel.add(cancel);
		return panel;
	}
	
	/**
	 * Builds JPanel that has four label and four text fields that will hold up to 10 word spaces 
	 * 
	 * @param firstName The name of the first label
	 * @param secondName The name of the second label
	 * @param thirdName The name of the third label
	 * @param fourthName The name of the fourth label
	 * @return A JPanel that has four label and four text fields
	 */
	private JPanel buildAdditionWindow(String firstName, String secondName, String thirdName
			,String fourthName) {
		JPanel panel = new JPanel();
		JPanel firstPanel = new JPanel();
		JPanel secondPanel = new JPanel();
		JPanel thirdPanel = new JPanel();
		JPanel fourthPanel = new JPanel();
		JLabel firstLabel = new JLabel(firstName);
		JLabel secondLabel = new JLabel(secondName);
		JLabel thirdLabel = new JLabel(thirdName);
		JLabel fourthLabel = new JLabel(fourthName);
		JTextField firstField = new JTextField(10);
		JTextField secondField = new JTextField(10);
		JTextField thirdField = new JTextField(10);
		JTextField fourthField = new JTextField(10);
		firstPanel.add(firstLabel);
		firstPanel.add(firstField);
		secondPanel.add(secondLabel);
		secondPanel.add(secondField);
		thirdPanel.add(thirdLabel);
		thirdPanel.add(thirdField);
		fourthPanel.add(fourthLabel);
		fourthPanel.add(fourthField);
		panel.add(firstPanel);
		panel.add(secondPanel);
		panel.add(thirdPanel);
		panel.add(fourthPanel);
		return panel;
	}
	
	/**
	 * Builds a JTable nested inside a JScrollPane that will have all the transactions 
	 * of the income, expense and transfer transaction regardless of which account it is made on
	 * The JTable has a row sorter that will sort according to the auto created row sorter
	 * 
	 * @param fileName The file to read the transaction data from
	 * @return JScrollPane with a JTable nested with all data on the transaction
	 */
	private JScrollPane buildJTable(String fileName) {
		ArrayList<String> data = BankIO.readFromFile(fileName);
		DefaultTableModel tableModel = new DefaultTableModel(data.size(),4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		String[] columnIdentifiers = new String[4];
		switch (fileName) {
		case "Expense.txt" :
			columnIdentifiers[0] = "Date";
			columnIdentifiers[1] = "Type";
			columnIdentifiers[2] = "Name";
			columnIdentifiers[3] = "Amount";
			break;
		case "Income.txt" :
			columnIdentifiers[0] = "Source";
			columnIdentifiers[1] = "Date";
			columnIdentifiers[2] = "Type";
			columnIdentifiers[3] = "Amount";
			break;
		case "Transfer.txt" :
			columnIdentifiers[0] = "Date";
			columnIdentifiers[1] = "From";
			columnIdentifiers[2] = "To";
			columnIdentifiers[3] = "Amount";
			break;
		}
		tableModel.setColumnIdentifiers(columnIdentifiers);
		for(int i = 0; i < data.size(); i++) {
			String wholeData = data.get(i);
			String[] splitData = wholeData.split(":");
			for(int  j = 0; j < 4; j++) {
				tableModel.setValueAt(splitData[j], i, j);			
			}
		}
		JTable table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollerTable = new JScrollPane(table);
		scrollerTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollerTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		return scrollerTable;
	}
	
	/**
	 * Updates the JTable that was built initially when the applet starts up
	 * Called when there is any new successful transactions being made
	 * 
	 * @param data The data from the transaction
	 * @param scrollPane The table that needs to be updated determined by the type of transaction
	 */
	private void updateJTable(String[] data, JScrollPane scrollPane) {
		JTable tableToBeUpdated = (JTable)scrollPane.getViewport().getComponent(0);
		DefaultTableModel tableModel = (DefaultTableModel)tableToBeUpdated.getModel();
		tableModel.addRow(data);
		tableToBeUpdated.setModel(tableModel);
		tableModel.fireTableDataChanged();
	}
}
