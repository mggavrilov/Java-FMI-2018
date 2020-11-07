package bg.uni.sofia.fmi.grep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor implements Runnable {

	// private String filename;
	private String searchString;
	private int start;
	private int end;

	public FileProcessor(String searchString, int start, int end) {
		this.searchString = searchString;
		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {
		for (int i = start; i < end; i++) {
			String filename = Main.filesList.get(i);

			try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
				String line;
				int lineCounter = 0;

				while ((line = in.readLine()) != null) {
					lineCounter++;

					if (line.contains(searchString)) {
						System.out.println(filename + ":" + lineCounter + ":" + line);
					}
				}
			} catch (FileNotFoundException e) {
				System.err.println("File not found: " + filename);
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Couldn't read file: " + filename);
				e.printStackTrace();
			}
		}
	}

}
