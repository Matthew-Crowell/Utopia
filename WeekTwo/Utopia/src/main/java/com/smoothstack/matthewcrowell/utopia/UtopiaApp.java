package com.smoothstack.matthewcrowell.utopia;

import com.smoothstack.matthewcrowell.utopia.entity.Flight;
import com.smoothstack.matthewcrowell.utopia.entity.Route;
import com.smoothstack.matthewcrowell.utopia.entity.User;
import com.smoothstack.matthewcrowell.utopia.service.ConnectionUtil;
import com.smoothstack.matthewcrowell.utopia.service.EmployeeService;
import com.smoothstack.matthewcrowell.utopia.service.TravelerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UtopiaApp {
	private final ConnectionUtil connUtil = new ConnectionUtil();
	private User user;

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

		if (username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
			user = new User();
			user.setUserId(rs.getInt("id"));
			user.setRole(rs.getInt("role_id"));
			user.setGivenName(rs.getString("given_name"));
			user.setFamilyName(rs.getString("family_name"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPhoneNumber(rs.getString("phone"));
		}

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
	}

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

	private void travelerCancelTicket(Scanner scanner) {
		TravelerService travelerService = new TravelerService();
		try {
			travelerService.cancelBooking(this);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

	}

	private void travelerBookTicket(UtopiaApp app, Scanner scanner) {
		try {
			TravelerService travelerService = new TravelerService();
			travelerService.addBooking(app);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}


	}

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
		option = scanner.nextInt();
		if (!option.equals(counter)) {
			employeeFlightOptions(flights.get(option - 1), scanner);
		}
	}

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

	private void updateFlightDetails(Flight flight, Scanner scanner) {
		scanner.nextLine();
		StringBuilder option = new StringBuilder();
		System.out.print("Please enter new Route: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		if(option.length() > 0){
			Route route = new Route();
			route.setId(Integer.parseInt(option.toString()));
			flight.setRoute(route);
		}

		System.out.print("Please enter new Airplane ID: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		if(option.length() > 0){
			flight.setAirplaneId(Integer.parseInt(option.toString()));
		}

		System.out.print("Please enter new Departure Date and Time: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		if(option.length() > 0){
			flight.setDepartureDateTime(option.toString());
		}
		try {
			Connection conn = connUtil.getConnection();
			EmployeeService employeeService = new EmployeeService();
			employeeService.updateFlight(this, flight);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void employeeViewFlightDetails(Scanner scanner) {
		Integer option = 0;
		while (!option.equals(4)) {
			// TODO: Finish this menu
			System.out.println("You have chosen to view...");
			System.out.println("Departure Airport: ");
			System.out.println("Arrival Airport: ");
			System.out.println("Departure Date: ");
			System.out.println("Departure Time: ");
			System.out.println("Arrival Date: ");
			System.out.println("Arrival Time: ");
			System.out.println("Available Seats by Class: ");
			System.out.println("1) First - ");
			System.out.println("2) Business - ");
			System.out.println("3) Economy - ");
			System.out.println("4) Exit to Previous");

			option = scanner.nextInt();
		}

	}

	private void displayAdminMenu(Scanner scanner) {

	}
}
