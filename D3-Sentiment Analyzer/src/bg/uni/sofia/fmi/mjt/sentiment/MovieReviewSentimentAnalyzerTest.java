package bg.uni.sofia.fmi.mjt.sentiment;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MovieReviewSentimentAnalyzerTest {

	private static MovieReviewSentimentAnalyzer analyzer;
	private static final String stopWordsURL = "https://raw.githubusercontent.com/fmi/java-course/master/homeworks/03-movie-review-sentiment-analyzer/resources/stopwords.txt";
	private static final String movieReviewsURL = "https://raw.githubusercontent.com/fmi/java-course/master/homeworks/03-movie-review-sentiment-analyzer/resources/movieReviews.txt";

	private static final double PRECISION = 0.01;

	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
		System.setErr(null);
	}

	private static void readFileFromURL(String url, File file) throws IOException {
		URL remoteLocation = new URL(url);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(remoteLocation.openStream()))) {
			int length;
			char[] buf = new char[1024];

			try (FileWriter out = new FileWriter(file)) {
				while ((length = in.read(buf)) > 0) {
					out.write(buf, 0, length);
				}
			}
		}
	}

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		File stopWordsFile = File.createTempFile("stopwords", ".txt");
		File movieReviewsFile = File.createTempFile("moviereviews", ".txt");

		stopWordsFile.deleteOnExit();
		movieReviewsFile.deleteOnExit();

		readFileFromURL(stopWordsURL, stopWordsFile);
		readFileFromURL(movieReviewsURL, movieReviewsFile);

		analyzer = new MovieReviewSentimentAnalyzer(movieReviewsFile.getAbsolutePath(),
				stopWordsFile.getAbsolutePath());
	}

	@Test
	public void testFakeFiles() {
		MovieReviewSentimentAnalyzer testAnalyzer = new MovieReviewSentimentAnalyzer("fake1.txt", "fake2.txt");
		assertEquals("Error reading stopwords file fake2.txt" + System.lineSeparator()
				+ "Error reading movie reviews file fake1.txt" + System.lineSeparator(), errContent.toString());
	}

	@Test
	public void testDictionarySize() {
		assertEquals(15079, analyzer.getSentimentDictionarySize());
	}

	@Test
	public void testRealStopWord() {
		assertTrue(analyzer.isStopWord("how"));
	}

	@Test
	public void testFakeStopWord() {
		assertFalse(analyzer.isStopWord("skateboard"));
	}

	@Test
	public void testReviewScore() {
		assertEquals(1.4637421952077123,
				analyzer.getReviewSentiment("A weak script that ends with a quick and boring finale."), PRECISION);
		assertEquals(2.180814662891809,
				analyzer.getReviewSentiment("The funniest comedy of the year, good work! Don't miss it!"), PRECISION);
	}

	@Test
	public void testWordScore() {
		assertEquals(4.0, analyzer.getWordSentiment("skateboards"), PRECISION);
		assertEquals(4.0, analyzer.getWordSentiment("staggering"), PRECISION);
		assertEquals(3.5, analyzer.getWordSentiment("achievements"), PRECISION);
		assertEquals(3.25, analyzer.getWordSentiment("popular"), PRECISION);
		assertEquals(3.0, analyzer.getWordSentiment("spells"), PRECISION);
		assertEquals(2.6, analyzer.getWordSentiment("international"), PRECISION);
		assertEquals(2.0, analyzer.getWordSentiment("snowball"), PRECISION);
		assertEquals(1.75, analyzer.getWordSentiment("dude"), PRECISION);
		assertEquals(1.0, analyzer.getWordSentiment("mediterranean"), PRECISION);
		assertEquals(0.0, analyzer.getWordSentiment("cash"), PRECISION);
		assertEquals(-1.0, analyzer.getWordSentiment("yqfwdgausdu"), PRECISION);
	}

	@Test
	public void testScoreAsWord() {
		assertEquals("positive", analyzer.getReviewSentimentAsName("skateboards"));
		assertEquals("positive", analyzer.getReviewSentimentAsName("staggering"));
		assertEquals("positive", analyzer.getReviewSentimentAsName("achievements"));
		assertEquals("somewhat positive", analyzer.getReviewSentimentAsName("popular"));
		assertEquals("somewhat positive", analyzer.getReviewSentimentAsName("spells"));
		assertEquals("somewhat positive", analyzer.getReviewSentimentAsName("international"));
		assertEquals("neutral", analyzer.getReviewSentimentAsName("snowball "));
		assertEquals("neutral", analyzer.getReviewSentimentAsName("dude"));
		assertEquals("somewhat negative", analyzer.getReviewSentimentAsName("mediterranean"));
		assertEquals("negative", analyzer.getReviewSentimentAsName("cash"));
		assertEquals("unknown", analyzer.getReviewSentimentAsName("yqfwdgausdu"));
	}

	@Test
	public void testMostFrequentWords() {
		List<String> expectedList = List.of("s", "film", "movie", "t", "n", "one", "like", "rrb", "lrb", "story");

		assertEquals(expectedList, new ArrayList<String>(analyzer.getMostFrequentWords(10)));
	}

	@Test
	public void testMostPositiveWords() {
		List<String> words = new ArrayList<String>(analyzer.getMostPositiveWords(10));

		for (String word : words) {
			assertEquals(4.0, analyzer.getWordSentiment(word), PRECISION);
		}
	}

	@Test
	public void testMostNegativeWords() {
		List<String> words = new ArrayList<String>(analyzer.getMostNegativeWords(10));

		for (String word : words) {
			assertEquals(0.0, analyzer.getWordSentiment(word), PRECISION);
		}
	}
}
