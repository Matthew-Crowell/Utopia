package com.smoothstack.matthewcrowell.utopia.entity;

import java.util.List;

public class Booking {
	// booking
	private Integer bookingNumber;
	private Integer isActive;
	private String confirmationCode;
	// booking_user
	private User user;
	// booking_payment
	private String stripeId;
	private Integer refunded;
	// flight_bookings
	private List<Flight> flights;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStripeId() {
		return stripeId;
	}

	public void setStripeId(String swipeId) {
		this.stripeId = swipeId;
	}

	public Integer getRefunded() {
		return refunded;
	}

	public void setRefunded(Integer refunded) {
		if(refunded >= 0 && refunded <= 1) {
			this.refunded = refunded;
		}
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	public Integer getBookingNumber() {
		return bookingNumber;
	}

	public void setBookingNumber(Integer bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}


}
