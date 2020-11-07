package bg.uni.sofia.fmi.p2p.client;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bg.uni.sofia.fmi.p2p.commands.Commands;

public class TestClient {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpBeforeClass() throws Exception {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(System.out);
		System.setErr(System.err);
	}

	@Test
	public void testListFiles() throws IOException {
		Client client = new Client();

		String input = "test";
		BufferedReader mockInStream = new BufferedReader(new StringReader(input));
		client.setIn(mockInStream);

		StringWriter stringWriter = new StringWriter();
		PrintWriter mockOutStream = new PrintWriter(stringWriter);
		client.setOut(mockOutStream);

		ClientMain.listFiles(client);

		assertEquals("test" + System.lineSeparator(), outContent.toString());
		assertEquals(Commands.LIST_FILES.getCommand() + System.lineSeparator(), stringWriter.toString());
	}

	@Test
	public void testRegister() {
		Client client = new Client();
		ClientMiniServerThread clientMiniServerThread = new ClientMiniServerThread();
		int testPort = 12345;
		clientMiniServerThread.setPort(testPort);

		client.setClientMiniServerThread(clientMiniServerThread);

		StringWriter stringWriter = new StringWriter();
		PrintWriter mockOutStream = new PrintWriter(stringWriter);
		client.setOut(mockOutStream);

		String username = "user";
		File file = new File("file1.txt");

		String[] arguments = { "register", username, file.getAbsolutePath() };

		ClientMain.register(true, client, arguments);

		assertEquals("user", client.getLocalUsername());

		String expectedOutput = Commands.REGISTER.getCommand() + System.lineSeparator() + username
				+ System.lineSeparator() + testPort + System.lineSeparator() + file.getAbsolutePath()
				+ System.lineSeparator() + System.lineSeparator();

		assertEquals(expectedOutput, stringWriter.toString());
	}

	@Test
	public void testUnregister() {
		Client client = new Client();
		ClientMiniServerThread clientMiniServerThread = new ClientMiniServerThread();
		int testPort = 12345;
		clientMiniServerThread.setPort(testPort);

		client.setClientMiniServerThread(clientMiniServerThread);

		StringWriter stringWriter = new StringWriter();
		PrintWriter mockOutStream = new PrintWriter(stringWriter);
		client.setOut(mockOutStream);

		String username = "user";
		File file = new File("file1.txt");

		String[] arguments = { "unregister", username, file.getAbsolutePath() };

		ClientMain.register(false, client, arguments);

		assertEquals("user", client.getLocalUsername());

		String expectedOutput = Commands.UNREGISTER.getCommand() + System.lineSeparator() + username
				+ System.lineSeparator() + testPort + System.lineSeparator() + file.getAbsolutePath()
				+ System.lineSeparator() + System.lineSeparator();

		assertEquals(expectedOutput, stringWriter.toString());
	}

	@Test
	public void testWrongUsername() {
		Client client = new Client();
		client.setLocalUsername("fakeuser");

		StringWriter stringWriter = new StringWriter();
		PrintWriter mockOutStream = new PrintWriter(stringWriter);
		client.setOut(mockOutStream);

		String username = "user";
		File file = new File("file1.txt");

		String[] arguments = { "unregister", username, file.getAbsolutePath() };

		ClientMain.register(true, client, arguments);

		assertEquals("Wrong username." + System.lineSeparator(), errContent.toString());
		assertEquals("fakeuser", client.getLocalUsername());
	}

	@Test
	public void testQuit() throws IOException {
		Client client = new Client();
		client.setLocalUsername("user");
		client.setSocket(new Socket());

		StringWriter stringWriter = new StringWriter();
		PrintWriter mockOutStream = new PrintWriter(stringWriter);
		client.setOut(mockOutStream);

		ClientMain.quit(client);

		String expected = Commands.QUIT.getCommand() + System.lineSeparator() + client.getLocalUsername()
				+ System.lineSeparator();

		assertEquals(expected, stringWriter.toString());
		assertEquals("Shutting down client." + System.lineSeparator(), outContent.toString());
	}

	/*@Test
	public void testDownload() throws IOException {

		Client leecher = new Client();
		Client seeder = new Client();

		Client fakeServer = new Client();

		seeder.setClientMiniServerThread(new ClientMiniServerThread());
		seeder.getClientMiniServerThread().run();

		fakeServer.setClientMiniServerThread(new ClientMiniServerThread());
		fakeServer.getClientMiniServerThread().run();

		int leecherPort = 12345;

		String ip = "127.0.0.1";
		int seederPort = seeder.getClientMiniServerThread().getPort();

		int fakeServerPort = fakeServer.getClientMiniServerThread().getPort();

		leecher.setSocket(
				new Socket(InetAddress.getByName(ip), fakeServerPort, InetAddress.getByName(ip), leecherPort));

		try (PrintWriter out = new PrintWriter(leecherPort + ".txt");) {
			out.println("seeder - " + ip + ":" + seederPort);
			out.flush();
		}

		leecher.setLocalUsername("leecher");

		StringWriter stringWriter = new StringWriter();
		PrintWriter mockOutStream = new PrintWriter(stringWriter);
		leecher.setOut(mockOutStream);

		String downloadedFileName = "file1_dl.txt";

		String[] arguments = { "download", "seeder", new File("file1.txt").getAbsolutePath(), downloadedFileName };

		ClientMain.download(leecher, arguments);

		File downloadedFile = new File(downloadedFileName);

		seeder.getClientMiniServerThread().getSocket().close();
		fakeServer.getClientMiniServerThread().getSocket().close();

		seeder.getClientMiniServerThread().interrupt();
		fakeServer.getClientMiniServerThread().interrupt();

		assertTrue(downloadedFile.exists());
	}*/

}
