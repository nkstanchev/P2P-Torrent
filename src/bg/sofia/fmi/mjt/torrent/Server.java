package bg.sofia.fmi.mjt.torrent;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

class ClientInfo implements Serializable {
	String host;
	int clientPort;
	int miniServerPort;
	public ClientInfo (String host, int clientPort, int miniServerPort)
	{
		this.host = host;
		this.clientPort = clientPort;
		this.miniServerPort = miniServerPort;
	}
	@Override
	public String toString() { 
	    return this.host + ":" + this.clientPort + " miniserver port: " + this.miniServerPort;
	}
}

public class Server {
	
	
	private static final Logger LOGGER = Logger.getLogger( Server.class.getName() );
	private final int PORT = 8080;
	private static Server server;
	private ConcurrentHashMap<String, ClientInfo> users;
	private ConcurrentHashMap<String, HashSet<String>> userFiles;
	
	private Server() {
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			this.users = new ConcurrentHashMap<>();
			this.userFiles = new ConcurrentHashMap<>();
			while (true) {
				Socket socket = serverSocket.accept();
				
				ServerListener listener = new ServerListener(socket, this);
				new Thread(listener).start();
			}
			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			System.out.println("Error: Starting server failed!");
		}
	}
	public static Server getInstance() {
		if (server == null)
		{
			server = new Server(); 
			return server;
		}
		return server;
	}
	public ConcurrentHashMap<String, ClientInfo> getUsers() {
		return this.users;
	}
	public ConcurrentHashMap<String, HashSet<String>> getUserFiles() {
		return this.userFiles;
	}
	public static void main (String ...args) {
		Server server = Server.getInstance();
	}
}
	