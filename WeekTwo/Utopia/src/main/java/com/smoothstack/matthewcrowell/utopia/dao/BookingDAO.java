package com.smoothstack.matthewcrowell.utopia.dao;

import com.smoothstack.matthewcrowell.utopia.entity.Booking;
import com.smoothstack.matthewcrowell.utopia.entity.Flight;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookingDAO extends BaseDAO<Booking>{

	public BookingDAO(Connection conn) {
		super(conn);
	}

	public void addBooking(Booking booking){
		try {
			save("INSERT INTO booking (`is_active`, `confirmation_code`) VALUES (?, ?);",
					new Object[] { booking.getIsActive(), booking.getConfirmationCode() });
			for(Flight flight : booking.getFlights()){
				save("INSERT INTO flight_booking (`flight_id`, `booking_id`) VALUES (?, ?);",
						new Object[] { flight.getId(), booking.getBookingNumber() });
			}

			save("INSERT INTO booking (`is_active`, `confirmation_code`) VALUES (?, ?);",
					new Object[] { booking.getIsActive(), booking.getConfirmationCode() });
			save("INSERT INTO booking (`is_active`, `confirmation_code`) VALUES (?, ?);",
					new Object[] { booking.getIsActive(), booking.getConfirmationCode() });
			save("INSERT INTO booking (`is_active`, `confirmation_code`) VALUES (?, ?);",
					new Object[] { booking.getIsActive(), booking.getConfirmationCode() });
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@Override
	public List<Booking> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		return null;
	}
}
