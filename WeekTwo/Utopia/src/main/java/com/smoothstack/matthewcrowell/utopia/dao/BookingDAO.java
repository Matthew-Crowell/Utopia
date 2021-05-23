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

public class BookingDAO extends BaseDAO<Booking>{

	public BookingDAO(Connection conn) {
		super(conn);
	}

	public Boolean addBooking(Booking booking){
		Boolean completedSuccessfully = Boolean.FALSE;
		try {
			save("INSERT INTO booking (`is_active`, `confirmation_code`) VALUES (?, ?);",
					new Object[] { booking.getIsActive(), booking.getConfirmationCode() });

			StringBuilder query = new StringBuilder();
			query.append("SELECT * FROM booking WHERE confirmation_code = '" + booking.getConfirmationCode() +"'");
			PreparedStatement pstmt = conn.prepareStatement(query.toString());
			ResultSet results = pstmt.executeQuery();
			results.next();
			booking.setBookingNumber(results.getInt("id"));


			save("INSERT INTO booking_user (`booking_id`, `user_id`) VALUES (?, ?)",
					new Object[] { booking.getBookingNumber(), booking.getUser().getUserId() });
			save("INSERT INTO booking_payment (`booking_id`, `stripe_id`, `refunded`) VALUES (?, ?, ?);",
					new Object[] { booking.getBookingNumber(), booking.getStripeId(), booking.getRefunded() });
			for(Flight flight : booking.getFlights()){
				save("INSERT INTO flight_bookings (`flight_id`, `booking_id`) VALUES (?, ?);",
						new Object[] { flight.getId(), booking.getBookingNumber() });
			}
			completedSuccessfully = Boolean.TRUE;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return completedSuccessfully;
	}

	public List<Booking> getBookings() throws SQLException, ClassNotFoundException {
		return read("SELECT * from booking", null);
	}

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
			query.append("SELECT * FROM booking_user where booking_id = ?" + booking.getBookingNumber());
			PreparedStatement pstmt = conn.prepareStatement(query.toString());
			ResultSet results = pstmt.executeQuery();
			while(results.next()){
				Integer userId = results.getInt("user_id");
				User user = new User();
				user.setUserId(userId);
				user = udao.findUser(user);
				booking.setUser(user);
			}

			query.setLength(0);
			query.append("SELECT * FROM booking_payment where booking_id = ?" + booking.getBookingNumber());
			pstmt = conn.prepareStatement(query.toString());
			results = pstmt.executeQuery();
			while(results.next()){
				booking.setStripeId(results.getString("swipe_id"));
				booking.setRefunded(results.getInt("refunded"));
			}

			FlightDAO fdao = new FlightDAO(conn);
			List<Flight> allFlights = fdao.getFlights();

			query.setLength(0);
			query.append("SELECT * FROM flight_bookings where booking_id = ?" + booking.getBookingNumber());
			pstmt = conn.prepareStatement(query.toString());
			results = pstmt.executeQuery();
			List<Flight> flights = new ArrayList<>();
			while(results.next()){
				for(Flight flight : allFlights){
					if(flight.getId().equals(results.getInt("flight_id"))){
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
