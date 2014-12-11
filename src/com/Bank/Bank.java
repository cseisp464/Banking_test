package com.Bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Bank {

	private long account_number;
	private long routing_number;
	private double balance;
	private int password;
	
	public int getPassword() {
		return password;
	}
	public void setPassword(int password) {
		this.password = password;
	}

	private JDBCHelper db;
	
	public long getAccount_number() {
		return account_number;
	}
	public void setAccount_number(long account_number) {
		this.account_number = account_number;
	}
	public long getRouting_number() {
		return routing_number;
	}
	public void setRouting_number(long routing_number) {
		this.routing_number = routing_number;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void updateBalance(double new_balance, long account_id) throws SQLException{
		connectDB();
		
		ArrayList<Object> param =  new ArrayList<Object>();
		param.add(new_balance);
		param.add(account_id);
		String query_string = "update accounts set balance = ?"
				+ " WHERE account_id = ? ";
		int result = db.updateDB(query_string, param);
		if(result>0){
			System.out.println("Successfully updated the balance");
		}else{
			System.out.println("oops! something went wrong");
		}
		setBalance(new_balance);
		
		closeDB();
	}
	
	/*
	 * This Function validates the account number by checking 
	 * if the account number exists in the database
	 */
	public boolean validateBankDetails(long entered_account_number, long entered_routing_number, double ticket_cost, int password) throws SQLException{
		
		boolean status = false;
		connectDB();
		
		System.out.println("In Bank.java");
		
		ArrayList<Object> param =  new ArrayList<Object>();
		param.add(entered_account_number);
		String query_string = "SELECT * FROM accounts"
				+ " WHERE account_id = ? ";
		ResultSet rs1 = db.queryDB(query_string, param);
		System.out.println(rs1.getStatement());
		
		// checking if record exists in the result set
		// if exists then we store the account details in the bean
		if(rs1.next()){
			status = true;
			setAccount_number(rs1.getLong("account_id"));
			setBalance(rs1.getDouble("balance"));
			setRouting_number(rs1.getLong("routing_number"));
			setPassword(rs1.getInt("password"));
		}
		closeDB();
		return status;
	}
	
	private void connectDB(){
		// Testing Database Connection using the JDBC Helper class
		db = new JDBCHelper("cse.unl.edu", "spalusa", "spalusa", "Test@464");
	}
	
	private void closeDB(){
		db.closeDBConnection();
	}
}
