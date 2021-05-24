package com.smoothstack.matthewcrowell.utopia.dao;

import com.smoothstack.matthewcrowell.utopia.entity.Airport;
import com.smoothstack.matthewcrowell.utopia.entity.Route;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to connect the Route class with the persistence layer.
 *
 * @author matthew.crowell
 */
public class RouteDAO extends BaseDAO<Route> {

	public RouteDAO(Connection conn) {
		super(conn);
	}

	/**
	 * Add a Route to the persistence layer.
	 *
	 * @param route Route to add
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addRoute(Route route) throws ClassNotFoundException, SQLException {
		save("INSERT into route (origin_id, destination_id) values (?, ?)",
				new Object[]{route.getOrigin().getAirportCode(), route.getDestination().getAirportCode()});
	}

	/**
	 * Get a List of all Routes in the persistence layer.
	 *
	 * @return List<Route> containing all Routes in database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Route> getRoutes() throws ClassNotFoundException, SQLException {
		return read("select * from route", null);
	}

	/**
	 * Get a List of all Routes associated with a specific Airport's IATA Code.
	 *
	 * @param airportCode String of an Airport's IATA code
	 * @return List<Route> of Routes associated with airportCode
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Route> getRoutesByAirportCode(String airportCode) throws ClassNotFoundException, SQLException {
		return read("select * from route where origin_id = ? OR destination_id = ?",
				new Object[]{airportCode, airportCode});
	}

	/**
	 * Update an existing Route in the persistence layer.
	 *
	 * @param route Route to update
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateRoute(Route route) throws ClassNotFoundException, SQLException {
		save("UPDATE route set origin_id = ?, destination_id = ? where id = ?", new Object[]{
				route.getOrigin().getAirportCode(), route.getDestination().getAirportCode(), route.getId()});
	}

	/**
	 * Remove a Route from the persistence layer.
	 *
	 * @param route Route to remove
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void removeRoute(Route route) throws ClassNotFoundException, SQLException {
		save("delete from route where id = ?", new Object[]{route.getId()});
	}

	/**
	 * Function used internally to interpret Route data from persistence layer.
	 *
	 * @param rs ResultSet from a SQL database query
	 * @return List<Route> of all Routes found in the persistence layer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<Route> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Route> routes = new ArrayList<>();
		AirportDAO adao = new AirportDAO(conn);
		List<Airport> airports = adao.getAirports();

		while (rs.next()) {
			Route route = new Route();
			route.setId(rs.getInt("id"));
			StringBuilder originId = new StringBuilder();
			originId.append(rs.getString("origin_id"));
			StringBuilder destinationId = new StringBuilder();
			destinationId.append(rs.getString("destination_id"));

			for (Airport airport : airports) {
				if (airport.getAirportCode().equals(originId.toString())) {
					route.setOrigin(airport);
				} else if (airport.getAirportCode().equals(destinationId.toString())) {
					route.setDestination(airport);
				}
			}
			routes.add(route);
		}
		return routes;
	}

}