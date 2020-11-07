package bg.uni.sofia.fmi.mjt.lambda;

/**
 * Representation of a peak with loaded data from the dataset. The class is
 * immutable. If you wish, you could provide suitable equals() and hashCode()
 * implementations
 */
public class Peak {

	private final int pos;
	private final String name;
	private final double height;
	private final double prominence;
	private final String range;
	private final int firstAscent;
	private final int totalAscents;

	private Peak(int pos, String name, double height, double prominence, String range, int firstAscent,
			int totalAscents) {
		this.pos = pos;
		this.name = name;
		this.height = height;
		this.prominence = prominence;
		this.range = range;
		this.firstAscent = firstAscent;
		this.totalAscents = totalAscents;
	}

	public static Peak createPeak(String line) {
		String[] data = line.split(",");

		return new Peak(Integer.parseInt(data[0]), data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]),
				data[4], Integer.parseInt(data[5]), Integer.parseInt(data[6]));
	}

	public int getPos() {
		return pos;
	}

	public String getName() {
		return name;
	}

	public double getHeight() {
		return height;
	}

	public double getProminence() {
		return prominence;
	}

	public String getRange() {
		return range;
	}

	public int getFirstAscent() {
		return firstAscent;
	}

	public int getTotalAscents() {
		return totalAscents;
	}

}
