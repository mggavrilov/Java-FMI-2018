package bg.uni.sofia.fmi.mjt.cinema.reservation.system.core;

public class Seat {
	private int row;
	private int seat;

	public Seat(int row, int seat) {
		this.row = row;
		this.seat = seat;
	}

	public int getRow() {
		return row;
	}

	public int getSeat() {
		return seat;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + row;
		result = prime * result + seat;
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
		Seat other = (Seat) obj;
		if (row != other.row) {
			return false;
		}
		if (seat != other.seat) {
			return false;
		}
		return true;
	}
}
