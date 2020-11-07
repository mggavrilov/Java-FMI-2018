package bg.uni.sofia.fmi.mjt.cinema.reservation.system.core;

public class Movie {
	private String name;
	private int duration;
	private MovieGenre genre;

	public Movie(String name, int duration, MovieGenre genre) {
		this.name = name;
		this.duration = duration;
		this.genre = genre;
	}

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}

	public MovieGenre getGenre() {
		return genre;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setGenre(MovieGenre genre) {
		this.genre = genre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + duration;
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Movie other = (Movie) obj;
		if (duration != other.duration) {
			return false;
		}
		if (genre != other.genre) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
