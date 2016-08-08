package com.luxbet.qa.framework.utils;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * <b>Description: 
 * This is a database utility class provides APIs to retrieve/update column data from a given table.
 * @author Selva Nakuladeva
 * @since 22 July 2014
 */
public class Database {
	private static Logger logger = Logger.getLogger(Database.class);
	
	private Connection _con;
	private String _table;
	private String _recordNumber;
	private String _dbUrl = "jdbc:mysql://10.25.99.17:3306/testauto";
	private String _dbUsername = "testauto";    //"root";
    private String _dbPassword = "Luxbet123";   //"fungi1";

	public Database() {
		this._con = connect();
	}

    public Database(String table) {
        this._table = table;
        this._con = connect();
    }

	public Database(String table, String recordNumber) {
		this._table = table;
		this._recordNumber = recordNumber;
		this._con = connect();
	}
	
	/**
	 * <b>Description: 
	 * Returns the record(row) number where the cursor is currently set.
	 * @author Selva Nakuladeva
	 * @since 22 July 2014
	 */
	public String getRecordNumber() {
		logger.info("getRecordNumber()");
		
		return _recordNumber;
	}
	
	/**
	 * <b>Description: 
	 * This method moves the cursor to the specified record.
	 * @author Selva Nakuladeva
	 * @since 22 July 2014
	 */
	public void setRecordNumber(String recordNumber) {
		logger.info(String.format("setRecordNumber(%s)", recordNumber));
		
		this._recordNumber = recordNumber;
	}
	
	/**
	 * <b>Description: 
	 * Returns the table name that is currently set.
	 * @author Selva Nakuladeva
	 * @since 22 July 2014
	 */
	public String getTable() {
		logger.info("getTable()");
		
		return _table;
	}
	
	/**
	 * <b>Description: 
	 * Once you have a connection object to the db, you can then call this method to switch to tables
	 * @author Selva Nakuladeva
	 * @since 22 July 2014
	 */
	public void setTable(String table) {
		logger.info(String.format("setTable(%s)", table));
		
		this._table = table;
	}
	

	/**
	 * <b>Description: 
	 * Returns specified column data of the initialised db table
	 * @author Selva Nakuladeva
	 * @since 22 July 2014
	 */
	public String data(String columnName, String whereClause) {
		logger.info(String.format("data(%s)", columnName));
		
		String data = null;
		try {
			//ResultSet result = executeQuery("SELECT " + columnName + " FROM " + _table + " where id" + _table + "='" + _recordNumber + "';");
            String query = "SELECT  `" +columnName+ "` FROM  `" +_table+ "`  WHERE  " + whereClause;
            ResultSet result = executeQuery(query);
			result.next();
			data = result.getString(columnName);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return data;
	}
	
	/**
	 * <b>Description: 
	 * Sets or updates the specified column data of the initialised db table
	 * @author Selva Nakuladeva
	 * @since 22 July 2014
	 */
	public void setData(String columnName, String dataToSet) {
		logger.info(String.format("setData(%s)", dataToSet));
		try {
			executeUpdate("UPDATE " + getTable() + " SET " + columnName + "='" + dataToSet + "' WHERE id" + getTable() + "='" + getRecordNumber() + "';");
		} catch (Throwable t) {
			logger.error(t.getMessage());
		}
		
	}
	
	/**
	 * <b>Description: 
	 * Checks to see if this connection object is still open and closes the connection
	 * @author Selva Nakuladeva
	 * @since 22 July 2014
	 */
	public void closeConnection() {
		logger.info("closeConnection()");
		
		try {
			if (!_con.isClosed()) {
				_con.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
	    }
	}
	
	/**
	 * <b>Description: 
	 * Returns a connection object to specified db url 
	 * @author Selva Nakuladeva
	 * @since 22 July 2014
	 */
	private Connection connect() {
		logger.info("connect()");
		
		Connection con = null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
	        con = DriverManager.getConnection(_dbUrl, _dbUsername, _dbPassword);
		
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return con;
	}
     
	private ResultSet executeQuery(String query) {
		logger.info(String.format("executeStatement(%s)", query));
		
		try {
			PreparedStatement statement = _con.prepareStatement(query);
			return statement.executeQuery();
		} catch (Exception e) {
			logger.error(e.getMessage());
	    }
		return null;
	}
	
	private Object executeUpdate(String query) {
		logger.info(String.format("executeStatement(%s)", query));
		
		try {
			PreparedStatement statement = _con.prepareStatement(query);
			return statement.executeUpdate();
		} catch (Exception e) {
			logger.error(e.getMessage());
	    }
		return null;
	}
      
}
