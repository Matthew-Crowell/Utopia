package com.smoothstack.matthewcrowell.utopia.test;

import com.smoothstack.matthewcrowell.utopia.UtopiaApp;
import com.smoothstack.matthewcrowell.utopia.dao.UserDAO;
import com.smoothstack.matthewcrowell.utopia.entity.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDAOTest extends TestCase {
	private UtopiaApp app;
	private User user;
	private Connection conn;
	private UserDAO udao;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		app = new UtopiaApp();
		conn = app.getConnUtil().getConnection("mysql");
		user = new User();
		udao = new UserDAO(conn);
		user.setRole(2);
		user.setUsername("vjohnson");
		user.setGivenName("Victoria");
		user.setFamilyName("Johnson");
		user.setEmail("Victoria.Johnson@example.com");
		user.setPhoneNumber("123-456-7890");
		user.setNewPassword("test");
	}

	@Test
	public void testAddUser() {

		try {
			udao.addUser(user);
			conn.commit();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			udao.removeUser(user);
			conn.commit();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFindUser() {
		User duplicate = null;
		try {
			duplicate = udao.findUser(user);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		assertTrue(duplicate.getUsername().equals("vjohnson"));
	}


	@Test
	public void testGetUsers() {
		Boolean foundUser = Boolean.FALSE;
		List<User> users = null;
		try {
			udao.addUser(user);
			conn.commit();
			users = udao.getUsers();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Integer i = 0;
		for(User u : users){
			if(u.getUsername().equals("vjohnson")){
				foundUser = Boolean.TRUE;
			}
		}
		assertTrue(foundUser);

		try {
			udao.removeUser(user);
			conn.commit();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testUpdateUser() {
		User duplicate = null;
		try {
			udao.addUser(user);
			conn.commit();
			user.setGivenName("Viktoria");
			udao.updateUser(user);
			conn.commit();
			user.setNewPassword(null);
			udao.updateUser(user);
			conn.commit();
			duplicate = udao.findUser(user);
			assertTrue(duplicate.getGivenName().equals("Viktoria"));
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveUser() {

		user.setUserId(-1);
		try {
			udao.removeUser(user);
			conn.commit();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}