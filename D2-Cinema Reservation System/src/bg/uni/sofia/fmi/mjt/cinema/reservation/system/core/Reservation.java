package bg.uni.sofia.fmi.mjt.cinema.reservation.system.core;

public class Reservation {
	private Projection projection;
	private Seat seat;

	public Reservation(Projection projection, Seat seat) {
		this.setProjection(projection);
		this.setSeat(seat);
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projection == null) ? 0 : projection.hashCode());
		result = prime * result + ((seat == null) ? 0 : seat.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Reservation other = (Reservation) obj;
		if (projection == null) {
			if (other.projection != null) {
				return false;
			}
		} else if (!projection.equals(other.projection)) {
			return false;
		}
		if (seat == null) {
			if (other.seat != null) {
				return false;
			}
		} else if (!seat.equals(other.seat)) {
			return false;
		}
		return true;
	}
}
