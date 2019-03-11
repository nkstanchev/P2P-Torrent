package bg.sofia.fmi.mjt.torrent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeerServerListener implements Runnable {

	private static final Logger LOGGER = Logger.getLogger( ServerListener.class.getName() );
	private Socket socket;
	
	public PeerServerListener(Socket socket) {
		this.socket = socket;
	}
	private void download(Socket socket, String fileLocation) throws IOException {
		
        FileInputStream fis = new FileInputStream(fileLocation);
        OutputStream os = socket.getOutputStream();
        System.out.println("Sending " + fileLocation);
        byte[] bytes = new byte[16 * 1024];
        int count;
        while ((count = fis.read(bytes)) > 0) {
        	os.write(bytes, 0, count);
        }
        fis.close();
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
                System.out.println("Receiverd mini server command is " + line);
                String[] splitByWords = line.split(" "); 
                String command = splitByWords[0];
                switch(command) {
                	case "download":	
                		download(socket, splitByWords[1]);
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
            try { socket.close(); } 
            catch (IOException e) {
            	LOGGER.log(Level.SEVERE, e.toString(), e);
                System.out.println("Error closing socket");
            }
            System.out.println("Connection with client closed");
        }
	}

}
