package bg.uni.sofia.fmi.mjt.cinema.reservation.system;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bg.uni.sofia.fmi.mjt.cinema.reservation.system.core.*;
import bg.uni.sofia.fmi.mjt.cinema.reservation.system.exceptions.*;

public class Main {

	public static void main(String[] args) {
		Hall testHall = new Hall(1, 5, 5);
		LocalDateTime testDate = LocalDateTime.of(2017, 12, 12, 12, 12);

		Movie m1 = new Movie("movie1", 100, MovieGenre.HORROR);
		Movie m2 = new Movie("amovie2", 50, MovieGenre.HORROR);
		Movie m3 = new Movie("movie3", 25, MovieGenre.COMEDY);

		List<Projection> mp1 = new ArrayList<>();
		mp1.add(new Projection(m1, testHall, testDate));
		mp1.add(new Projection(m2, testHall, testDate));
		mp1.add(new Projection(m3, testHall, testDate));

		Map<Movie, List<Projection>> map = new HashMap<>();
		map.put(m1, mp1);
		map.put(m2, mp1);
		map.put(m3, mp1);

		CinemaCity cinema = new CinemaCity(map);

		List<Movie> testSorted = new ArrayList<Movie>(cinema.getSortedMoviesByGenre());
		for (Movie m : testSorted) {
			System.out.println(m.getGenre().toString() + " | " + m.getName());
		}

		System.exit(0);

		try {
			System.out.println(cinema.getFreeSeats(mp1.get(0)));
			Ticket t1 = new Ticket(mp1.get(0), new Seat(1, 1), "pesho");
			Ticket t2 = new Ticket(mp1.get(0), new Seat(1, 2), "gosho");
			Ticket t3 = new Ticket(mp1.get(0), new Seat(1, 3), "misho");

			cinema.bookTicket(t1);
			cinema.bookTicket(t2);
			cinema.bookTicket(t3);

			System.out.println(cinema.getFreeSeats(mp1.get(0)));
		} catch (ProjectionNotFoundException e) {

		} catch (AlreadyReservedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidSeatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExpiredProjectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
