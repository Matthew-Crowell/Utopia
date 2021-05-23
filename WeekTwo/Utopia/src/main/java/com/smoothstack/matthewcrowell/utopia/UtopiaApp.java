package com.smoothstack.matthewcrowell.utopia;

import com.smoothstack.matthewcrowell.utopia.dao.FlightDAO;
import com.smoothstack.matthewcrowell.utopia.entity.Flight;
import com.smoothstack.matthewcrowell.utopia.entity.User;
import com.smoothstack.matthewcrowell.utopia.service.ConnectionUtil;
import com.smoothstack.matthewcrowell.utopia.service.TravelerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UtopiaApp {
	private final ConnectionUtil connUtil = new ConnectionUtil();
	private User user;

	public ConnectionUtil getConnUtil() {
		return connUtil;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		UtopiaApp app = new UtopiaApp();
		Scanner scanner = new Scanner(System.in);

//		StringBuilder input = new StringBuilder();
//		System.out.println("New flight:");
//		FlightDAO fdao = new FlightDAO(app.connUtil.getConnection());
//
//		System.out.print("Flight ID: ");
//		input.append(scanner.nextLine());
//		Flight targetFlight = null;
//		List<Flight> flights = new ArrayList<>();
//		flights = fdao.getFlights();
//		for (Flight flight : flights) {
//			if (input.toString().equals(flight.getId().toString())) {
//				targetFlight = flight;
//			}
//		}
//		fdao.removeFlight(targetFlight);
		app.login(scanner);
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
		while (!option.equals(3)) {
			System.out.println("Welcome, Traveler!");
			System.out.println("1) Book a Ticket");
			System.out.println("2) Cancel an Upcoming Trip");
			System.out.println("3) Logout");
			System.out.print("Selection: ");
			option = scanner.nextInt();

			switch (option) {
				case (1):
					try {
						travelerBookTicket(this, scanner);
					} catch (SQLException | ClassNotFoundException throwables) {
						throwables.printStackTrace();
					}
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
		Integer option = 0;
		while (!option.equals(3)) {
			System.out.println("Your upcoming trips:");
			System.out.println("1) <flights listed here>");
			System.out.println("3) Quit to Previous");
			System.out.print("Selection: ");
			option = scanner.nextInt();

			if (option.equals(1)) {
				// TODO: Cancel trip
			}
		}

	}

	private void travelerBookTicket(UtopiaApp app, Scanner scanner) throws SQLException, ClassNotFoundException {
		TravelerService travelerService = new TravelerService();
		travelerService.addBooking(app);

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
		while (!option.equals(3)) {
			System.out.println("Flights:");
			System.out.println("1) <list of flights displayed here>");
			System.out.println("3) Quit to Previous");
			option = scanner.nextInt();
			if (option.equals(1)) {
				employeeFlightOptions(scanner);
			}
		}

	}

	private void employeeFlightOptions(Scanner scanner) {
		System.out.println("Options:");
		System.out.println("1) View Flight Details");
		System.out.println("2) Update Flight Details");
		System.out.println("3) Add Seats to Flight");
		System.out.println("4) Quit to Previous");
		System.out.print("Selection: ");
		Integer option = scanner.nextInt();
		if (option.equals(1)) {
			// TODO: add flight to method parameters
			employeeViewFlightDetails(scanner);
		} else if (option.equals(2)) {
			// TODO: add flight to method parameters
			updateFlightDetails(scanner);
		} else if (option.equals(3)) {
			// TODO: add flight to method parameters
			addSeatsToFlight(scanner);
		}
	}

	private void addSeatsToFlight(Scanner scanner) {
		Integer option = 0;
		while (!option.equals(4)) {
			System.out.println("Add Seat To:");
			System.out.println("1) First Class");
			System.out.println("2) Business Class");
			System.out.println("3) Economy class");
			System.out.println("4) Quit to Cancel Operation");
			System.out.print("Selection: ");
			option = scanner.nextInt();
			if (option.equals(1)) {
				// add seat to first class
			} else if (option.equals(2)) {
				// add seat to business class
			} else if (option.equals(3)) {
				// add seat to economy class
			}
		}
	}

	private void updateFlightDetails(Scanner scanner) {
		scanner.nextLine();
		StringBuilder option = new StringBuilder();
		System.out.print("Please enter new Origin Airport: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		// business logic
		System.out.print("Please enter new Departure Date: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		// business logic
		System.out.print("Please enter new Departure Time: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		// business logic
		System.out.print("Please enter new Arrival Date: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		// business logic
		System.out.print("please enter new Arrival Time: ");
		option.setLength(0);
		option.append(scanner.nextLine());
		// business logic
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
