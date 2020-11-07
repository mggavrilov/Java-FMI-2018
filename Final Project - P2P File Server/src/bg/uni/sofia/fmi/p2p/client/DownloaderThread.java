package bg.uni.sofia.fmi.p2p.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DownloaderThread extends Thread {

	private static final int BYTES = 4096;

	private Socket socket;

	public DownloaderThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			String file = in.readLine();

			File filepath = new File(file);

			if (filepath.exists() && !filepath.isDirectory()) {
				FileInputStream fin = new FileInputStream(filepath.getAbsolutePath());

				int counter = BYTES;
				byte[] bytes = new byte[BYTES];

				while ((counter = fin.read(bytes, 0, counter)) > 0) {
					out.write(bytes, 0, counter);
				}

				out.flush();

				fin.close();
			}

		} catch (IOException e) {
			System.err.println("A problem with the downloader thread occured.");
			e.printStackTrace();
		}
	}

}
