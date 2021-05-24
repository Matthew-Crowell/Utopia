package com.smoothstack.matthewcrowell.utopia.dao;

import com.smoothstack.matthewcrowell.utopia.entity.Booking;
import com.smoothstack.matthewcrowell.utopia.entity.Flight;
import com.smoothstack.matthewcrowell.utopia.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to connect the Booking class with the persistence layer.
 *
 * @author matthew.crowell
 */
public class BookingDAO extends BaseDAO<Booking> {

	public BookingDAO(Connection conn) {
		super(conn);
	}

	/**
	 * Add a Booking to the persistence layer.
	 *
	 * @param booking Booking to add
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Boolean addBooking(Booking booking) throws SQLException, ClassNotFoundException {
		Boolean completedSuccessfully = Boolean.FALSE;
		save("INSERT INTO booking (`is_active`, `confirmation_code`) VALUES (?, ?);",
				new Object[]{booking.getIsActive(), booking.getConfirmationCode()});

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM booking WHERE confirmation_code = '" + booking.getConfirmationCode() + "'");
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		ResultSet results = pstmt.executeQuery();
		results.next();
		booking.setBookingNumber(results.getInt("id"));
		save("INSERT INTO booking_payment (`booking_id`, `stripe_id`, `refunded`) VALUES (?, ?, ?);",
				new Object[]{booking.getBookingNumber(), booking.getStripeId(), booking.getRefunded()});
		if (booking.getBookedBy().getRole().equals(1) || booking.getBookedBy().getRole().equals(3)) {
			save("INSERT INTO booking_agent (`booking_id`, `agent_id`) VALUES (?, ?)",
					new Object[]{booking.getBookingNumber(), booking.getBookedBy().getUserId()});
			save("insert into passenger (`booking_id`, `given_name`, `family_name`, `dob`, `address`, `gender`) " +
					"values(?, ?, ?, DATE_SUB(NOW(), interval 21 year)," +
					"'Unknown', 'Not Specified');", new Object[]
					{booking.getBookingNumber(), booking.getPassenger().getGivenName(),
							booking.getPassenger().getFamilyName()});
		} else if (booking.getBookedBy().getRole().equals(2)) {
			save("INSERT INTO booking_user (`booking_id`, `user_id`) VALUES (?, ?)",
					new Object[]{booking.getBookingNumber(), booking.getBookedBy().getUserId()});
			save("insert into passenger (`booking_id`, `given_name`, `family_name`, `dob`, `address`, `gender`) " +
					"values(?, ?, ?, DATE_SUB(NOW(), interval 21 year)," +
					"'Unknown', 'Not Specified');", new Object[]
					{booking.getBookingNumber(), booking.getBookedBy().getGivenName(),
							booking.getBookedBy().getFamilyName()});
		} else {
			save("INSERT INTO booking_guest (`booking_id`, `contact_email`, `contact_phone`) " +
							"VALUES (?, ?, ?)",
					new Object[]{booking.getBookingNumber(), booking.getBookedBy().getEmail(),
							booking.getBookedBy().getPhoneNumber()});
			save("INSERT INTO passenger booking_id = ?, given_name = ?, family_name = ?," +
					"dob = DATE_SUB(NOW(), interval 21 year), gender = 'Not Specified', " +
					"address = 'Unknown'", new Object[]
					{booking.getBookingNumber(), booking.getBookedBy().getGivenName(),
							booking.getBookedBy().getFamilyName()});
		}
		for (Flight flight : booking.getFlights()) {
			save("INSERT INTO flight_bookings (`flight_id`, `booking_id`) VALUES (?, ?);",
					new Object[]{flight.getId(), booking.getBookingNumber()});
		}
		completedSuccessfully = Boolean.TRUE;
		return completedSuccessfully;
	}

	/**
	 * Get a List of all Active Bookings in the persistence layer.
	 *
	 * @return List<Booking> containing all Bookings in database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Booking> getActiveBookings() throws SQLException, ClassNotFoundException {
		return read("SELECT * from booking where is_active = ?", new Object[]
				{ 1 });
	}

	/**
	 * Get a List of Bookings in the persistence layer.
	 *
	 * @return List<Booking> containing all Bookings in database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Booking> getInactiveBookings() throws SQLException, ClassNotFoundException {
		return read("SELECT * from booking where is_active = ?", new Object[]
				{ 0 });
	}

	/**
	 * Update an existing Booking in the persistence layer.
	 *
	 * @param booking Booking to update
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Boolean updateBooking(Booking booking) throws SQLException, ClassNotFoundException {
		Boolean completedSuccessfully = Boolean.FALSE;
		save("UPDATE booking SET is_active = ?, confirmation_code = ? WHERE id = ?;",
				new Object[]{booking.getIsActive(), booking.getConfirmationCode(), booking.getBookingNumber()});

		save("UPDATE booking_payment SET stripe_id = ?, refunded = ? WHERE booking_id = ?;",
				new Object[]{booking.getStripeId(), booking.getRefunded(), booking.getBookingNumber()});
		for (Flight flight : booking.getFlights()) {
			save("UPDATE flight_bookings SET flight_id = ? WHERE booking_id = ?;",
					new Object[]{flight.getId(), booking.getBookingNumber()});
		}
		completedSuccessfully = Boolean.TRUE;
		return completedSuccessfully;
	}

	/**
	 * Remove a Booking from the persistence layer.
	 *
	 * @param booking Booking to remove
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Boolean removeBooking(Booking booking) throws SQLException, ClassNotFoundException {
		Boolean completedSuccessfully = Boolean.FALSE;
		save("DELETE FROM booking WHERE id = ?", new Object[]{booking.getBookingNumber()});
		save("DELETE FROM booking_user WHERE booking_id = ?", new Object[]{booking.getBookingNumber()});
		save("DELETE FROM booking_payment WHERE booking_id = ?", new Object[]{booking.getBookingNumber()});
		save("DELETE FROM flight_bookings WHERE booking_id = ?", new Object[]{booking.getBookingNumber()});
		completedSuccessfully = Boolean.TRUE;
		return completedSuccessfully;
	}

	/**
	 * Function used internally to interpret Booking data from persistence layer.
	 *
	 * @param rs ResultSet from a SQL database query
	 * @return List<Booking> of all Bookings found in the persistence layer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public List<Booking> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Booking> bookings = new ArrayList<>();
		UserDAO udao = new UserDAO(conn);
		while (rs.next()) {
			Booking booking = new Booking();
			booking.setBookingNumber(rs.getInt("id"));
			booking.setIsActive(rs.getInt("is_active"));
			booking.setConfirmationCode(rs.getString("confirmation_code"));

			StringBuilder query = new StringBuilder();
			query.append("SELECT * FROM booking_user where booking_id = " + booking.getBookingNumber());
			PreparedStatement pstmt = conn.prepareStatement(query.toString());
			ResultSet results = pstmt.executeQuery();

			while (results.next()) {
				Integer userId = results.getInt("user_id");
				User user = new User();
				user.setUserId(userId);
				user = udao.findUser(user);
				booking.setBookedBy(user);
			}

			query.setLength(0);
			query.append("SELECT * FROM booking_payment where booking_id = " + booking.getBookingNumber());
			pstmt = conn.prepareStatement(query.toString());
			results = pstmt.executeQuery();

			while (results.next()) {
				booking.setStripeId(results.getString("stripe_id"));
				booking.setRefunded(results.getInt("refunded"));
			}

			FlightDAO fdao = new FlightDAO(conn);
			List<Flight> allFlights = fdao.getFlights();

			query.setLength(0);
			query.append("SELECT * FROM flight_bookings where booking_id = " + booking.getBookingNumber());
			pstmt = conn.prepareStatement(query.toString());
			results = pstmt.executeQuery();
			List<Flight> flights = new ArrayList<>();
			while (results.next()) {
				for (Flight flight : allFlights) {
					if (flight.getId().equals(results.getInt("flight_id"))) {
						flights.add(flight);
					}
				}
			}
			booking.setFlights(flights);
			bookings.add(booking);
		}

		return bookings;
	}
}
