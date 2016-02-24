package main.java.dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.bean.User;

import java.sql.Connection;

public class UserDao {

	private static final String URL = "jdbc:mysql://localhost:3306/test";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "rene";
	private static final String DB_NAME = "people";

	private Connection connection;
	private static UserDao dao = new UserDao();

	public static UserDao getInstance() {
		return dao;
	}

	private UserDao() {
		System.out.println("Connecting database...");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(URL, USERNAME,
					PASSWORD);
			System.out.println("Database connected!");
			this.connection = connection;
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public User getUser(String name) {
		String queryString = "select * " + "from " + DB_NAME
				+ " where name = ?";
		PreparedStatement statement = null;
		User resultUser = null;
		try {
			statement = connection.prepareStatement(queryString);
			statement.setString(1, name);
			
			ResultSet rs = statement.executeQuery();
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
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return resultUser;
	}
	
	public static void main(String[] args) {
		UserDao userDao = UserDao.getInstance();
		User user = userDao.getUser("Minh");
		System.out.println(user.getName() + ", " + user.getAge() + " years old");
	}

}
