package net.mcsro.utilities.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 
 * 
 * 
 * 
 * @author Infinite
 *
 */
public class MySQL extends Database {
	static String user = "";
	static String database = "";
	static String password = "";
	static String port = "";
	static String hostname = "";
	static Connection c = null;

	
	/**
	 * 
	 * @param Hostname = Hostname
	 * @param portnmbr =  Port
	 * @param database = Database
	 * @param username = Username
	 * @param password = Password
	 */
	@SuppressWarnings("static-access")
	public MySQL(String hostname, String portnmbr, String database,
			String username, String password) {
		this.hostname = hostname;
		this.port = portnmbr;
		this.database = database;
		this.user = username;
		this.password = password;
	}
	/**
	 * 
	 * Opens the connection.
	 * 
	 * @return the open connection
	 */
	public Connection open() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://" + hostname + ":"
					+ port + "/" + database, user, password);
			return c;
		} catch (SQLException e) {
			System.out.println("Could not connect to MySQL server! because: "
					+ e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver not found!");
		}
		return c;
	}
	/**
	 * 
	 * @return connection status
	 * True = open.
	 * False = closed.
	 */
	@SuppressWarnings("static-access")
	public boolean checkConnection() {
		if (this.c != null) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * 
	 * 
	 * @return the connection
	 */
	@SuppressWarnings("static-access")
	public Connection getConn() {
		return this.c;
	}

	public static Connection closeConnection(Connection c) {
		return c = null;
	}

	public ResultSet querySQL(String query) throws SQLException {
		Connection c = null;
			c = getConn();
			c = open();
		Statement s = null;
			s = c.createStatement();
		ResultSet ret = null;
			ret = s.executeQuery(query);
		closeConnection(c);

		return ret;
	}

	public void updateSQL(String update) throws SQLException {
		Connection c = null;
		if (checkConnection()) {
			c = getConn();
		} else {
			c = open();
		}
		Statement s = null;
			s = c.createStatement();
			s.executeUpdate(update);
		
		closeConnection(c);
	}
	
	public ResultSet executeQuery(String select, String table) throws SQLException{
		if(checkConnection()){
		return c.createStatement().executeQuery("SELECT " + select + " FROM " + table+ ";");
		}else{
			return null;
		}
	}
	public ResultSet executeQuery(String select, String table, String condition, String answer) throws SQLException{
		if(checkConnection()){
		if(answer.equalsIgnoreCase("NOW()")){
			return c.createStatement().executeQuery("SELECT " + select + " FROM " + table+ " WHERE "+condition +" = "+""+answer+""+";");
		}
		return c.createStatement().executeQuery("SELECT " + select + " FROM " + table+ " WHERE "+condition +" = "+"'"+answer+"'"+";");
		}else{
			return null;
		}
	}
	public ResultSet executeQuery(String select, String table, String condition, double answer) throws SQLException{
		if(checkConnection()){
		return c.createStatement().executeQuery("SELECT " + select + " FROM " + table+ " WHERE "+condition +" = "+""+answer+""+";");
		}else{
			return null;
		}
	}
	public void insert( String table, String[] where, String[] answer) throws Throwable , SQLException{
		if(checkConnection()){
			if(where.length==0||answer.length==0){
				throw new Exception().initCause(new Throwable("Answer or Condition is = 0!"));	
			}
			if(where.length != answer.length){
				throw new Exception().initCause(new Throwable("Answer.length != Condition.length!"));	
			}
		String query = "INSERT INTO " + table + " (`";
		StringBuilder builder =  new StringBuilder();
		int i = 0;
		for(String s : where){
			i++;
			if(i == where.length){
				builder.append(s).append("`)");
			}else{
				builder.append(s).append("`, `");
			}

		}
		query = query + builder.toString().trim() + " VALUE (";
		StringBuilder v =  new StringBuilder();
		int u = 0;
		
		for(String s : answer){
			u++;
			if(u == where.length){
				if(s.equalsIgnoreCase("NOW()")){
					v.append("").append(s).append(");");
				}
				if(isNaN(s)){
					v.append("").append(s).append(");");
				}else{
					v.append("'").append(s).append("');");
				}
			}else{
				if(s.equalsIgnoreCase("NOW()")){
					v.append("").append(s).append(", ");
				}
				if(isNaN(s)){
					v.append("").append(s).append(", ");
				}else{
					v.append("'").append(s).append("', ");
				}
				
			}

		}
		query = query + v.toString().trim();
		c.createStatement().execute(query);
		}
	}
	@SuppressWarnings("unused")
	public static boolean isNaN(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}