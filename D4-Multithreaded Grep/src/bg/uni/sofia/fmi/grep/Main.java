package bg.uni.sofia.fmi.grep;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
	private static final int EXPECTED_ARGUMENTS = 3;

	static List<String> filesList;

	private static void traverseDir(File[] files) {
		for (File file : files) {
			filesList.add(file.toString());

			if (file.isDirectory()) {
				traverseDir(file.listFiles());
			}
		}
	}

	private static boolean validateArgs(String[] args) {
		if (args.length != EXPECTED_ARGUMENTS) {
			return false;
		}

		for (String s : args) {
			if (s.equals("")) {
				return false;
			}
		}

		try {
			int threads = Integer.parseInt(args[2]);

			if (threads < 1) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	/*
	 * Remove commented lines to enable calculation of execution time
	 */
	public static void main(String[] args) throws InterruptedException {
		if (validateArgs(args)) {
			// long lStartTime = System.currentTimeMillis();
			// List<Thread> threads = new ArrayList<>();

			filesList = new ArrayList<>();

			File path = new File(args[0]);
			String searchString = args[1];
			int threadAmount = Integer.parseInt(args[2]);

			traverseDir(path.listFiles());

			if (filesList.size() < threadAmount) {
				threadAmount = filesList.size();
			}

			int filesPerThread = (int) Math.ceil((double) filesList.size() / threadAmount);

			for (int i = 0; i < threadAmount; i++) {
				int start = i * filesPerThread;
				int end = start + filesPerThread;

				if (end > filesList.size()) {
					end = filesList.size();
				}

				FileProcessor fp = new FileProcessor(searchString, start, end);
				Thread thread = new Thread(fp);
				thread.start();
				// threads.add(thread);
			}

			/*
			 * for (Thread t : threads) { t.join(); }
			 */

			// long lEndTime = System.currentTimeMillis();

			// System.out.println("Elapsed time in milliseconds: " + ((lEndTime -
			// lStartTime)));
		} else {
			System.out.println("Invalid arguments! Usage: Main.java <dir_name> <search_string> <threads>");
		}
	}

}
