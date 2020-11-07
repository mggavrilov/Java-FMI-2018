package bg.uni.sofia.fmi.p2p.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Map;

import bg.uni.sofia.fmi.p2p.commands.Commands;

public class ServerThread extends Thread {

	private Socket socket;
	private String localUsername;

	public Socket getSocket() {
		return socket;
	}

	public String getLocalUsername() {
		return localUsername;
	}

	public ServerThread(Socket socket) {
		this.socket = socket;
		localUsername = "";
	}

	public void quit(BufferedReader in) throws IOException {
		String username = in.readLine();
		quit(username);
	}

	public void quit(String username) {
		// This method receives a username from the client, so we can immediately find
		// it in the Server.users and Server.userFiles hash maps and remove it.
		// Otherwise, we'd have to look through the hash map's values one by one (linear
		// time), or create another structure where the socket is a key to find the
		// username.
		try {

			System.out.println(
					"Closing connection with client " + username + ". Address: " + socket.getRemoteSocketAddress());

			Server.users.remove(username);
			Server.userFiles.remove(username);

			socket.close();
		} catch (IOException e) {
			System.err.println("Error while closing connection with client.");
			e.printStackTrace();
		}
	}

	public void fetchUsers(PrintWriter out) {
		for (Map.Entry<String, InetSocketAddress> entry : Server.users.entrySet()) {
			// format: /127.0.0.1:44444
			String socketAddress = entry.getValue().toString();

			// format: 127.0.0.1:44444
			socketAddress = socketAddress.substring(1);

			out.println(entry.getKey() + " - " + socketAddress);
		}

		out.println();
		out.flush();
	}

	public void register(BufferedReader in) throws IOException {
		String username = in.readLine();
		localUsername = username;
		
		int port = Integer.parseInt(in.readLine());

		// Prevents a user from registering files with another user's username, as a
		// username uniquely represents a socket connection. I know we're not supposed
		// to authenticate (as per the assignment) but this could lead to various bugs
		// if it happens.
		InetSocketAddress miniServerSocket = new InetSocketAddress(socket.getInetAddress(), port);

		synchronized (Server.users) {
			if (Server.users.get(username) == null || Server.users.get(username).equals(miniServerSocket)) {

				Server.users.put(username, miniServerSocket);

				HashSet<String> files = Server.userFiles.get(username);

				if (files == null) {
					files = new HashSet<>();
				}

				String file;

				while ((file = in.readLine()) != null && file.length() > 0) {
					files.add(file);
				}

				Server.userFiles.put(username, files);
			} else {
				String file;

				while ((file = in.readLine()) != null && file.length() > 0) {
					// consume remaining data in stream
				}
			}
		}
	}

	public void unregister(BufferedReader in) throws IOException {
		String username = in.readLine();

		HashSet<String> files = Server.userFiles.get(username);

		String file;

		if (files != null) {
			while ((file = in.readLine()) != null && file.length() > 0) {
				files.remove(file);
			}

			Server.userFiles.put(username, files);
		}
	}

	public void listFiles(PrintWriter out) {
		for (String user : Server.userFiles.keySet()) {
			for (String path : Server.userFiles.get(user)) {
				out.println(user + " : " + path);
			}
		}

		out.println();
		out.flush();
	}

	@Override
	public void run() {
		try (PrintWriter out = new PrintWriter(socket.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			while (!socket.isClosed()) {
				int command = Integer.parseInt(in.readLine());

				if (command == Commands.QUIT.getCommand()) {

					quit(in);

				} else if (command == Commands.FETCH_USERS.getCommand()) {

					fetchUsers(out);

				} else if (command == Commands.REGISTER.getCommand()) {

					register(in);

				} else if (command == Commands.UNREGISTER.getCommand()) {

					unregister(in);

				} else if (command == Commands.LIST_FILES.getCommand()) {

					listFiles(out);

				} else {
					System.err.println("Unrecognized command sent to server.");
				}
			}

		} catch (SocketException e) {
			quit(localUsername);
		} catch (IOException e) {
			System.err.println("A problem with the server socket thread occured.");
			e.printStackTrace();
		}
	}
}
