package bg.uni.sofia.fmi.mjt.cinema.reservation.system.core;

public class Ticket {
	private Projection projection;
	private Seat seat;
	private String owner;

	public Ticket(Projection projection, Seat seat, String owner) {
		this.setProjection(projection);
		this.setSeat(seat);
		this.setOwner(owner);
	}

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
