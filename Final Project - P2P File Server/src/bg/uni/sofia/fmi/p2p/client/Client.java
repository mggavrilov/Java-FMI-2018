package bg.uni.sofia.fmi.p2p.client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private String localUsername;
	private Socket socket;

	private BufferedReader in;
	private PrintWriter out;

	private FetchUsersThread fetchUsersThread;
	private ClientMiniServerThread clientMiniServerThread;

	public Client() {
		localUsername = "";
		socket = null;

		in = null;
		out = null;

		fetchUsersThread = null;
		clientMiniServerThread = null;
	}

	public String getLocalUsername() {
		return localUsername;
	}

	public void setLocalUsername(String localUsername) {
		this.localUsername = localUsername;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public FetchUsersThread getFetchUsersThread() {
		return fetchUsersThread;
	}

	public void setFetchUsersThread(FetchUsersThread fetchUsersThread) {
		this.fetchUsersThread = fetchUsersThread;
	}

	public ClientMiniServerThread getClientMiniServerThread() {
		return clientMiniServerThread;
	}

	public void setClientMiniServerThread(ClientMiniServerThread clientMiniServerThread) {
		this.clientMiniServerThread = clientMiniServerThread;
	}
}
