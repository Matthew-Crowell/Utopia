package com.smoothstack.matthewcrowell.utopia.dao;

import com.smoothstack.matthewcrowell.utopia.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to connect the User class with the persistence layer.
 *
 * @author matthew.crowell
 */
public class UserDAO extends BaseDAO<User> {

	public UserDAO(Connection conn) {
		super(conn);
	}

	/**
	 * Add an User to the persistence layer.
	 *
	 * @param user User to add
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Boolean addUser(User user) throws SQLException, ClassNotFoundException {
		Boolean completedSuccessfully = Boolean.FALSE;
		save("INSERT INTO user (`role_id`, `given_name`, `family_name`, `username`, `email`, `password`, `phone`) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?)", new Object[]{user.getRole(), user.getGivenName(), user.getFamilyName(),
				user.getUsername(), user.getEmail(), user.getNewPassword(), user.getPhoneNumber()});

		completedSuccessfully = Boolean.TRUE;
		return completedSuccessfully;
	}

	/**
	 * Get a List of all Users in the persistence layer.
	 *
	 * @return List<User> containing all Users in database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<User> getUsers() throws SQLException, ClassNotFoundException {
		List<User> users = new ArrayList<>();
		users = read("SELECT * FROM user", null);
		return users;
	}

	/**
	 * Find a specific User by username or userId.
	 *
	 * @param user User to locate
	 * @return User from persistence layer
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public User findUser(User user) throws SQLException, ClassNotFoundException {
		List<User> users = new ArrayList<>();
		users = read("SELECT * FROM user WHERE username = ? OR id = ?",
				new Object[]{user.getUsername(), user.getUserId()});
		return users.get(0);
	}

	/**
	 * Update an existing User in the persistence layer.
	 *
	 * @param user User to update
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Boolean updateUser(User user) throws SQLException, ClassNotFoundException {
		Boolean completedSuccessfully = Boolean.FALSE;
		if (user.getNewPassword() != null) {
			save("UPDATE user SET role_id = ?, given_name = ?, family_name = ?, username = ?, " +
							"email = ?, phone = ?, password = ? WHERE id = ? or username = ?",
					new Object[]{user.getRole(), user.getGivenName(), user.getFamilyName(),
							user.getUsername(), user.getEmail(), user.getPhoneNumber(),
							user.getNewPassword(), user.getUserId(), user.getUsername()});
		} else {
			save("UPDATE user SET role_id = ?, given_name = ?, family_name = ?, username = ?, " +
					"email = ?, phone = ? WHERE id = ? or username = ?", new Object[]{user.getRole(),
					user.getGivenName(), user.getFamilyName(), user.getUsername(), user.getEmail(),
					user.getPhoneNumber(), user.getUserId(), user.getUsername()});
		}
		completedSuccessfully = Boolean.TRUE;
		return completedSuccessfully;
	}

	/**
	 * Remove a User from the persistence layer.
	 *
	 * @param user User to remove
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Boolean removeUser(User user) throws SQLException, ClassNotFoundException {
		Boolean completedSuccessfully = Boolean.FALSE;
		save("DELETE FROM user WHERE username = ?", new Object[]{user.getUsername()});
		completedSuccessfully = Boolean.TRUE;
		return completedSuccessfully;
	}

	/**
	 * Function used internally to interpret User data from persistence layer.
	 *
	 * @param rs ResultSet from a SQL database query
	 * @return List<User> of all Users found in the persistence layer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<User> extractData(ResultSet rs) throws SQLException {
		List<User> users = new ArrayList<>();

		while (rs.next()) {
			User user = new User();
			user.setUserId(rs.getInt("id"));
			user.setRole(rs.getInt("role_id"));
			user.setGivenName(rs.getString("given_name"));
			user.setFamilyName(rs.getString("family_name"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPhoneNumber(rs.getString("phone"));
			users.add(user);
		}

		return users;
	}
}
