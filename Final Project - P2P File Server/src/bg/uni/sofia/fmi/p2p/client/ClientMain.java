package bg.uni.sofia.fmi.p2p.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.NoSuchElementException;
import java.util.Scanner;

import bg.uni.sofia.fmi.p2p.commands.Commands;
import bg.uni.sofia.fmi.p2p.server.Server;

public class ClientMain {

	private static final int BYTES = 4096;

	public static void listFiles(Client client) throws IOException {
		String line;

		client.getOut().println(Commands.LIST_FILES.getCommand());
		client.getOut().flush();

		while ((line = client.getIn().readLine()) != null && line.length() > 0) {
			System.out.println(line);
		}
	}

	public static void download(Client client, String[] arguments) throws IOException {
		String username = arguments[1];

		String remotePath = arguments[2];
		String savePath = arguments[3];

		FileInputStream fin = new FileInputStream(client.getSocket().getLocalPort() + ".txt");
		FileChannel fch = fin.getChannel();
		// https://stackoverflow.com/a/11837714
		FileLock lock = fch.lock(0L, Long.MAX_VALUE, true); // shared lock for reading

		BufferedReader fileReader = new BufferedReader(new InputStreamReader(fin));

		Socket dlSocket = null;

		String line;

		// Parse the user -> ip:port mappings
		while ((line = fileReader.readLine()) != null && line.length() > 0) {
			String[] parts = line.split(" - ");
			if (username.equals(parts[0])) {
				String[] socketParts = parts[1].split(":");
				String ip = socketParts[0];
				int port = Integer.parseInt(socketParts[1]);
				dlSocket = new Socket(ip, port);
				break;
			}
		}

		lock.release();
		fin.close();
		fileReader.close();
		fch.close();

		if (dlSocket != null) {
			PrintWriter dlOut = new PrintWriter(dlSocket.getOutputStream());
			DataInputStream dlIn = new DataInputStream(dlSocket.getInputStream());

			dlOut.println(remotePath);
			dlOut.flush();

			int counter = BYTES;
			byte[] bytes = new byte[BYTES];

			FileOutputStream fileWriter = new FileOutputStream(savePath);

			while ((counter = dlIn.read(bytes, 0, counter)) > 0) {
				fileWriter.write(bytes, 0, counter);
			}

			fileWriter.close();
			dlOut.close();
			dlIn.close();
			dlSocket.close();
		}

		// register new file ownership to the server
		// Note: technically, users should be able to download files without registering
		// files of their own first. However, they can only receive a username when they
		// register a file (as per the assignment). For the purposes of this project, we
		// assume that they'll register a file first, before downloading from another
		// user. Otherwise, the downloaded file won't be registered to the server.
		if (!client.getLocalUsername().equals("") && (new File(savePath)).exists()) {
			client.getOut().println(Commands.REGISTER.getCommand());
			client.getOut().println(client.getLocalUsername());
			client.getOut().println(client.getClientMiniServerThread().getPort());
			client.getOut().println((new File(savePath)).getAbsolutePath());
			client.getOut().println();
			client.getOut().flush();
		}
	}

	public static void register(boolean register, Client client, String[] arguments) {

		String username = arguments[1];

		if (client.getLocalUsername().equals("")) {
			// register user
			client.setLocalUsername(username);
		} else {
			if (!client.getLocalUsername().equals(username)) {
				System.err.println("Wrong username.");
				return;
			}
		}

		if (register) {
			client.getOut().println(Commands.REGISTER.getCommand());
		} else {
			client.getOut().println(Commands.UNREGISTER.getCommand());
		}

		client.getOut().println(username);
		client.getOut().println(client.getClientMiniServerThread().getPort());

		for (int i = 2; i < arguments.length; i++) {
			File file = new File(arguments[i]);

			// When registering: the file must exist on the user's system.
			// When unregistering: the file doesn't need to exist on the user's system.
			if ((file.exists() && !file.isDirectory()) || !register) {
				client.getOut().println(file.getAbsolutePath());
			}
		}

		client.getOut().println();
		client.getOut().flush();
	}

	public static void quit(Client client) throws IOException {
		System.out.println("Shutting down client.");
		client.getOut().println(Commands.QUIT.getCommand());
		client.getOut().println(client.getLocalUsername());
		client.getOut().flush();
		client.getSocket().close();
	}

	public static void main(String[] args) {
		Client client = new Client();

		try (Socket s = new Socket("localhost", Server.SERVER_PORT);
				PrintWriter out = new PrintWriter(s.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				Scanner sc = new Scanner(System.in);) {

			client.setSocket(s);
			client.setOut(out);
			client.setIn(in);

			client.setFetchUsersThread(new FetchUsersThread(s));
			client.setClientMiniServerThread(new ClientMiniServerThread());

			client.getFetchUsersThread().start();
			client.getClientMiniServerThread().start();

			while (true) {
				System.out.print("Command: ");

				String line;

				try {
					line = sc.nextLine();
				} catch (NoSuchElementException e) {
					// Ctrl-Z EOF
					line = "quit";
				}

				// https://stackoverflow.com/a/18893443
				// split arguments on space but don't split arguments enclosed in inverted
				// commas "".
				String[] arguments = line.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

				// remove "" from the arguments which were enclosed in them
				for (int i = 0; i < arguments.length; i++) {
					arguments[i] = arguments[i].replace("\"", "");
				}

				String cmd = arguments[0];

				if (cmd.equals("list-files")) {

					listFiles(client);

				} else {

					if (cmd.equals("download")) {

						download(client, arguments);

					} else if (cmd.equals("register")) {

						register(true, client, arguments);

					} else if (cmd.equals("unregister")) {

						register(false, client, arguments);

					} else if (cmd.equals("quit")) {
						quit(client);
						break;
					} else {
						System.err.println(
								"Invalid command. Available commands: register, unregister, list-files, download and quit.");
					}
				}

			}

		} catch (IOException e) {
			System.err.println("An error occured with the client.");
			e.printStackTrace();
		}

		client.getFetchUsersThread().interrupt();

		try {
			client.getClientMiniServerThread().getSocket().close();
		} catch (IOException e) {
			System.err.println("Error stopping the client mini server thread.");
			e.printStackTrace();
		}

	}
}
