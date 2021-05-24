package com.smoothstack.matthewcrowell.utopia.test;

import com.smoothstack.matthewcrowell.utopia.UtopiaApp;
import com.smoothstack.matthewcrowell.utopia.dao.RouteDAO;
import com.smoothstack.matthewcrowell.utopia.entity.Airport;
import com.smoothstack.matthewcrowell.utopia.entity.Route;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RouteDAOTest extends TestCase {
	private RouteDAO rdao;
	private Route route;
	private Airport origin;
	private Airport destination;
	private UtopiaApp app;
	private Connection conn;
	private List<Route> routes;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		app = new UtopiaApp();
		conn = app.getConnUtil().getConnection("mysql");
		rdao = new RouteDAO(conn);
		route = new Route();
		origin = new Airport();
		origin.setCityName("Nashville");
		origin.setAirportCode("BNA");
		destination = new Airport();
		destination.setCityName("Panama City");
		destination.setAirportCode("ECP");
		route.setOrigin(origin);
		route.setDestination(destination);
		routes = rdao.getRoutes();
	}

	@Test
	public void testAddRoute() {
		Boolean foundRoute = Boolean.FALSE;
		List<Route> routes2 = null;
		try {
			rdao.addRoute(route);
			conn.commit();
			routes2 = rdao.getRoutes();
			for (Route r : routes2) {
				if (r.getOrigin().getAirportCode().equals("BNA") && r.getDestination().getAirportCode().equals("ECP")) {
					foundRoute = Boolean.TRUE;
				}
			}

			assertTrue(foundRoute);

			rdao.removeRoute(route);
			conn.commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@Test
	public void testUpdateRoute() throws SQLException, ClassNotFoundException {
		Boolean updatedSuccessfully = Boolean.FALSE;
		try {
			rdao.addRoute(route);
			conn.commit();
			routes = rdao.getRoutes();

			for (Route r : routes) {
				if (r.getOrigin().getAirportCode().equals("BNA") &&
						r.getDestination().getAirportCode().equals("ECP")) {
					route = r;
					route.getDestination().setAirportCode("MIA");
					rdao.updateRoute(route);
					conn.commit();
				}
			}

			routes = rdao.getRoutes();
			for(Route r : routes){
				if(r.getOrigin().getAirportCode().equals("BNA") &&
				r.getDestination().getAirportCode().equals("MIA")){
					updatedSuccessfully = Boolean.TRUE;
				}
			}
			rdao.removeRoute(route);
			conn.commit();
			assertTrue(updatedSuccessfully);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetRoutes() {
		try {
			routes = rdao.getRoutes();
			assertTrue(routes.size() > 100);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@Test
	public void testGetRoutesByAirportCode() {
		List<Route> routes = null;

		try {
			routes = rdao.getRoutesByAirportCode("BNA");
			assertTrue(routes.size() >= 8);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@Test
	public void testRemoveRoute() {
		Boolean addedSuccessfully = Boolean.FALSE;
		Boolean removedSuccessfully = Boolean.FALSE;
		Route route2 = null;
		try {
			rdao.addRoute(route);
			conn.commit();
			routes = rdao.getRoutesByAirportCode("BNA");

			for(Route r : routes){
				if(r.getOrigin().getAirportCode().equals("BNA") &&
				r.getDestination().getAirportCode().equals("ECP")){
					route = r;
					addedSuccessfully = Boolean.TRUE;
				}
			}

			rdao.removeRoute(route);
			conn.commit();
			removedSuccessfully = Boolean.TRUE;
			routes = rdao.getRoutesByAirportCode("BNA");
			for(Route r : routes){
				if(r.getOrigin().getAirportCode().equals("BNA") &&
						r.getDestination().getAirportCode().equals("ECP")) {
					removedSuccessfully = Boolean.FALSE;
				}
			}

			assertTrue(addedSuccessfully && removedSuccessfully);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}