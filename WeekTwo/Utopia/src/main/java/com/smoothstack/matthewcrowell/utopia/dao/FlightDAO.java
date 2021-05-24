package com.smoothstack.matthewcrowell.utopia.dao;

import com.smoothstack.matthewcrowell.utopia.entity.Flight;
import com.smoothstack.matthewcrowell.utopia.entity.Route;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to connect the Flight class with the persistence layer.
 *
 * @author matthew.crowell
 */
public class FlightDAO extends BaseDAO<Flight> {

	public FlightDAO(Connection conn) {
		super(conn);
	}

	/**
	 * Add a Flight to the persistence layer.
	 *
	 * @param flight Flight to add
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Boolean addFlight(Flight flight) throws SQLException, ClassNotFoundException {
		Boolean completedSuccessfully = Boolean.FALSE;
		save("INSERT INTO flight (`route_id`, `airplane_id`, `departure_time`, " +
				"`reserved_seats`, `seat_price`) VALUES (?, ?, ?, ?, ?)", new Object[]{flight.getRoute().getId(),
				flight.getAirplaneId(), flight.getDepartureDateTime(),
				flight.getReservedSeats(), flight.getPricePerSeat()});
		completedSuccessfully = Boolean.TRUE;

		return completedSuccessfully;
	}

	/**
	 * Get a List of all Flights in the persistence layer.
	 *
	 * @return List<Flight> Flight all Bookings in database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Flight> getFlights() throws SQLException, ClassNotFoundException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM flight;");
		List<Flight> flights = read(query.toString(), null);

		return flights;
	}

	/**
	 * Update an existing Flight in the persistence layer.
	 *
	 * @param flight Flight to update
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Boolean updateFlight(Flight flight) throws SQLException, ClassNotFoundException {
		Boolean completedSuccessfully = Boolean.FALSE;
		save("UPDATE flight SET route_id = ?, airplane_id = ?, departure_time = ?, " +
				"reserved_seats = ?, seat_price = ? WHERE id=?", new Object[]{flight.getRoute().getId(),
				flight.getAirplaneId(), flight.getDepartureDateTime(),
				flight.getReservedSeats(), flight.getPricePerSeat(), flight.getId()});
		completedSuccessfully = Boolean.TRUE;

		return completedSuccessfully;
	}

	/**
	 * Remove a Flight from the persistence layer.
	 *
	 * @param flight Flight to remove
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Boolean removeFlight(Flight flight) throws SQLException, ClassNotFoundException {
		Boolean completedSuccessfully = Boolean.FALSE;
		save("DELETE FROM flight WHERE id = '" + flight.getId() + "'", null);
		completedSuccessfully = Boolean.TRUE;
		return completedSuccessfully;
	}

	/**
	 * Function used internally to interpret Flight data from persistence layer.
	 *
	 * @param rs ResultSet from a SQL database query
	 * @return List<Flight> of all Flights found in the persistence layer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<Flight> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Flight> flights = new ArrayList<>();

		Integer routeId = 0;
		RouteDAO rdao = new RouteDAO(conn);
		List<Route> routes = rdao.getRoutes();

		while (rs.next()) {
			Flight flight = new Flight();
			flight.setId(rs.getInt("id"));
			routeId = rs.getInt("route_id");
			for (Route route : routes) {
				if (route.getId().equals(routeId)) {
					flight.setRoute(route);
				}
			}
			flight.setAirplaneId(rs.getInt("airplane_id"));
			flight.setDepartureDateTime(rs.getString("departure_time"));
			flight.setReservedSeats(rs.getInt("reserved_seats"));
			flight.setPricePerSeat(rs.getDouble("seat_price"));
			flights.add(flight);
		}
		return flights;
	}
}
