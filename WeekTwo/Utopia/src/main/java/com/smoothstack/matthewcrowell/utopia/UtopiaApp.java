package com.smoothstack.matthewcrowell.utopia;

import com.smoothstack.matthewcrowell.utopia.entity.*;
import com.smoothstack.matthewcrowell.utopia.service.AdminService;
import com.smoothstack.matthewcrowell.utopia.service.ConnectionUtil;
import com.smoothstack.matthewcrowell.utopia.service.EmployeeService;
import com.smoothstack.matthewcrowell.utopia.service.TravelerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class UtopiaApp {
	private final ConnectionUtil connUtil = new ConnectionUtil();
	private User user;

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		UtopiaApp app = new UtopiaApp();
		Scanner scanner = new Scanner(System.in);
		app.login(scanner);
	}

	public ConnectionUtil getConnUtil() {
		return connUtil;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param scanner
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void login(Scanner scanner) throws SQLException, ClassNotFoundException {
		System.out.println("Welcome to Utopia Airlines.");
		System.out.print("Username: ");
		String username = scanner.nextLine();
		System.out.print("Password: ");
		String password = scanner.nextLine();

		/* Get username, password, and user role */
		ConnectionUtil connUtil = new ConnectionUtil();
		Connection conn = connUtil.getConnection();

		String query = "SELECT * FROM user WHERE username = '" + username + "'";
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		/* End direct database query */

		Boolean loggedIn = Boolean.FALSE;
		if (username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
			loggedIn = !loggedIn;
			user = new User();
			user.setUserId(rs.getInt("id"));
			user.setRole(rs.getInt("role_id"));
			user.setGivenName(rs.getString("given_name"));
			user.setFamilyName(rs.getString("family_name"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPhoneNumber(rs.getString("phone"));

			conn.close();

			if (user.getRole().equals(1)) {
				displayEmployeeMenu(scanner);
			}

			if (user.getRole().equals(2)) {
				displayTravelerMenu(scanner);
			}

			if (user.getRole().equals(3)) {
				displayAdminMenu(scanner);
			}
		} else {
			System.out.println("Unable to login.  Please try again.");
		}
	}

	/**
	 * @param scanner
	 */
	private void displayTravelerMenu(Scanner scanner) {
		Integer option = 0;
		System.out.println("Welcome, Traveler!\n");
		while (!option.equals(3)) {
			System.out.println("Main Menu");
			System.out.println("1) Book a Ticket");
			System.out.println("2) Cancel an Upcoming Trip");
			System.out.println("3) Logout");
			System.out.print("Selection: ");
			option = scanner.nextInt();

			switch (option) {
				case (1):

					travelerBookTicket(this, scanner);

					break;
				case (2):
					travelerCancelTicket(scanner);
					break;
				case (3):
					break;
				default:
					break;
			}

		}
	}

	/**
	 * @param scanner
	 */
	private void travelerCancelTicket(Scanner scanner) {
		TravelerService travelerService = new TravelerService();
		try {
			travelerService.cancelBooking(this);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

	}

	/**
	 * @param app
	 * @param scanner
	 */
	private void travelerBookTicket(UtopiaApp app, Scanner scanner) {
		try {
			TravelerService travelerService = new TravelerService();
			travelerService.addBooking(app);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}


	}

	/**
	 * @param scanner
	 */
	private void displayEmployeeMenu(Scanner scanner) {
		Integer option = 0;
		while (!option.equals(2)) {
			System.out.println("Welcome, Employee!");
			System.out.println("1) View Managed Flights");
			System.out.println("2) Quit to Previous");
			System.out.print("Selection: ");
			option = scanner.nextInt();
			if (option.equals(1)) {
				displayEmployeeFlights(scanner);
			}
		}

	}

	/**
	 * @param scanner
	 */
	private void displayEmployeeFlights(Scanner scanner) {
		Integer option = 0;
		Integer counter = 1;

		System.out.println("Flights:");

		EmployeeService employeeService = new EmployeeService();
		List<Flight> flights = employeeService.displayflights(this);
		for (Flight flight : flights) {
			System.out.println(counter + ") " + flight);
			counter++;
		}

		System.out.println(counter + ") Quit to Previous");
		System.out.print("Selection: ");
		option = scanner.nextInt();
		if (!option.equals(counter)) {
			employeeFlightOptions(flights.get(option - 1), scanner);
		}
	}

	/**
	 * @param flight
	 * @param scanner
	 */
	private void employeeFlightOptions(Flight flight, Scanner scanner) {
		System.out.println("Options:");
		System.out.println("1) View Flight Details");
		System.out.println("2) Update Flight Details");
		System.out.println("3) Add Seats to Flight");
		System.out.println("4) Quit to Previous");
		System.out.print("Selection: ");
		Integer option = scanner.nextInt();
		if (option.equals(1)) {
			System.out.println(flight);
			System.out.println("\tReserved Seats: " + flight.getReservedSeats());
		} else if (option.equals(2)) {
			updateFlightDetails(flight, scanner);
		} else if (option.equals(3)) {
			addSeatsToFlight(flight, scanner);
		}
	}

	/**
	 * @param flight
	 * @param scanner
	 */
	private void addSeatsToFlight(Flight flight, Scanner scanner) {
		Integer option = 0;
		System.out.print("Enter new Maximum Capacity: ");
		option = scanner.nextInt();

		EmployeeService employeeService = new EmployeeService();
		try {
			employeeService.updateFlight(this, flight);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	/**
	 * @param flight
	 * @param scanner
	 */
	private void updateFlightDetails(Flight flight, Scanner scanner) {
		scanner.nextLine();
		StringBuilder option = new StringBuilder();
		System.out.print("Please enter new Route: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		if (option.length() > 0) {
			Route route = new Route();
			route.setId(Integer.parseInt(option.toString()));
			flight.setRoute(route);
		}

		System.out.print("Please enter new Airplane ID: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		if (option.length() > 0) {
			flight.setAirplaneId(Integer.parseInt(option.toString()));
		}

		System.out.print("Please enter new Departure Date and Time: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		if (option.length() > 0) {
			flight.setDepartureDateTime(option.toString());
		}
		try {
			EmployeeService employeeService = new EmployeeService();
			employeeService.updateFlight(this, flight);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param scanner
	 */
	private void displayAdminMenu(Scanner scanner) {
		Integer input = 0;
		while (input != 7) {
			System.out.println("Administrator Menu");
			System.out.println("1) Edit Flights");
			System.out.println("2) Edit Bookings");
			System.out.println("3) Edit Airports");
			System.out.println("4) Edit Routes");
			System.out.println("5) Edit Users");
			System.out.println("6) Return to Menu");
			System.out.print("Selection: ");
			input = scanner.nextInt();
			switch (input) {
				case 1:
					adminEditFlights(scanner);
					break;
				case 2:
					adminEditBookings(scanner);
				case 3:
					adminEditAirports(scanner);
					break;
				case 4:
					adminEditRoutes(scanner);
					break;
				case 5:
					adminEditUsers(scanner);
					break;
				default:
					break;
			}
		}

	}

	/**
	 * @param scanner
	 */
	private void adminEditUsers(Scanner scanner) {
		StringBuilder input = new StringBuilder();

		scanner.nextLine();

		System.out.println("Users Menu");
		System.out.println("1) Add User");
		System.out.println("2) Delete User");
		System.out.println("3) Return to Menu");
		System.out.print("Selection: ");
		input.append(scanner.nextLine());
		if (input.toString().equals("1")) {
			adminAddUser(scanner);
		} else if (input.toString().equals("2")) {
			adminDeleteUser(scanner);
		}
	}

	/**
	 * @param scanner
	 */
	private void adminDeleteUser(Scanner scanner) {
		AdminService adminService = new AdminService();
		StringBuilder input = new StringBuilder();

		System.out.println("User Deletion Menu");
		System.out.println("1) Delete by User ID");
		System.out.println("2) Delete by Username");
		System.out.println("3) Return to Menu");
		System.out.print("Selection: ");
		input.append(scanner.nextLine());

		if (input.toString().equals("1")) {
			input.setLength(0);
			System.out.print("Enter User ID: ");
			input.append(scanner.nextLine());
			try {
				adminService.deleteUser(this, adminService.findUser(this, Integer.parseInt(input.toString())));
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		} else if (input.toString().equals("2")) {
			input.setLength(0);
			System.out.print("Enter Username: ");
			input.append(scanner.nextLine());
			try {
				adminService.deleteUser(this, adminService.findUser(this, input.toString()));
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
	}

	/**
	 * @param scanner
	 */
	private void adminAddUser(Scanner scanner) {
		AdminService adminService = new AdminService();
		StringBuilder input = new StringBuilder();

		User user = new User();
		System.out.print("Enter Role ID: ");
		input.append(scanner.nextLine());
		user.setRole(Integer.parseInt(input.toString()));

		System.out.print("Enter Given Name: ");
		input.setLength(0);
		input.append(scanner.nextLine());
		user.setGivenName(input.toString());

		System.out.print("Enter Family Name: ");
		input.setLength(0);
		input.append(scanner.nextLine());
		user.setFamilyName(input.toString());

		System.out.print("Enter Username: ");
		input.setLength(0);
		input.append(scanner.nextLine());
		user.setUsername(input.toString());

		System.out.print("Enter Email Address: ");
		input.setLength(0);
		input.append(scanner.nextLine());
		user.setEmail(input.toString());

		System.out.print("Enter Password: ");
		input.setLength(0);
		input.append(scanner.nextLine());
		user.setNewPassword(input.toString());

		System.out.print("Enter Phone Number: ");
		input.setLength(0);
		input.append(scanner.nextLine());
		user.setPhoneNumber(input.toString());

		try {
			adminService.addUser(this, user);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	/**
	 * @param scanner
	 */
	private void adminEditAirports(Scanner scanner) {
		StringBuilder input = new StringBuilder();

		scanner.nextLine();

		System.out.println("Airports Menu");
		System.out.println("1) Add Airport");
		System.out.println("2) Delete Airport");
		System.out.println("3) Return to Menu");
		System.out.print("Selection: ");
		input.append(scanner.nextLine());
		if (input.toString().equals("1")) {
			adminAddAirport(scanner);
		} else if (input.toString().equals("2")) {
			adminDeleteAirport(scanner);
		}
	}

	/**
	 * @param scanner
	 */
	private void adminDeleteAirport(Scanner scanner) {
		AdminService adminService = new AdminService();
		StringBuilder input = new StringBuilder();

		Airport airport = new Airport();
		System.out.print("Enter IATA Code: ");
		input.append(scanner.nextLine());
		airport.setAirportCode(input.toString());

		try {
			adminService.deleteAirport(this, airport);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	/**
	 * @param scanner
	 */
	private void adminAddAirport(Scanner scanner) {
		AdminService adminService = new AdminService();
		StringBuilder input = new StringBuilder();

		Airport airport = new Airport();
		System.out.print("Enter IATA Code: ");
		input.append(scanner.nextLine());
		airport.setAirportCode(input.toString());

		System.out.print("Enter City: ");
		input.setLength(0);
		input.append(scanner.nextLine());
		airport.setCityName(input.toString());

		try {
			adminService.addAirport(this, airport);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	/**
	 * @param scanner
	 */
	private void adminEditRoutes(Scanner scanner) {
		AdminService adminService = new AdminService();
		StringBuilder input = new StringBuilder();
		System.out.println("Routes Menu");
		System.out.println("1) Add Route");
		System.out.println("2) Delete Route");
		System.out.println("3) Back to Menu");
		input.append(scanner.nextInt());
		if (input.toString().equals("1")) {
			adminAddRoute(scanner);
		} else if (input.toString().equals("2")) {
			adminDeleteRoute(scanner);
		}
	}

	/**
	 * @param scanner
	 */
	private void adminDeleteRoute(Scanner scanner) {
		AdminService adminService = new AdminService();
		StringBuilder input = new StringBuilder();

		Airport origin = new Airport();
		Airport destination = new Airport();

		scanner.nextLine();

		System.out.print("Enter Origin IATA Code: ");
		input.append(scanner.nextLine());
		origin.setAirportCode(input.toString());
		input.setLength(0);

		System.out.print("Enter Destination IATA Code: ");
		input.append(scanner.nextLine());
		destination.setAirportCode(input.toString());
		input.setLength(0);

		Route route = new Route();
		route.setOrigin(origin);
		route.setDestination(destination);
		try {
			adminService.deleteRoute(this, route);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	/**
	 * @param scanner
	 */
	private void adminAddRoute(Scanner scanner) {
		AdminService adminService = new AdminService();
		StringBuilder input = new StringBuilder();

		scanner.nextLine();

		System.out.print("Enter Origin IATA Code: ");
		input.append(scanner.nextLine());
		Route route = new Route();
		Airport origin = new Airport();
		origin.setAirportCode(input.toString());
		input.setLength(0);

		System.out.print("Enter Destination IATA Code: ");
		input.append(scanner.nextLine());
		Airport destination = new Airport();
		destination.setAirportCode(input.toString());

		route.setOrigin(origin);
		route.setDestination(destination);
		try {
			adminService.addRoute(this, route);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

	}

	/**
	 * @param scanner
	 */
	private void adminEditBookings(Scanner scanner) {
		AdminService adminService = new AdminService();
		Integer counter = 1;
		List<Booking> bookings = null;
		try {
			bookings = adminService.getBookings(this);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		System.out.println("Active Bookings:");
		for (Booking booking : bookings) {
			System.out.println(counter + ") " + booking.toString());
			counter++;
		}
		System.out.println(counter + ") Return to Menu");
		System.out.print("Selection: ");
		Integer option = scanner.nextInt();
		if (!option.equals(counter)) {
			adminBookingOptions(bookings.get(option - 1), scanner);
		}
	}

	/**
	 * @param booking
	 * @param scanner
	 */
	private void adminBookingOptions(Booking booking, Scanner scanner) {
		System.out.println("1) Edit Booking Details");
		System.out.println("2) Cancel Booking");
		System.out.println("3) Return to Menu");
		System.out.print("Selection: ");
		Integer option = scanner.nextInt();
		if (option.equals(1)) {
			adminUpdateBookingDetails(booking, scanner);
		} else if (option.equals(2)) {
			adminCancelBooking(booking);
		}
	}

	/**
	 * @param booking
	 */
	private void adminCancelBooking(Booking booking) {
		AdminService adminService = new AdminService();
		try {
			adminService.cancelBooking(this, booking);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	/**
	 * @param booking
	 * @param scanner
	 */
	private void adminUpdateBookingDetails(Booking booking, Scanner scanner) {
		AdminService adminService = new AdminService();
		System.out.println("Details for " + booking.toString());
		System.out.println("1) Edit Passenger");
		System.out.println("2) Edit Payment Information");
		System.out.println("3) Issue Refund");
		System.out.println("4) Change Flight");
		System.out.println("5) Return to Menu");
		Integer option = scanner.nextInt();
		StringBuilder detail = new StringBuilder();

		if (option.equals(1)) {
			System.out.print("Enter Passenger ID: ");
			detail.setLength(0);
			detail.append(scanner.nextInt());
			try {
				booking.setPassenger(adminService.findUser(this, Integer.parseInt(detail.toString())));
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
			try {
				adminService.updateBooking(this, booking);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		} else if (option.equals(2)) {
			System.out.print("Enter New Stripe Code: ");
			detail.setLength(0);
			detail.append(scanner.nextLine());
			booking.setStripeId(detail.toString());
		} else if (option.equals(3)) {
			booking.setRefunded(1);
			try {
				adminService.updateBooking(this, booking);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		} else if (option.equals(4)) {
			List<Flight> flights = adminService.getFlights(this);
			System.out.println("Select New Flight: ");
			Integer counter = 1;
			for (Flight f : flights) {
				System.out.println(counter + ") " + f.toString());
			}
			System.out.println(counter + ") Return to Menu");
			System.out.print("Selection ");
			detail.setLength(0);
			detail.append(scanner.nextInt());
			List<Flight> newFlights = new ArrayList<>();
			newFlights.add(flights.get(Integer.parseInt(detail.toString()) - 1));
			booking.setFlights(newFlights);
			try {
				adminService.updateBooking(this, booking);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		displayAdminMenu(scanner);
	}

	/**
	 * @param scanner
	 */
	private void adminEditFlights(Scanner scanner) {
		AdminService adminService = new AdminService();
		Integer counter = 1;
		List<Flight> flights = adminService.getFlights(this);
		for (Flight flight : flights) {
			System.out.println(counter + ") " + flight);
			counter++;
		}
		System.out.println(counter + ") Add Flight");
		System.out.print("Selection: ");
		Integer option = scanner.nextInt();
		if (!option.equals(counter) && !option.equals(counter + 1)) {
			adminFlightOptions(flights.get(option - 1), scanner);
		} else if (option.equals(counter)) {
			Flight flight = new Flight();
			StringBuilder detail = new StringBuilder();
			System.out.print("Enter Route ID: ");
			detail.append(scanner.nextInt());
			List<Route> routes = null;
			try {
				routes = adminService.getRoutes(this);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
			for (Route r : routes) {
				if (r.getId().equals(Integer.parseInt(detail.toString()))) {
					flight.setRoute(r);
				}
			}

			System.out.print("Enter Airplane ID: ");
			detail.setLength(0);
			detail.append(scanner.nextInt());
			flight.setAirplaneId(Integer.parseInt(detail.toString()));

			System.out.print("Enter Maximum Capacity: ");
			detail.setLength(0);
			detail.append(scanner.nextInt());
			scanner.nextLine();
			flight.setMaxCapacity(Integer.parseInt(detail.toString()));

			System.out.print("Enter Departure Date and Time (YYYY-MM-DD HH:MM:SS): ");
			detail.setLength(0);
			detail.append(scanner.nextLine());
			flight.setDepartureDateTime(detail.toString());
			System.out.print("Enter Number of Reserved Seats: ");
			detail.setLength(0);
			detail.append(scanner.nextInt());
			flight.setReservedSeats(Integer.parseInt(detail.toString()));

			System.out.print("Enter Price per Seat: ");
			detail.setLength(0);
			detail.append(scanner.nextDouble());
			flight.setPricePerSeat(Double.parseDouble(detail.toString()));

			try {
				adminService.addFlight(this, flight);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		displayAdminMenu(scanner);
	}

	/**
	 * @param flight
	 * @param scanner
	 */
	private void adminFlightOptions(Flight flight, Scanner scanner) {
		System.out.println("1) Edit Flight Details");
		System.out.println("2) Remove Flight");
		System.out.println("3) Return to Menu");
		System.out.print("Selection: ");
		Integer option = scanner.nextInt();
		if (option.equals(1)) {
			adminUpdateFlightDetails(flight, scanner);
		} else if (option.equals(2)) {
			adminDeleteFlight(flight);
		}
	}

	private void adminDeleteFlight(Flight flight) {
		AdminService adminService = new AdminService();
		try {
			adminService.deleteFlight(this, flight);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	/**
	 * @param flight
	 * @param scanner
	 */
	private void adminUpdateFlightDetails(Flight flight, Scanner scanner) {
		scanner.nextLine();
		StringBuilder option = new StringBuilder();
		System.out.print("Please enter new Route: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		if (option.length() > 0) {
			Route route = new Route();
			route.setId(Integer.parseInt(option.toString()));
			flight.setRoute(route);
		}

		System.out.print("Please enter new Airplane ID: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		if (option.length() > 0) {
			flight.setAirplaneId(Integer.parseInt(option.toString()));
		}

		System.out.print("Please enter new Departure Date and Time: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		if (option.length() > 0) {
			flight.setDepartureDateTime(option.toString());
		}
		try {
			AdminService adminService = new AdminService();
			adminService.updateFlight(this, flight);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
