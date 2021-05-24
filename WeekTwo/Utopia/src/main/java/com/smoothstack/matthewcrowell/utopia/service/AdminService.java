package com.smoothstack.matthewcrowell.utopia.service;

import com.smoothstack.matthewcrowell.utopia.UtopiaApp;
import com.smoothstack.matthewcrowell.utopia.dao.*;
import com.smoothstack.matthewcrowell.utopia.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminService {

	public List<Flight> getFlights(UtopiaApp app) {
		List<Flight> flights = null;
		try {
			Connection conn = app.getConnUtil().getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			flights = fdao.getFlights();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return flights;
	}

	public Boolean updateFlight(UtopiaApp app, Flight flight) throws SQLException {
		Connection conn = null;
		Boolean success = Boolean.FALSE;
		try {
			conn = app.getConnUtil().getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			fdao.updateFlight(flight);
			success = !success;
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return success;
	}

	public Boolean addFlight(UtopiaApp app, Flight flight) throws SQLException {
		Connection conn = null;
		Boolean success = Boolean.FALSE;
		try {
			conn = app.getConnUtil().getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			fdao.addFlight(flight);
			success = !success;
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return success;
	}

	public Boolean deleteFlight(UtopiaApp app, Flight flight) throws SQLException {
		Connection conn = null;
		Boolean success = Boolean.FALSE;
		try {
			conn = app.getConnUtil().getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			fdao.removeFlight(flight);
			conn.commit();
			success = !success;
		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return success;
	}

	public List<Booking> getBookings(UtopiaApp app) throws SQLException {
		List<Booking> bookings = null;
		Connection conn = null;
		try {
			conn = app.getConnUtil().getConnection();
			BookingDAO bdao = new BookingDAO(conn);
			bookings = bdao.getActiveBookings();
		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return bookings;
	}

	public Boolean cancelBooking(UtopiaApp app, Booking booking) throws SQLException {
		Boolean success = Boolean.FALSE;
		Connection conn = null;
		BookingDAO bdao = null;
		FlightDAO fdao = null;
		Flight flight = booking.getFlights().get(0);
		flight.setReservedSeats(flight.getReservedSeats() - 1);
		booking.setIsActive(0);
		booking.setRefunded(1);
		try {
			conn = app.getConnUtil().getConnection();
			bdao = new BookingDAO(conn);
			bdao.updateBooking(booking);
			conn.commit();
			success = !success;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return success;
	}

	public Boolean updateBooking(UtopiaApp app, Booking booking) throws SQLException {
		Boolean success = Boolean.FALSE;
		Connection conn = null;
		BookingDAO bdao = null;
		try {
			conn = app.getConnUtil().getConnection();
			bdao = new BookingDAO(conn);
			bdao.updateBooking(booking);
			conn.commit();
			success = !success;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return success;
	}

	public User findUser(UtopiaApp app, String username) {
		User user = null;
		Connection conn;
		UserDAO udao;
		try {
			conn = app.getConnUtil().getConnection();
			udao = new UserDAO(conn);
			user = new User();
			user.setUserId(-1);
			user.setUsername(username);
			user = udao.findUser(user);
		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
		}
		return user;
	}

	public User findUser(UtopiaApp app, Integer userID) throws SQLException {
		User user = null;
		Connection conn = null;
		UserDAO udao;
		try {
			conn = app.getConnUtil().getConnection();
			udao = new UserDAO(conn);
			user = new User();
			user.setUserId(userID);
			user.setUsername("null");
			user = udao.findUser(user);
		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return user;
	}

	public List<Route> getRoutes(UtopiaApp app) throws SQLException {
		List<Route> routes = null;
		RouteDAO rdao;
		Connection conn = null;
		try {
			conn = app.getConnUtil().getConnection();
			rdao = new RouteDAO(conn);
			routes = rdao.getRoutes();
		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return routes;
	}

	public void addRoute(UtopiaApp app, Route route) throws SQLException {
		RouteDAO rdao = null;
		Connection conn = null;
		try {
			conn = app.getConnUtil().getConnection();
			rdao = new RouteDAO(conn);
			rdao.addRoute(route);
			conn.commit();
		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteRoute(UtopiaApp app, Route route) throws SQLException {
		RouteDAO rdao = null;
		Connection conn = null;
		List<Route> routes = null;
		try {
			conn = app.getConnUtil().getConnection();
			rdao = new RouteDAO(conn);
			routes = rdao.getRoutes();
			for (Route r : routes) {
				if (r.getOrigin().getAirportCode().equals(route.getOrigin().getAirportCode())
						&& r.getDestination().getAirportCode().equals(route.getDestination().getAirportCode())) {
					route = r;
				}
			}
			rdao.removeRoute(route);
			conn.commit();
		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void addAirport(UtopiaApp app, Airport airport) throws SQLException {
		Connection conn = null;
		AirportDAO adao = null;
		try {
			conn = app.getConnUtil().getConnection();
			adao = new AirportDAO(conn);
			adao.addAirport(airport);
			conn.commit();
		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void deleteAirport(UtopiaApp app, Airport airport) throws SQLException {
		Connection conn = null;
		AirportDAO adao = null;
		try {
			conn = app.getConnUtil().getConnection();
			adao = new AirportDAO(conn);
			adao.removeAirport(airport);
			conn.commit();
		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void addUser(UtopiaApp app, User user) throws SQLException {
		Connection conn = null;
		UserDAO udao = null;
		try {
			conn = app.getConnUtil().getConnection();
			udao = new UserDAO(conn);
			udao.addUser(user);
			conn.commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void deleteUser(UtopiaApp app, User user) throws SQLException {
		Connection conn = null;
		UserDAO udao = null;
		try {
			conn = app.getConnUtil().getConnection();
			udao = new UserDAO(conn);
			udao.removeUser(user);
			conn.commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
