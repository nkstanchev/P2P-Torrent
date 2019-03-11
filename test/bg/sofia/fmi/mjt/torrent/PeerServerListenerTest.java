package bg.sofia.fmi.mjt.torrent;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PeerServerListenerTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	
	@Test
	public void shouldSendCorrectFileWhenGivenDownloadCommandAndFileExists() throws IOException {
		
		// setup
		final String fileLocation = "test.txt";
		final String outputText = "download " + fileLocation;
		final String fileText = "example text";
		final String terminationText = "";
		File file = new File(fileLocation);	
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		writer.print(fileText);
		writer.close();
		InputStream stream = new ByteArrayInputStream(outputText.getBytes());
		// when
		Socket socket = mock(Socket.class);
		when(socket.getInputStream()).thenReturn( stream );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		when(socket.getOutputStream()).thenReturn( baos );
		PeerServerListener psl = new PeerServerListener(socket);
		psl.run();
		
		byte[] expectedData = fileText.getBytes();
		byte[] actualData = baos.toByteArray();
		
		Assert.assertArrayEquals( expectedData, actualData);
		// stop it somehow
		stream = new ByteArrayInputStream(terminationText.getBytes());
	}
	
	@Rule public ExpectedException thrown= ExpectedException.none();
	
//	@Test
//	public void shouldThrowExceptionWhenGivenDownloadCommandAndFileDoesNotExists() throws IOException {
//		
////		byte[] expectedData = outputText.getBytes();
////		Path path = Paths.get(file.getAbsolutePath());
////		byte[] actualData = Files.readAllBytes(path);
////		Assert.assertArrayEquals( expectedData, actualData);
//			    
//		final String fileLocation = "test2.txt";
//		final String outputText = "download " + fileLocation;
//		final String fileText = "example text";
//		final String terminationText = "";
//		InputStream stream = new ByteArrayInputStream(outputText.getBytes());
//		Socket socket = mock(Socket.class);
//		when(socket.getInputStream()).thenReturn( stream );
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		PeerServerListener psl = new PeerServerListener(socket,socket.getInputStream(), baos);
//		psl.run();
//		thrown.expect( FileNotFoundException.class );
//		// stop it somehow
//	}
	
}
