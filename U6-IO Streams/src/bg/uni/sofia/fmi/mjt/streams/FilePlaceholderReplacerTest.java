package bg.uni.sofia.fmi.mjt.streams;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Test;

public class FilePlaceholderReplacerTest {

	@Test(expected = FileNotFoundException.class)
	public void testNonExistentInput() throws IOException {
		Files.deleteIfExists(Paths.get("input.txt"));
		Files.deleteIfExists(Paths.get("output.txt"));

		FilePlaceholderReplacer.replace("input.txt", "output.txt", Map.of("placeholder", "real"));
	}

	@Test
	public void testExistingInput() throws IOException {
		try (FileOutputStream writer = new FileOutputStream("input.txt")) {
			writer.write("{real1} text1 {real2} text2 {real3} text3".getBytes());
			writer.flush();
		}

		FilePlaceholderReplacer.replace("input.txt", "output.txt",
				Map.of("real1", "new1", "real2", "new2", "real3", "new3"));

		String expectedOutput = "new1 text1 new2 text2 new3 text3";

		try (InputStream reader = new FileInputStream("output.txt")) {
			String actualOutput = new String(reader.readAllBytes());

			assertEquals(expectedOutput, actualOutput);
		}
	}
}
