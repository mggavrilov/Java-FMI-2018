package bg.uni.sofia.fmi.p2p.client;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import bg.uni.sofia.fmi.p2p.commands.Commands;

public class FetchUsersThread extends Thread {
	private Socket socket;
	private static final int MILLISECONDS_IN_SECOND = 1000;
	private static final int SLEEP_SECONDS = 30;

	public FetchUsersThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try (PrintWriter out = new PrintWriter(socket.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			while (true) {
				out.println(Commands.FETCH_USERS.getCommand());
				out.flush();

				String line;

				// The file that holds the user -> ip:port mappings is named after the local
				// port the client gets when connecting to the server
				FileOutputStream fout = new FileOutputStream(socket.getLocalPort() + ".txt");
				FileChannel fch = fout.getChannel();
				// https://stackoverflow.com/a/11837714
				FileLock lock = fch.lock(); // exclusive lock for writing

				while ((line = in.readLine()) != null && line.length() > 0) {
					fout.write((line + System.lineSeparator()).getBytes());
				}

				lock.release();
				fch.close();
				fout.close();

				Thread.sleep(SLEEP_SECONDS * MILLISECONDS_IN_SECOND);
			}

		} catch (IOException e) {
			System.err.println("An error occurred in the fetch users thread.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Shutting down fetch users thread.");
		}
	}
}
