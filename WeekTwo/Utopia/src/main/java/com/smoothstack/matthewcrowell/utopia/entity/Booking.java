package com.smoothstack.matthewcrowell.utopia.entity;

import java.util.List;

public class Booking {

	private Integer bookingNumber;
	private Integer isActive;
	private String confirmationCode;
	private User userId;
	private String swipeId;
	private Integer refunded;
	private List<Flight> flights;

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getSwipeId() {
		return swipeId;
	}

	public void setSwipeId(String swipeId) {
		this.swipeId = swipeId;
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
