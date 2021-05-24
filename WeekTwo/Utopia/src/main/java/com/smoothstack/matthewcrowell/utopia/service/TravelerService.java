package com.smoothstack.matthewcrowell.utopia.service;

import com.smoothstack.matthewcrowell.utopia.UtopiaApp;
import com.smoothstack.matthewcrowell.utopia.dao.BookingDAO;
import com.smoothstack.matthewcrowell.utopia.dao.FlightDAO;
import com.smoothstack.matthewcrowell.utopia.entity.Booking;
import com.smoothstack.matthewcrowell.utopia.entity.Flight;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class TravelerService {

	public Boolean addBooking(UtopiaApp app) throws SQLException {
		Boolean completedSuccessfully = Boolean.FALSE;
		Scanner scanner = new Scanner(System.in);
		Connection conn = null;
		List<Flight> tempFlights = new ArrayList<>();
		Integer option = 0;
		Booking booking = new Booking();
		booking.setIsActive(1);
		booking.setBookedBy(app.getUser());
		booking.setRefunded(0);
		booking.setStripeId("randomizedStringGoesHere");
		booking.setFlights(tempFlights);

		try {
			conn = app.getConnUtil().getConnection();
			FlightDAO fdao = new FlightDAO(conn);
			List<Flight> flights = fdao.getFlights();
			int counter = 1;
			int exit = -1;
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
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime ldt = LocalDateTime.now();
				Flight flight = flights.get(option - 1);
				booking.setConfirmationCode(formatter.format(ldt));
				System.out.println("Seat selection:");
				System.out.println("1) View Flight Details");
				System.out.println("2) First Class");
				System.out.println("3) Business Class");
				System.out.println("4) Economy Class");
				System.out.println("5) Quit to Previous");
				System.out.print("Selection: ");
				option = scanner.nextInt();
				if (option.equals(1)) {
					System.out.println(flight.toString());
				} else if (!option.equals(5)) {
					flight.setReservedSeats(flight.getReservedSeats() + 1);
					completedSuccessfully = fdao.updateFlight(flight);
					booking.getFlights().add(flight);
					BookingDAO bdao = new BookingDAO(conn);
					completedSuccessfully = bdao.addBooking(booking);
				}
			}
			conn.commit();
		} catch (SQLException | ClassNotFoundException e) {
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return completedSuccessfully;
	}

	/**
	 * @param app
	 * @return
	 * @throws SQLException
	 */
	public Boolean cancelBooking(UtopiaApp app) throws SQLException {
		Boolean completedSuccessfully = Boolean.FALSE;
		Scanner scanner = new Scanner(System.in);
		Connection conn = null;
		try {
			conn = app.getConnUtil().getConnection();
			BookingDAO bdao = new BookingDAO(conn);
			List<Booking> bookings = bdao.getActiveBookings();
			List<Booking> userBookings = new ArrayList<>();
			System.out.println("Your trips:");
			Integer counter = 1;
			for (Booking booking : bookings) {
				if (booking.getBookedBy().getUserId().equals(app.getUser().getUserId()) ||
						booking.getPassenger().getUserId().equals(app.getUser().getUserId())) {
					userBookings.add(booking);
					System.out.println(counter + ") " + booking.getFlights().get(0).getRoute().getOrigin().getCityName()
							+ " to " + booking.getFlights().get(0).getRoute().getDestination().getCityName());
					counter++;
				}

			}
			if (counter.equals(1)) {
				System.out.println("\tYou have no trips scheduled currently.");
			}
			System.out.println(counter + ") Quit to Previous");
			System.out.print("Selection: ");
			Integer option = scanner.nextInt();
			if (!option.equals(counter) & !option.equals(0)) {
				Booking booking = bookings.get(option - 1);
				booking.setIsActive(0);
				bdao.updateBooking(booking);
				FlightDAO fdao = new FlightDAO(conn);
				Flight flight = booking.getFlights().get(0);
				flight.setReservedSeats(flight.getReservedSeats() - 1);
				fdao.updateFlight(flight);
			}
			conn.commit();
			completedSuccessfully = Boolean.TRUE;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return completedSuccessfully;
	}
}
