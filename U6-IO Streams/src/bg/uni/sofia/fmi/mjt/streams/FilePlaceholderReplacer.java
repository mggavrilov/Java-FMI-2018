package bg.uni.sofia.fmi.mjt.streams;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class FilePlaceholderReplacer {

	private static final int BUFFER_READ_SIZE = 2048;

	public static void replace(String fromFileName, String toFileName, Map<String, String> placeholders)
		throws IOException {
		String output = "";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try (InputStream reader = new FileInputStream(fromFileName)) {
			byte[] buff = new byte[BUFFER_READ_SIZE];
			int r = 0;
			while ((r = reader.read(buff)) != -1) {
				bos.write(buff, 0, r);
			}

			String input = new String(bos.toByteArray());

			StringReplacer replacer = new StringReplacer(input);
			output = replacer.replace(placeholders);
		}

		try (FileOutputStream writer = new FileOutputStream(toFileName)) {
			writer.write(output.getBytes());
			writer.flush();
		}
	}
}