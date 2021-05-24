package com.smoothstack.matthewcrowell.utopia.service;

import com.smoothstack.matthewcrowell.utopia.UtopiaApp;
import com.smoothstack.matthewcrowell.utopia.dao.FlightDAO;
import com.smoothstack.matthewcrowell.utopia.entity.Flight;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EmployeeService {

	public List<Flight> displayflights(UtopiaApp app){
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
			conn.close();
		}
		return success;
	}
}
