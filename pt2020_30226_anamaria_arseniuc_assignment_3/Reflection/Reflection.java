package Reflection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Reflection {

	/**
	 * metoda care extrage campurile si valorile corespunzatoare acestora, primind
	 * instanta unui obiect oarecare
	 * 
	 * @param object
	 * @throws IllegalAccessException
	 */
	public static void retrieveProperties(Object object) throws IllegalAccessException {
		for (Field field : object.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value;
			try {
				value = field.get(object);
				System.out.println(field.getName() + "=" + value);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * inserare operatiilor SQL (inserare, delete etc)
	 * 
	 * @param zclass
	 * @param tableName
	 * @return
	 */

	public static String createInsertStatementSql(Class<?> zclass, String tableName) {
		StringBuilder fields = new StringBuilder();
		StringBuilder vars = new StringBuilder();
		for (Field field : zclass.getDeclaredFields()) {
			String name = field.getName();
			if (fields.length() > 1) {
				fields.append(",");
				vars.append(",");
			}

			vars.append("?");
		}
		String table = "database";
		String Sql = "INSERT INTO " + table + "(" + fields.toString() + ") VALUES(" + vars.toString() + ")";
		return Sql;
	}

	public static PreparedStatement createInsertPreparedStatement(Connection conn, Object object, String tableName) {
		PreparedStatement stmt = null;
		try {
			Class<?> zclass = object.getClass();
			String Sql = createInsertStatementSql(zclass, tableName);
			stmt = conn.prepareStatement(Sql);
			Field[] fields = zclass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				Object value = field.get(object);
				stmt.setObject((i + 1), value);
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | SQLException e) {
			String string = "Unable to create prepared statement: " + e.getMessage();
			throw new RuntimeException(string, e);
		}
		return stmt;
	}
}
