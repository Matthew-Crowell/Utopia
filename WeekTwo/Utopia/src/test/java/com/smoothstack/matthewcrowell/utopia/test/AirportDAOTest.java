package com.smoothstack.matthewcrowell.utopia.test;

import com.smoothstack.matthewcrowell.utopia.UtopiaApp;
import com.smoothstack.matthewcrowell.utopia.dao.AirportDAO;
import com.smoothstack.matthewcrowell.utopia.entity.Airport;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AirportDAOTest extends TestCase {
	private UtopiaApp app;
	private AirportDAO adao;
	private Connection conn;
	private Airport airport;
	private List<Airport> airports;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		app = new UtopiaApp();
		conn = app.getConnUtil().getConnection("mysql");
		adao = new AirportDAO(conn);
		airport = new Airport();
		airport.setAirportCode("DUB");
		airport.setCityName("Dublin");
		airports = adao.getAirports();
	}

	@Test
	public void testAddAirport() {
		Boolean success = Boolean.FALSE;
		try {
			adao.addAirport(airport);
			conn.commit();

			airports = adao.getAirports();
			for (Airport ap : airports) {
				if (ap.getAirportCode().equals("DUB")) {
					success = Boolean.TRUE;
				}
			}
			adao.removeAirport(airport);
			conn.commit();
			assertTrue(success);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@Test
	public void testUpdateAirport() {
		Boolean success = Boolean.FALSE;
		try {
			adao.addAirport(airport);
			conn.commit();
			airport.setCityName("Baile Átha Cliath");
			adao.updateAirport(airport);
			conn.commit();
			airports = adao.getAirports();
			for (Airport ap : airports) {
				if (ap.getCityName().equals("Baile Átha Cliath")) {
					success = Boolean.TRUE;
				}
			}
			assertTrue(success);
			adao.removeAirport(airport);
			conn.commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

	}

	@Test
	public void testGetAirports() {
		Boolean success = Boolean.FALSE;
		try {
			airports = adao.getAirports();
			if (airports.size() >= 20) {
				success = Boolean.TRUE;
			}
			assertTrue(success);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@Test
	public void testRemoveAirport() {
		Boolean success = Boolean.FALSE;
		try {
			adao.addAirport(airport);
			conn.commit();
			airports = adao.getAirports();
			for (Airport ap : airports) {
				if (ap.getAirportCode().equals("DUB")) {
					success = !success;
				}
			}
			adao.removeAirport(airport);
			conn.commit();

			airports = adao.getAirports();
			for (Airport ap : airports) {
				if (ap.getAirportCode().equals("DUB")) {
					success = !success;
				}
			}
			assertTrue(success);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
}