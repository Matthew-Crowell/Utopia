package com.smoothstack.matthewcrowell.utopia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smoothstack.matthewcrowell.utopia.entity.Airport;
import com.smoothstack.matthewcrowell.utopia.entity.Route;

public class RouteDAO extends BaseDAO<Route> {

	public RouteDAO(Connection conn) {
		super(conn);
	}

	public void addRoute(Route route) throws ClassNotFoundException, SQLException {
		save("INSERT into route (origin_id, destination_id) values (?, ?)",
				new Object[] { route.getOrigin().getAirportCode(), route.getDestination().getAirportCode() });
	}

	public void updateRoute(Route route) throws ClassNotFoundException, SQLException {
		save("UPDATE route set origin_id = ?, destination_id = ? where id = ?", new Object[] {
				route.getOrigin().getAirportCode(), route.getDestination().getAirportCode(), route.getId() });
	}

	public void deleteRoute(Route route) throws ClassNotFoundException, SQLException {
		save("delete from route where id = ?", new Object[] { route.getId() });
	}

	public List<Route> readAllRoutes() throws ClassNotFoundException, SQLException {
		return read("select * from route", null);
	}

	public List<Route> readAllRoutesByAirportCode(String airportCode) throws ClassNotFoundException, SQLException {
		return read("select * from route where origin_id = ? OR destination_id = ?",
				new Object[] {airportCode, airportCode});
	}

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

			for(Airport airport : airports){
//				System.out.println("Setting origin and destination for " + airport.getAirportCode());
				if(airport.getAirportCode().equals(originId.toString())) {
//					System.out.println("Setting " + route.getId() + " origin to " + airport.getAirportCode());
					route.setOrigin(airport);
				} else if(airport.getAirportCode().equals(destinationId.toString())){
//					System.out.println("Setting " + route.getId() + " destination to " + airport.getAirportCode());
					route.setDestination(airport);
				}
			}
			routes.add(route);
		}
		return routes;
	}

}