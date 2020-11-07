package bg.uni.sofia.fmi.p2p.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Server {
	public static final int SERVER_PORT = 4444;

	static Map<String, InetSocketAddress> users = new HashMap<>();
	static Map<String, HashSet<String>> userFiles = new HashMap<>();

	public static void main(String[] args) {
		try (ServerSocket ss = new ServerSocket(SERVER_PORT);) {

			while (true) {
				Socket s = ss.accept();
				new ServerThread(s).start();
			}

		} catch (IOException e) {
			System.err.println("A problem with the server socket occurred.");
			e.printStackTrace();
		}
	}
}
