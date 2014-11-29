package com.Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JDBCHelper {
	
	public Connection conn;
	public Connection dbConnection;

	public JDBCHelper(String host, String db, String user, String password){
		this.conn = this.initiateConnection(host, db, user, password);
	}
	
	/***
	 * Make sure to close your connection!!!
	 */
	/**private static void example() {
		JDBCHelper jdbc = new JDBCHelper("cse.unl.edu", "cse464", "cse464", "");
		ArrayList<Object> param =  new ArrayList<Object>();
		
		param.add("Hello");
		param.add(35767);
		param.add(4.0);
		param.add(Timestamp.valueOf("2014-09-30 11:41:00"));
		 
		param.add(35767);
		ResultSet rs1 = jdbc.queryDB("SELECT flights.arrival AS arrival FROM flights JOIN planes ON flights.plane=planes.id WHERE planes.plane_number=?", param);

		try {
			if (rs1 != null){
				while (rs1.next()){
					System.out.println(rs1.getTimestamp("arrival"));
				}
			}
			jdbc.conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	} */


	/**
	 * This class loads the MySQL Driver and Connects to the entered database.
	 * @param host Host computer ("cse.unl.edu")
	 * @param db
	 * @param user
	 * @param password 
	 * @return A live connection or null
	 */
	public Connection initiateConnection(String host, String db, String user, String password){

		dbConnection = null;

		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("Driver Loaded");
		}catch(Exception x){
			System.out.println("Unable to load the driver class");
		}


		try{
			String connString = String.format("jdbc:mysql://%s:3306/%s?user=%s&password=%s", host, db, user, password);
			dbConnection = DriverManager.getConnection(connString);
			System.out.println("Connected to Database");
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Couldn't get Connection");
			return null;
		}
		return dbConnection;
	}

	/**
	 * 
	 * 
	 * @param query A Select Statement.  Use ? for parameters and the sqlParam parameters to pass in values. 
	 * @param sqlParam An ArrayList of objects of parameters for the Select Statement.
	 * @return A resultset if the query is successful, else null
	 */
	public <T> ResultSet queryDB(String query, ArrayList<T> sqlParam){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(query);

			int i = 1;
			for (T a : sqlParam){
				//System.out.println(a.getClass());
				if (a.getClass() == String.class){
					ps.setString(i, (String)a);
					//System.out.println(String.format("I'm a String!  %d - %s", i, (String) a));
				}else if(a.getClass() == Integer.class){
					ps.setInt(i, (Integer)a);
					//System.out.println(String.format("I'm an Integer!  %d - %d", i, (Integer) a));
				}else if(a.getClass() == Double.class){
					ps.setDouble(i, (Double)a);
					//System.out.println(String.format("I'm a Double!  %d - %f", i, (Double) a));
				}else if (a.getClass() == Timestamp.class){
					ps.setTimestamp(i, (Timestamp)a);
					//System.out.println(String.format("I'm a DateTime!  %d - %s", i, a.toString()));
				}else if (a.getClass() == Long.class){
					ps.setLong(i, (Long)a);
					//System.out.println(String.format("I'm a DateTime!  %d - %s", i, a.toString()));
				}
				i++;
			}
			
			rs = ps.executeQuery();
			
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		return rs;
	}
	

	/**
	 * 
	 * 
	 * @param query SQL Data Manipulation Language (DML) statements, such as INSERT, UPDATE or DELETE; or an SQL statement that returns nothing, such as a DDL statement.
	 * Use ? for parameters and the sqlParam parameters to pass in values. 
	 * @param sqlParam An ArrayList of objects of parameters for the Select Statement.
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
	 */
	public <T> int updateDB(String query, ArrayList<T> sqlParam){
		PreparedStatement ps = null;
		int rs = 0;
		try{
			ps = conn.prepareStatement(query);

			int i = 1;
			for (T a : sqlParam){
				//System.out.println(a.getClass());
				if (a.getClass() == String.class){
					ps.setString(i, (String)a);
					//System.out.println(String.format("I'm a String!  %d - %s", i, (String) a));
				}else if(a.getClass() == Integer.class){
					ps.setInt(i, (Integer)a);
					//System.out.println(String.format("I'm an Integer!  %d - %d", i, (Integer) a));
				}else if(a.getClass() == Double.class){
					ps.setDouble(i, (Double)a);
					//System.out.println(String.format("I'm a Double!  %d - %f", i, (Double) a));
				}else if (a.getClass() == Timestamp.class){
					ps.setTimestamp(i, (Timestamp)a);
					//System.out.println(String.format("I'm a DateTime!  %d - %s", i, a.toString()));
				}else if (a.getClass() == Long.class){
					ps.setLong(i, (Long)a);
					//System.out.println(String.format("I'm a DateTime!  %d - %s", i, a.toString()));
				}
				i++;
			}
			
			rs = ps.executeUpdate();
			
		}catch (SQLException e){
			e.printStackTrace();
			return 0;
		}
		return rs;
	}
	
	public void closeDBConnection(){
		try {
			conn.close();
			dbConnection.close();
			System.out.println("Database Connection Closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

