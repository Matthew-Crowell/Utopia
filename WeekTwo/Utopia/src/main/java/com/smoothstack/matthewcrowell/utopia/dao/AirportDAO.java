package com.smoothstack.matthewcrowell.utopia.dao;

import com.smoothstack.matthewcrowell.utopia.entity.Airport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to grant persistence access to the Airport class.
 *
 * @author matthew.crowell
 */
public class AirportDAO extends BaseDAO<Airport> {

	public AirportDAO(Connection conn) {
		super(conn);
	}

	/**
	 * Add an Airport to the persistence layer.
	 *
	 * @param airport Airport to add
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addAirport(Airport airport) throws ClassNotFoundException, SQLException {
		save("INSERT into airport (iata_id, city) values (?, ?)",
				new Object[]{airport.getAirportCode(), airport.getCityName()});
	}

	/**
	 * Get a List of all Airports in the persistence layer.
	 *
	 * @return List<Airport> containing all Routes in database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Airport> getAirports() throws ClassNotFoundException, SQLException {
		return read("select * from airport", null);
	}

	/**
	 * Update an existing Airport in the persistence layer.
	 *
	 * @param airport Airport to update
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateAirport(Airport airport) throws ClassNotFoundException, SQLException {
		save("UPDATE airport set city = ? where iata_id = ? ", new Object[]{
				airport.getCityName(), airport.getAirportCode()});
	}

	/**
	 * Remove a Airport from the persistence layer.
	 *
	 * @param airport Airport to remove
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void removeAirport(Airport airport) throws ClassNotFoundException, SQLException {
		save("delete from airport where iata_id = ?", new Object[]{airport.getAirportCode()});
	}

	/**
	 * Function used internally to interpret Airport data from persistence layer.
	 *
	 * @param rs ResultSet from a SQL database query
	 * @return List<Airport> of all Airports found in the persistence layer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<Airport> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Airport> airports = new ArrayList<>();
		while (rs.next()) {
			Airport airport = new Airport();
			airport.setAirportCode(rs.getString("iata_id"));
			airport.setCityName(rs.getString("city"));
			airports.add(airport);
		}

		return airports;
	}

}