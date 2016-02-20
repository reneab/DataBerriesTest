package main.java.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.bean.User;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

import java.sql.Statement;

public class UserDao {

	private static final String URL = "jdbc:mysql://localhost:3306/test";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "rene";
	private static final String DB_NAME = "people";

	private Connection connection;
	private static UserDao dao = null;

	public static UserDao getInstance() {
		if (dao == null) {
			dao = new UserDao();
		}
		return dao;
	}

	private UserDao() {
		System.out.println("Connecting database...");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = (Connection) DriverManager.getConnection(
					URL, USERNAME, PASSWORD);
			System.out.println("Database connected!");
			this.connection = connection;
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public User getUser(String name) {
		Statement stmt = null;
		String query = "select * " + "from " + DB_NAME + " where name = '"
				+ name + "'";

		User resultUser = null;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				int age = rs.getInt("age");
				resultUser = new User(name, age);
			} else {
				System.err.println("No result found for your query");
			}
		} catch (SQLException e) {
			System.out.println("Error while querying database for user : "
					+ name);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return resultUser;
	}

	public static void main(String[] args) {
		UserDao userDao = new UserDao();
		User user = userDao.getUser("Minh");
		System.out.println(user.getName() + " : " + user.getAge());
	}

}
