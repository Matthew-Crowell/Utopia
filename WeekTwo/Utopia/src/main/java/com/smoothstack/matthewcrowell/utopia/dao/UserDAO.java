package com.smoothstack.matthewcrowell.utopia.dao;

import com.smoothstack.matthewcrowell.utopia.entity.Booking;
import com.smoothstack.matthewcrowell.utopia.entity.Flight;
import com.smoothstack.matthewcrowell.utopia.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends BaseDAO<User>{

	public UserDAO(Connection conn) {
		super(conn);
	}

	public List<User> getUsers(){
		List<User> users = new ArrayList<>();
		try {
			users = read("SELECT * FROM users", null);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public User findUser(User user){
		List<User> users = new ArrayList<>();
		try{
			users = read("SELECT * FROM users WHERE username = ? OR id = ?",
					new Object[] { user.getUsername(), user.getUserId() });
		} catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
		return users.get(0);
	}

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
