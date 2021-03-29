package BusinessLogic;

import java.sql.Connection;
import java.sql.Statement;

import DataAccess.ConnectionFactory;

public class Commands {
	
	/**
	 * clasa care contine comenzi sql: crearea tabelelor , inserare, select
	 */
	public void createClient() {
		String SQL_DROP_C = "DROP CLIENT";
		final String SQL_CREATE_C = "CREATE TABLE CLIENT" + "(" + " ID INT NOT NULL AUTO_INCREMENT,"
				+ " NAME VARCHAR(50) NOT NULL," + " ADDRESS VARCHAR(100) NOT NULL," + " PRIMARY KEY (ID)" + ")";
		
		Connection conn = null;
		Statement statement = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			statement = conn.createStatement();
			
			statement.execute(SQL_DROP_C);
			statement.execute(SQL_CREATE_C);

		} catch (Throwable t) {
		}
	}
	
	public void createProduct() {
		String SQL_DROP_P = "DROP PRODUCT";
		final String SQL_CREATE_P = "CREATE TABLE PRODUCT" + "(" + " ID INT NOT NULL AUTO_INCREMENT,"
				+ " NAME VARCHAR(50) NOT NULL," + " QUANTITY INT) NOT NULL," + " PRICE DECIMAL(15, 2) NOT NULL," +" PRIMARY KEY (ID)" + ")";
		
		Connection conn = null;
		Statement statement = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			statement = conn.createStatement();
			
			statement.execute(SQL_DROP_P);
			statement.execute(SQL_CREATE_P);

		} catch (Throwable t) {
		}
	}
	
	public void selectProduct() {
		final String SQL_SELECT = "SELECT * FROM EMPLOYEE";
		
		Connection conn = null;
		Statement statement = null;
		
		try {
			conn = ConnectionFactory.getConnection();
			statement = conn.createStatement();	
			statement.execute(SQL_SELECT);
		
		} catch (Throwable t) {
		}
	}
	
}
