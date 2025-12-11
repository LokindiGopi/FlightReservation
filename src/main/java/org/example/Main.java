package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello world!");

        FlightService service = new FlightService();
        Scanner scanner = new Scanner(System.in);

        service.addFlight(new Flight("AA101", "New York",
                LocalDateTime.of(2025, 12, 15, 10, 30), 50));
        service.addFlight(new Flight("BA202", "New York",
                LocalDateTime.of(2025, 12, 15, 15, 45), 20));
        service.addFlight(new Flight("CA303", "Chicago",
                LocalDateTime.of(2025, 12, 16, 9, 15), 30));

        while (true) {
            System.out.println("\n--- Flight Reservation System ---");
            System.out.println("1. Search Flights");
            System.out.println("2. Book Flight");
            System.out.println("3. View Reservations");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    System.out.print("Enter destination: ");
                    String dest = scanner.nextLine();

                    System.out.print("Enter date (YYYY-MM-DD): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());

                    List<Flight> result = service.searchFlightsEqual(dest, date.atStartOfDay());
                    System.out.println("Available Flights:");
                    result.forEach(System.out::println);
                    break;

                case 2:
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter flight number: ");
                    String flightNum = scanner.nextLine();
                    System.out.print("Enter destination: ");
                    String desti = scanner.nextLine();

                    Flight flight = service.searchFlights(desti, LocalDateTime.now())
                            .stream().filter(f -> f.getFlightNumber().equalsIgnoreCase(flightNum))
                            .findFirst().orElse(null);

                    if (flight == null) {
                        System.out.println("Flight not found!");
                        break;
                    }

                    System.out.print("Enter seats to book: ");
                    int seats = Integer.parseInt(scanner.nextLine());

                    try {
                        Reservation res = service.bookFlight(name, flight, seats);
                        System.out.println("Booking successful: " + res);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Enter customer name: ");
                    String cust = scanner.nextLine();
                    service.getReservations(cust).forEach(System.out::println);
                    break;

                case 4:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}