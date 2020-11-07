package bg.uni.sofia.fmi.p2p.server;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestServer {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	private static final String TEST_IP = "127.0.0.1";
	private static final int TEST_PORT = 12345;

	private static final String TEST_FILE = "file1.txt";

	private void putUser(String username) {
		Server.users.put(username, new InetSocketAddress(TEST_IP, TEST_PORT));
		Server.userFiles.put(username, new HashSet<String>(Set.of(TEST_FILE)));
	}

	private void removeUsers() {
		Server.users.clear();
		Server.userFiles.clear();
	}

	@Before
	public void setUpBeforeClass() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(System.out);
		System.setErr(System.err);
	}

	@Test
	public void testQuit() {
		ServerThread server = new ServerThread(new Socket());
		String username = "user";

		putUser(username);

		assertTrue(Server.users.containsKey(username));
		assertTrue(Server.userFiles.containsKey(username));

		server.quit(username);

		String expectedMessage = "Closing connection with client " + username + ". Address: "
				+ server.getSocket().getRemoteSocketAddress() + System.lineSeparator();
		assertEquals(expectedMessage, outContent.toString());

		assertFalse(Server.users.containsKey("user"));
		assertFalse(Server.userFiles.containsKey("user"));
	}

	@Test
	public void testQuit2() throws IOException {
		ServerThread server = new ServerThread(new Socket());
		String username = "user";
		BufferedReader mockInStream = new BufferedReader(new StringReader(username));

		putUser(username);

		assertTrue(Server.users.containsKey(username));
		assertTrue(Server.userFiles.containsKey(username));

		server.quit(mockInStream);

		String expectedMessage = "Closing connection with client " + username + ". Address: "
				+ server.getSocket().getRemoteSocketAddress() + System.lineSeparator();
		assertEquals(expectedMessage, outContent.toString());

		assertFalse(Server.users.containsKey("user"));
		assertFalse(Server.userFiles.containsKey("user"));
	}

	@Test
	public void testFetchUsers() {
		ServerThread server = new ServerThread(new Socket());

		StringWriter stringWriter = new StringWriter();
		PrintWriter mockOutStream = new PrintWriter(stringWriter);

		String username = "user";

		putUser(username);

		server.fetchUsers(mockOutStream);

		String expected = username + " - " + TEST_IP + ":" + TEST_PORT + System.lineSeparator()
				+ System.lineSeparator();

		assertEquals(expected, stringWriter.toString());

		removeUsers();
	}

	@Test
	public void testRegister() throws IOException {
		ServerThread server = new ServerThread(new Socket());

		String username = "user";

		String input = username + System.lineSeparator() + TEST_PORT + System.lineSeparator() + TEST_FILE
				+ System.lineSeparator();
		BufferedReader mockInStream = new BufferedReader(new StringReader(input));

		server.register(mockInStream);

		assertTrue(Server.users.containsKey(username));
		assertTrue(Server.userFiles.containsKey(username));
		assertEquals(username, server.getLocalUsername());

		removeUsers();
	}

	@Test
	public void testUnregister() throws IOException {
		ServerThread server = new ServerThread(new Socket());

		String username = "user";

		putUser(username);

		assertTrue(Server.userFiles.get(username).contains(TEST_FILE));

		String input = username + System.lineSeparator() + TEST_FILE + System.lineSeparator();
		BufferedReader mockInStream = new BufferedReader(new StringReader(input));

		server.unregister(mockInStream);

		assertEquals(0, Server.userFiles.get(username).size());
		assertFalse(Server.userFiles.get(username).contains(TEST_FILE));

		removeUsers();
	}

	@Test
	public void testListFiles() {
		ServerThread server = new ServerThread(new Socket());

		StringWriter stringWriter = new StringWriter();
		PrintWriter mockOutStream = new PrintWriter(stringWriter);

		String username = "user";

		putUser(username);

		server.listFiles(mockOutStream);

		String expected = username + " : " + TEST_FILE + System.lineSeparator() + System.lineSeparator();

		assertEquals(expected, stringWriter.toString());

		removeUsers();
	}
}
