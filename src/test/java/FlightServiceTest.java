
import org.example.Flight;
import org.example.FlightService;
import org.example.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlightServiceTest {

    private FlightService service;
    private Flight sampleFlight;

    @BeforeEach
    void setUp() {
        service = new FlightService();
        sampleFlight = new Flight(
                "XY101",
                "New York",
                LocalDateTime.of(2025, 12, 15, 10, 30),
                50
        );

        service.addFlight(sampleFlight);
    }

    // Test searching flights
    @Test
    void testSearchFlights() {
        List<Flight> flights = service.searchFlightsEqual(
                "New York",
                LocalDateTime.of(2025, 12, 15, 10, 30)
        );

        assertEquals(1, flights.size());
    }

    // Test booking seats successfully
    @Test
    void testBookFlightSuccess() {
        Reservation reservation = service.bookFlight("Alice", sampleFlight, 3);

        assertNotNull(reservation);
        assertEquals(47, sampleFlight.getAvailableSeats());
        assertEquals(3, reservation.getSeatsBooked());
    }

    // Test booking more seats than available
    @Test
    void testBookFlightNotEnoughSeats() {
        assertThrows(IllegalArgumentException.class, () ->
                service.bookFlight("Bob", sampleFlight, 200)
        );
    }

    // Test retrieving reservations
    @Test
    void testGetReservations() {
        service.bookFlight("Alice", sampleFlight, 2);

        List<Reservation> reservations = service.getReservations("Alice");

        assertEquals(1, reservations.size());
    }
}

