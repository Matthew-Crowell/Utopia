package com.smoothstack.matthewcrowell.utopia.test;

import com.smoothstack.matthewcrowell.utopia.UtopiaApp;
import com.smoothstack.matthewcrowell.utopia.dao.FlightDAO;
import com.smoothstack.matthewcrowell.utopia.dao.RouteDAO;
import com.smoothstack.matthewcrowell.utopia.entity.Flight;
import com.smoothstack.matthewcrowell.utopia.entity.Route;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FlightDAOTest extends TestCase {
	private List<Route> routes;
	private RouteDAO rdao;
	private Flight flight;
	private List<Flight> flights;
	private UtopiaApp app;
	private FlightDAO fdao;
	private Connection conn;
	private Boolean success;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		flight = new Flight();
		app = new UtopiaApp();
		conn = app.getConnUtil().getConnection("mysql");
		fdao = new FlightDAO(conn);
		rdao = new RouteDAO(conn);
		routes = rdao.getRoutes();

		flight.setRoute(routes.get(7));
		flight.setReservedSeats(0);
		flight.setPricePerSeat(99.99);
		flight.setAirplaneId(10);
		flight.setMaxCapacity(100);
		flight.setDepartureDateTime("2021-12-20 13:37:00");

		success = Boolean.FALSE;
	}

	@Test
	public void testAddFlight() {
		try {
			fdao.addFlight(flight);
			conn.commit();

			flights = fdao.getFlights();
			for (Flight f : flights) {
				if (f.getRoute().getId() == flight.getRoute().getId() &&
						f.getDepartureDateTime().equals(flight.getDepartureDateTime())) {
					flight = f;
					success = Boolean.TRUE;
				}
			}
			assertTrue(success);
			fdao.removeFlight(flight);
			conn.commit();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetFlights() {
		try {
			flights = fdao.getFlights();
			if (flights.size() >= 4) {
				success = Boolean.TRUE;
			}
			assertTrue(success);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateFlight() {
		try {
			fdao.addFlight(flight);
			conn.commit();

			flights = fdao.getFlights();
			for (Flight f : flights) {
				if (f.getRoute().getId() == flight.getRoute().getId() &&
						f.getDepartureDateTime().equals(flight.getDepartureDateTime())) {
					flight = f;
				}
			}
			Route route = flight.getRoute();
			flight.setRoute(routes.get(30));
			fdao.updateFlight(flight);
			conn.commit();
			flights = fdao.getFlights();
			for (Flight f : flights) {
				if (f.getRoute().getId() == flight.getRoute().getId() &&
						f.getDepartureDateTime().equals(flight.getDepartureDateTime()) &&
						!route.equals(f.getRoute())) {
					success = !success;
				}
			}
			assertTrue(success);

			fdao.removeFlight(flight);
			conn.commit();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveFlight() {
		try {
			fdao.addFlight(flight);
			conn.commit();

			flights = fdao.getFlights();
			for (Flight f : flights) {
				if (f.getRoute().getId() == flight.getRoute().getId() &&
						f.getDepartureDateTime().equals(flight.getDepartureDateTime())) {
					flight = f;
					success = !success;
				}
			}
			fdao.removeFlight(flight);
			conn.commit();

			flights = fdao.getFlights();
			for (Flight f : flights) {
				if (f.getRoute().getId() == flight.getRoute().getId() &&
						f.getDepartureDateTime().equals(flight.getDepartureDateTime())) {
					success = !success;
				}
			}
			assertTrue(success);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}