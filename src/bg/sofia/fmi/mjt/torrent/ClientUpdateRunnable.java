package bg.sofia.fmi.mjt.torrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientUpdateRunnable extends TimerTask {

	private String host;
	private int port;
	private Client client;
	private static final Logger LOGGER = Logger.getLogger( ClientUpdateRunnable.class.getName() );
	
	public ClientUpdateRunnable(String host, int port, Client client) {
		this.host = host;
		this.port = port;
		this.client = client;
	}
	@Override
	public void run() {
		try {
			Socket updateSocket = new Socket(host, port);
			PrintWriter writer = new PrintWriter(updateSocket.getOutputStream(), true);
			writer.println("update");
			ObjectInputStream ois = new ObjectInputStream(updateSocket.getInputStream());
			client.users = (ConcurrentHashMap<String, ClientInfo>) ois.readObject();
        	client.userFiles = (ConcurrentHashMap<String, HashSet<String>>) ois.readObject();
        	ois.close();
//          Print received files and users
            System.out.println("Users into client");
            for (Map.Entry<String, ClientInfo> entry : client.users.entrySet()) {
        		System.out.println("From client " + entry.getKey() + " " + entry.getValue());
            }
            System.out.println("UserFiles into client");
            for (Map.Entry<String, HashSet<String>> userFiles : client.userFiles.entrySet()) {
            	System.out.println("From client: " + userFiles.getKey());
    			for (String filePath : userFiles.getValue())
    			{
    				System.out.println("From client: " + filePath);
    			}
            }	
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			System.out.println("=> cannot connect to server on localhost:8080, make sure that the server is started");
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			System.out.println("Class not found exception");
		}
	
	}

}
