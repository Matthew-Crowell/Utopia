package com.smoothstack.matthewcrowell.utopia.entity;

import java.util.List;

public class Booking {
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

	public User getBookedBy() {
		return bookedBy;
	}

	public void setBookedBy(User bookedBy) {
		this.bookedBy = bookedBy;
	}

	public User getPassenger() {
		return passenger;
	}

	public void setPassenger(User passenger) {
		this.passenger = passenger;
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
		if (refunded >= 0 && refunded <= 1) {
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

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		if (passenger != null) {
			str.append(passenger.getGivenName() + " " + passenger.getFamilyName());
		} else {
			str.append(bookedBy.getGivenName() + " " + bookedBy.getFamilyName());
		}
		return "Booking{" +
				"bookingNumber=" + bookingNumber +
				", isActive=" + isActive +
				", confirmationCode='" + confirmationCode + "'" +
				", bookedBy=" + bookedBy.getGivenName() + " " + bookedBy.getFamilyName() +
				", passenger=" + str +
				", stripeId='" + stripeId + '\'' +
				", refunded=" + refunded +
				", flights=" + flights.get(0) +
				'}';
	}
}
