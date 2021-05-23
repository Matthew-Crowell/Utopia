package com.smoothstack.matthewcrowell.utopia.service;

import com.smoothstack.matthewcrowell.utopia.UtopiaApp;
import com.smoothstack.matthewcrowell.utopia.dao.FlightDAO;
import com.smoothstack.matthewcrowell.utopia.entity.Booking;
import com.smoothstack.matthewcrowell.utopia.entity.Flight;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TravelerService {

	ConnectionUtil connUtil = new ConnectionUtil();

	public Boolean addBooking(UtopiaApp app) {
		Boolean completedSuccessfully = Boolean.FALSE;
		Scanner scanner = new Scanner(System.in);
		Connection conn = null;
		Integer option = 0;
		Booking booking = new Booking();
		booking.setIsActive(1);
		booking.setUserId(app.getUser());
		booking.setRefunded(0);
		try {
			conn = app.getConnUtil().getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			List<Flight> flights = fdao.getFlights();
			int counter = 1;
			int exit = -1;
			while (!option.equals(exit)) {
				System.out.println("Choose your flight:");
				for (Flight flight : flights) {
					System.out.println(counter + ") From " +
							flight.getRoute().getOrigin().getCityName() + " to " +
							flight.getRoute().getDestination().getCityName()
							+ " on " + flight.getDepartureDateTime());
					counter++;
				}
				exit = counter;
				System.out.println(exit + ") Quit to Previous");
				System.out.print("Selection: ");
				option = scanner.nextInt();
				if (!option.equals(exit)) {
					Flight flight = flights.get(option - 1);
					System.out.println("Seat selection:");
					System.out.println("1) View Flight Details");
					System.out.println("2) First Class");
					System.out.println("3) Business Class");
					System.out.println("4) Economy Class");
					System.out.println("5) Quit to Previous");
					System.out.print("Selection: ");
					option = scanner.nextInt();
					if (!option.equals(5)) {
						flight.setReservedSeats(flight.getReservedSeats() + 1);
						completedSuccessfully = fdao.updateFlight(flight);
					}
				}
				counter = 1;
			}
			conn.commit();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
			}
		}
		return completedSuccessfully;
	}
}
