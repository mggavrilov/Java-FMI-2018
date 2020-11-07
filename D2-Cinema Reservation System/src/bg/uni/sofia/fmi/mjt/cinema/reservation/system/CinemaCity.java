package bg.uni.sofia.fmi.mjt.cinema.reservation.system;

import bg.uni.sofia.fmi.mjt.cinema.reservation.system.core.Hall;
import bg.uni.sofia.fmi.mjt.cinema.reservation.system.core.Movie;
import bg.uni.sofia.fmi.mjt.cinema.reservation.system.core.Projection;
import bg.uni.sofia.fmi.mjt.cinema.reservation.system.core.Reservation;
import bg.uni.sofia.fmi.mjt.cinema.reservation.system.core.Seat;
import bg.uni.sofia.fmi.mjt.cinema.reservation.system.core.Ticket;

import bg.uni.sofia.fmi.mjt.cinema.reservation.system.exceptions.AlreadyReservedException;
import bg.uni.sofia.fmi.mjt.cinema.reservation.system.exceptions.ExpiredProjectionException;
import bg.uni.sofia.fmi.mjt.cinema.reservation.system.exceptions.InvalidSeatException;
import bg.uni.sofia.fmi.mjt.cinema.reservation.system.exceptions.ProjectionNotFoundException;
import bg.uni.sofia.fmi.mjt.cinema.reservation.system.exceptions.ReservationNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CinemaCity implements CinemaReservationSystem {

	private Map<Movie, List<Projection>> cinemaProgram;

	private Map<Reservation, String> reservations;

	public CinemaCity(Map<Movie, List<Projection>> cinemaProgram) {
		this.cinemaProgram = cinemaProgram;
		reservations = new HashMap<Reservation, String>();
	}

	@Override
	public void bookTicket(Ticket ticket) throws AlreadyReservedException, ProjectionNotFoundException,
			InvalidSeatException, ExpiredProjectionException {

		Projection projection = ticket.getProjection();
		
		validateProjection(projection);

		if (reservations.containsKey(new Reservation(ticket.getProjection(), ticket.getSeat()))) {
			throw new AlreadyReservedException();
		}
		
		if (projection.getDate().isBefore(LocalDateTime.now())) {
			throw new ExpiredProjectionException();
		}
		
		if (!validateSeat(projection.getHall(), ticket.getSeat())) {
			throw new InvalidSeatException();
		}

		reservations.put(new Reservation(ticket.getProjection(), ticket.getSeat()), ticket.getOwner());
	}

	@Override
	public void cancelTicket(Ticket ticket) throws ReservationNotFoundException, ProjectionNotFoundException {
		validateProjection(ticket.getProjection());

		String owner = reservations.get(new Reservation(ticket.getProjection(), ticket.getSeat()));

		if (owner == null || !owner.equals(ticket.getOwner())) {
			throw new ReservationNotFoundException();
		}

		reservations.remove(new Reservation(ticket.getProjection(), ticket.getSeat()));
	}

	@Override
	public int getFreeSeats(Projection projection) throws ProjectionNotFoundException {
		validateProjection(projection);

		int totalSeats = projection.getHall().getRows() * projection.getHall().getRowSeats();

		for (Reservation res : reservations.keySet()) {
			if (projection.equals(res.getProjection())) {
				--totalSeats;
			}
		}

		return totalSeats;
	}

	@Override
	public Collection<Movie> getSortedMoviesByGenre() {
		List<Movie> movies = new ArrayList<Movie>(cinemaProgram.keySet());
		movies.sort(new Comparator<Movie>() {
			@Override
			public int compare(Movie m1, Movie m2) {
				if (m1.getGenre() != m2.getGenre()) {
					return m1.getGenre().toString().compareTo(m2.getGenre().toString());
				} else {
					return m1.getName().compareTo(m2.getName());
				}
			}
		});

		return movies;
	}

	private void validateProjection(Projection projection) throws ProjectionNotFoundException {
		List<Projection> projections = cinemaProgram.get(projection.getMovie());

		if (projections == null) {
			throw new ProjectionNotFoundException();
		}

		if (projections.indexOf(projection) < 0) {
			throw new ProjectionNotFoundException();
		}
	}

	private boolean validateSeat(Hall hall, Seat seat) {
		if (seat.getRow() > hall.getRows() || seat.getSeat() > hall.getRowSeats()) {
			return false;
		} else {
			return true;
		}
	}
}
