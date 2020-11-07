package bg.uni.sofia.fmi.mjt.lambda;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides basic statistics on the peaks dataset.
 */
public class PeakStats {

	// Contains in-memory the data from the dataset
	private final List<Peak> peaks;

	/**
	 * Reads the dataset and initializes the peaks member variable. We keep the
	 * peaks in a List in order to get a Stream object multiple times.
	 * 
	 * Hint: use the Stream API to read and process the content of the file.
	 * 
	 * @param datasetPath
	 *            path to the dataset file
	 * @throws FileNotFoundException
	 */
	public PeakStats(String datasetPath) throws FileNotFoundException {
		try (Stream<String> lines = new BufferedReader(new FileReader(datasetPath)).lines()) {
			peaks = lines.map(line -> Peak.createPeak(line)).collect(Collectors.toList());
		}
	}

	public List<Peak> getPeaks() {
		return peaks;
	}

	/**
	 * @return - the height of the shortest peak that has never been ascended
	 */
	public double shortestNotAscended() {
		return peaks.stream().filter(p -> p.getFirstAscent() == 0)
				.min((p1, p2) -> Double.compare(p1.getHeight(), p2.getHeight())).get().getHeight();
	}

	/**
	 * How many times in average was a peak in Top N ascended. For example,
	 * avgAscentsTopN(10) should return the average number of times a peak from the
	 * highest 10 has been ascended.
	 * 
	 * @return - the first n peaks to include in the statistics
	 */
	public double avgAscentsTopN(int n) {
		throw new UnsupportedOperationException("Please provide implementation");
	}

	/**
	 * If there are peaks with equal prominence, we consider the supplied one as
	 * higher
	 * 
	 * @param prominence-
	 *            the prominence in meters
	 * @return the position where a peak with the given prominence would rank.
	 */
	public long getPositionByProminence(double prominence) {
		throw new UnsupportedOperationException("Please provide implementation");
	}

	/**
	 * @param year
	 *            - year of ascent
	 * @return - the highest peak ascended in the given year.
	 * @throws -
	 *             {@link IllegalArgumentException}, if there are no peaks ascended
	 *             in the given year.
	 */
	public Peak getHighestAscentPerYear(int year) {
		throw new UnsupportedOperationException("Please provide implementation");
	}

	/**
	 * @return - the list of peaks that are not part of the Himalaya.
	 */
	public List<String> getNonHimalayaNamesByProminence() {
		throw new UnsupportedOperationException("Please provide implementation");
	}

	/**
	 * Returns the name of the mountain range that has the most peaks in Top N
	 */
	public String getRangeWithMostPeaks(int n) {
		throw new UnsupportedOperationException("Please provide implementation");
	}

	/**
	 * Returns the names of the peaks in the given from-to interval according to
	 * their height ranking with "\n" as separator
	 */
	public String listPeaks(int from, int to) {
		throw new UnsupportedOperationException("Please provide implementation");
	}

}
