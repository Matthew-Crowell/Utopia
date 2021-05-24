package com.smoothstack.matthewcrowell.utopia.entity;


/**
 *
 */
public class Flight {
	private Integer id;
	private Route route;
	private Integer airplaneId;
	private Integer maxCapacity;
	private String departureDateTime;
	private Integer reservedSeats;
	private Double pricePerSeat;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Integer getAirplaneId() {
		return airplaneId;
	}

	public void setAirplaneId(Integer airplaneId) {
		this.airplaneId = airplaneId;
	}

	public Integer getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(Integer maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public String getDepartureDateTime() {
		return departureDateTime;
	}

	public void setDepartureDateTime(String departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public Integer getReservedSeats() {
		return reservedSeats;
	}

	public void setReservedSeats(Integer reservedSeats) {
		this.reservedSeats = reservedSeats;
	}

	public Double getPricePerSeat() {
		return pricePerSeat;
	}

	public void setPricePerSeat(Double pricePerSeat) {
		this.pricePerSeat = pricePerSeat;
	}

	@Override
	public String toString() {
		return "From " + route.getOrigin().getAirportCode() + ", " + route.getOrigin().getCityName() +
				" to " + route.getDestination().getAirportCode() + ", " + route.getDestination().getCityName()
				+ ", departing on " + departureDateTime + ".";
	}
}
