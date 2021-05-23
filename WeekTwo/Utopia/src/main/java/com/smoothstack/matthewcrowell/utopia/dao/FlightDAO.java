package com.smoothstack.matthewcrowell.utopia.dao;

import com.smoothstack.matthewcrowell.utopia.entity.Flight;
import com.smoothstack.matthewcrowell.utopia.entity.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO extends BaseDAO<Flight> {

	public FlightDAO(Connection conn) {
		super(conn);
	}

	public Boolean addFlight(Flight flight) throws SQLException {
		Boolean completedSuccessfully = Boolean.FALSE;
		try {
			save("INSERT INTO flight (`route_id`, `airplane_id`, `departure_time`, " +
					"`reserved_seats`, `seat_price`) VALUES (?, ?, ?, ?, ?)", new Object[]{flight.getRoute(),
					flight.getAirplaneId(), flight.getDepartureDateTime(),
					flight.getReservedSeats(), flight.getPricePerSeat()});
			completedSuccessfully = Boolean.TRUE;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return completedSuccessfully;
	}

	public List<Flight> getFlights() throws SQLException, ClassNotFoundException {
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM flight;");
		List<Flight> flights = read(query.toString(), null);

		return flights;
	}

	public Boolean removeFlight(Flight flight) throws SQLException {
		Boolean completedSuccessfully = Boolean.FALSE;
		try{
			save("DELETE FROM flight WHERE id = '" + flight.getId() +"'", null);
			completedSuccessfully = Boolean.TRUE;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return completedSuccessfully;
	}

	public Boolean updateFlight(Flight flight) throws SQLException {
		Boolean completedSuccessfully = Boolean.FALSE;
		try {
			save("UPDATE flight SET route_id = ?, airplane_id = ?, departure_time = ?, " +
					"reserved_seats = ?, seat_price = ? WHERE id=?", new Object[]{flight.getRoute().getId(),
					flight.getAirplaneId(), flight.getDepartureDateTime(),
					flight.getReservedSeats(), flight.getPricePerSeat(), flight.getId()});
			completedSuccessfully = Boolean.TRUE;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return completedSuccessfully;
	}

	@Override
	public List<Flight> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Flight> flights = new ArrayList<>();

		Integer routeId = 0;
		RouteDAO rdao = new RouteDAO(conn);
		List<Route> routes = rdao.readAllRoutes();

		while (rs.next()) {
			Flight flight = new Flight();
			flight.setId(rs.getInt("id"));
			routeId = rs.getInt("route_id");
			for(Route route : routes){
				if(route.getId().equals(routeId)){
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
