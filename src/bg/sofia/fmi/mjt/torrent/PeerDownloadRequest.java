package bg.sofia.fmi.mjt.torrent;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeerDownloadRequest implements Runnable {
	private static final Logger LOGGER = Logger.getLogger( PeerDownloadRequest.class.getName() );
	private Socket socket;
	private String fileLocation;
	
	public PeerDownloadRequest(Socket socket, String fileLocation) {
		this.socket = socket;
		this.fileLocation = fileLocation;
	}
	private void saveFile() throws IOException {
		//BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		InputStream in = socket.getInputStream();
		FileOutputStream out = new FileOutputStream(fileLocation);
		byte[] bytes = new byte[16 * 1024];
		int count;
	    
		while ((count = in.read(bytes)) > 0) {
	    	out.write(bytes, 0, count);
	    }
	}
	
	
	@Override
	public void run() {
		try {
			if (socket.isClosed()) {
				System.out.println("client socket is closed, stop waiting for server messages");
				return;
			}
			saveFile();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			System.out.println("Failed receving message from server");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
				System.out.println("Failed closing server socket");
			}
		}
	}

}