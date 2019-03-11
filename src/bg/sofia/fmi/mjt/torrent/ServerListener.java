package bg.sofia.fmi.mjt.torrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerListener implements Runnable {
	
	private static final Logger LOGGER = Logger.getLogger( ServerListener.class.getName() );
	private Socket socket;
	private Server server;
	
	private void listAllFiles(PrintWriter out) {
		out.println("Users:");
		for (Entry<String, ClientInfo> entry : server.getUsers().entrySet()) {
			out.println(entry.getKey() + " " + entry.getValue());
		}
		out.println("User Files:");
		for (Entry<String, HashSet<String>> userFiles : server.getUserFiles().entrySet()) {
			out.println(userFiles.getKey());
			for (String filePath : userFiles.getValue())
			{
				out.println(filePath);
			}
		}
	}
	private void connect(String[] splitByWords) throws IOException {
		ClientInfo ci = new ClientInfo(socket.getInetAddress().getHostAddress(), socket.getPort(), Integer.parseInt(splitByWords[2]));
		server.getUsers().putIfAbsent(splitByWords[1], ci);
	}
	private void registerFiles(String[] splitByWords, PrintWriter out) {
		
		if (splitByWords.length < 3) {
			out.println("File Register Error - Invalid arguments");
			return;
		}
		if (!(server.getUsers().containsKey(splitByWords[1]))) {
			out.println("File Register Error - No such user");
			return;
		}
		for (int i = 2; i < splitByWords.length; i++) {
			server.getUserFiles().putIfAbsent(splitByWords[1], new HashSet<String>());
			server.getUserFiles().get(splitByWords[1]).add(splitByWords[i]);
		}
		out.println("Sucessfully registered files:");
		for (int i = 2; i < splitByWords.length; i++) {
			out.println(splitByWords[i]);
		}
	}
	private void unregisterFiles(String[] splitByWords, PrintWriter out) {
		if (splitByWords.length < 3) {
			out.println("File Register Error - Invalid arguments");
			return;
		}
		HashSet<String> set = server.getUserFiles().get(splitByWords[1]);
		if (set == null) {
			out.println("File Register Error - No such user");
			return;
		}
		out.println("Sucessfully unregistered files:");
		for (int i = 2; i < splitByWords.length; i++) {
			boolean isRemoved = set.remove(splitByWords[i]);
			if (isRemoved) {
				out.println(splitByWords[i]);
			}
		}
		for (Entry<String, HashSet<String>> userFiles : server.getUserFiles().entrySet()) {
			System.out.println(userFiles.getKey());
			for (String filePath : userFiles.getValue())
			{
				System.out.println(filePath);
			}
		}
	}
	private void update(Socket socket) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(server.getUsers());
    	oos.writeObject(server.getUserFiles());
	}
	public ServerListener(Socket socket, Server server)
	{
		this.socket = socket;
		this.server = server;
	}
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            while (true) {
                String line = in.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }
                String[] splitByWords = line.split(" "); 
                String command = splitByWords[0];
                switch(command) {
                	case "connect":
                		connect(splitByWords);
                		break;
                	case "update":                		
                		update(socket);
                		break;
                	case "list-files":
                		listAllFiles(out);
                		break;
                	case "register":
                		registerFiles(splitByWords, out);
                		break;
                	case "unregister":
                		unregisterFiles(splitByWords, out);
                		break;
                	default:
                		out.println("Invalid command given!");
                		break;
                }
            }
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
            System.out.println("Error handling client");
        } finally {
            try { socket.close(); } catch (IOException e) {}
            System.out.println("Connection with client closed");
        }
	}
}
