package bg.sofia.fmi.mjt.torrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientRunnable implements Runnable {

	private static final Logger LOGGER = Logger.getLogger( ClientRunnable.class.getName() );
	private Socket socket;
	
	public ClientRunnable(Socket socket, Client client) {
		this.socket = socket;
	}
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (true) {
				if (socket.isClosed()) {
					System.out.println("client socket is closed, stop waiting for server messages");
					return;
				}
				System.out.println(reader.readLine());
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			System.out.println("Failed receving message from server");
		}
	}

}