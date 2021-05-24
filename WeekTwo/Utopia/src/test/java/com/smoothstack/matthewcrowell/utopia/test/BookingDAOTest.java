package com.smoothstack.matthewcrowell.utopia.test;

import com.smoothstack.matthewcrowell.utopia.UtopiaApp;
import com.smoothstack.matthewcrowell.utopia.dao.BookingDAO;
import com.smoothstack.matthewcrowell.utopia.dao.FlightDAO;
import com.smoothstack.matthewcrowell.utopia.dao.UserDAO;
import com.smoothstack.matthewcrowell.utopia.entity.Booking;
import com.smoothstack.matthewcrowell.utopia.entity.Flight;
import com.smoothstack.matthewcrowell.utopia.entity.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOTest extends TestCase {
	private UtopiaApp app;
	private Connection conn;
	private BookingDAO bdao;
	private UserDAO udao;
	private FlightDAO fdao;
	private Booking booking;
	// booking
	private Integer bookingNumber;
	private Integer isActive;
	private String confirmationCode;
	// booking_user, booking_agent, or booking_guest
	private User bookedBy;
	private User passenger;
	// booking_payment
	private String stripeId;
	private Integer refunded;
	// flight_bookings
	private List<Flight> flights;
	private Boolean success;
	private List<Booking> bookings;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		app = new UtopiaApp();
		conn = app.getConnUtil().getConnection("mysql");
		bdao = new BookingDAO(conn);
		udao = new UserDAO(conn);
		fdao = new FlightDAO(conn);
		booking = new Booking();
		bookingNumber = 0;
		isActive = 1;
		confirmationCode = "test";
		bookedBy = udao.getUsers().get(5);
		passenger = bookedBy;
		stripeId = "A Randomized String Goes Here";
		refunded = 0;
		flights = new ArrayList<>();
		flights.add(fdao.getFlights().get(0));
		success = Boolean.FALSE;
		booking.setBookedBy(bookedBy);
		booking.setBookingNumber(66);
		booking.setRefunded(0);
		booking.setIsActive(1);
		booking.setPassenger(bookedBy);
		booking.setConfirmationCode(confirmationCode);
		booking.setFlights(flights);
		booking.setStripeId(stripeId);
	}

	@Test
	public void testAddBooking() throws SQLException {
		try {
			bdao.addBooking(booking);
			conn.commit();

			bookings = bdao.getActiveBookings();

			for (Booking b : bookings) {
				if (b.getBookingNumber().equals(booking.getBookingNumber())) {
					success = !success;
				}
			}
			assertTrue(success);

			bdao.removeBooking(booking);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	@Test
	public void testGetActiveBookings() throws SQLException, ClassNotFoundException {
		bookings = bdao.getActiveBookings();
		if (bookings.size() >= 1) {
			success = !success;
		}
		assertTrue(success);
	}

	@Test
	public void testGetInactivebookings() throws SQLException, ClassNotFoundException {
		bookings = bdao.getInactiveBookings();
		if (bookings.size() >= 1) {
			success = !success;
		}
		assertTrue(success);
	}

	@Test
	public void testUpdateBooking() throws SQLException, ClassNotFoundException {
		bdao.addBooking(booking);
		conn.commit();

		bookings = bdao.getActiveBookings();

		for (Booking b : bookings) {
			if (b.getBookingNumber().equals(booking.getBookingNumber())) {
				success = !success;
			}
		}
		booking.setStripeId("test");
		bdao.updateBooking(booking);
		conn.commit();
		bookings = bdao.getActiveBookings();

		for (Booking b : bookings) {
			if (b.getBookingNumber().equals(booking.getBookingNumber())) {
				if (b.getIsActive() == 1) {
					success = !success;
				}
			}
		}

		bdao.removeBooking(booking);
		conn.commit();
	}

	@Test
	public void testRemoveBooking() throws SQLException {
		try {
			bdao.addBooking(booking);
			conn.commit();

			bookings = bdao.getActiveBookings();

			bdao.removeBooking(booking);
			success = !success;
			conn.commit();

			bookings = bdao.getActiveBookings();

			for (Booking b : bookings) {
				if (b.getBookingNumber().equals(bookingNumber)) {
					success = !success;
				}
			}
			assertTrue(success);
		} catch (ClassNotFoundException | SQLException e) {
			conn.rollback();
		} finally {
			conn.close();
		}
	}
}