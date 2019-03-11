package bg.sofia.fmi.mjt.torrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

	private static final Logger LOGGER = Logger.getLogger( Client.class.getName() );
	private BufferedReader reader;
	private PrintWriter writer;
	ConcurrentHashMap<String, ClientInfo> users;
	ConcurrentHashMap<String, HashSet<String>> userFiles;
	ServerSocket miniServer;
	int miniServerPort;
	final int numberOfSeconds = 30*1000;
	final int delay = 0;
	

	public static void main(String[] args) throws IOException {
		new Client().run();
	}

	public void run() throws IOException {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				String input = scanner.nextLine();
				String[] tokens = input.split(" ");
				String command = tokens[0];
				String username = "";
				switch (command) {
					case "connect":
						String host = tokens[1];
						int port = Integer.parseInt(tokens[2]);
						username = tokens[3];
						connect(host, port, username);
						break;
					case "download":
						username = tokens[1];
						String address = this.users.get(username).host;
						if (address != null) {
							String[] addressParts = address.split(":");
							download(address, this.users.get(username).miniServerPort, tokens[2], tokens[3]);
						} else {
							System.out.println("Error: No such user found!");
						}
						break;
					case "help":
						System.out.println("=== Supported commands are: ===");
						System.out.println("connect <host> <port> <username> - init client and connection with server");
						System.out.println("register <username> <path to file 1> ...  <path to file n> - inform peers for files by registering them to server");
						System.out.println("unregister <username> <path to file 1> ...  <path to file n> - inform peers for files by unregistering them to server");
						System.out.println("list-files - list all files registered on server");
						System.out.println("download <username> <path of file> <path to save> - download registered file from peer");
					default:
						writer.println(input);
						break;
				}
			}
		}
	}
	
	private void connect(String host, int port, String username) {
		try {
			// Start mini-server
			PeerServer miniServer = new PeerServer(this);
			new Thread(miniServer).start();
			
			Socket socket = new Socket(host, port);
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			System.out.println("successfully opened a socket");
			writer.println("connect " + username + " " + this.miniServerPort);
			ClientRunnable clientRunnable = new ClientRunnable(socket, this);
			new Thread(clientRunnable).start();
			
			// 30-second update request
			Timer timer = new Timer();
			System.out.println("successfully opened a background socket");
			ClientUpdateRunnable updateProcess = new ClientUpdateRunnable(host, port, Client.this);
			timer.schedule(updateProcess, delay, numberOfSeconds);
			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			System.out.println("Error: Cannot connect to server on "+ host +":" + port +" , "
					+ "make sure that the server is started");
		}
	}
	private void download(String host, int port, String fileLocation, String saveLocation) {
		try {
			Socket socket = new Socket(host, port);
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			System.out.println("successfully opened a socket for peer");
			writer.println("download " + fileLocation);
			
			PeerDownloadRequest downloadRequest = new PeerDownloadRequest(socket, saveLocation);
			new Thread(downloadRequest).start();
			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			System.out.println("Error: Cannot connect to peer on "+ host +":" + port +" , "
					+ "make sure that the peer is active");
		}
	}
}