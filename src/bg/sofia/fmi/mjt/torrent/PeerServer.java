package bg.sofia.fmi.mjt.torrent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeerServer implements Runnable {
	
	private Client client;
	private static final Logger LOGGER = Logger.getLogger( PeerServer.class.getName() );
	
	public PeerServer(Client client) {
		this.client = client;
	}
	@Override
	public void run() {
		try (ServerSocket miniServer = new ServerSocket(0)) {
			this.client.miniServerPort = miniServer.getLocalPort();
			while (true) {
				Socket peerConnection = miniServer.accept();
				PeerServerListener miniServerListener = new PeerServerListener(peerConnection);
				new Thread(miniServerListener).start();
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			System.out.println("Error: Starting peer miniserver failed!");
		}
	}
}
