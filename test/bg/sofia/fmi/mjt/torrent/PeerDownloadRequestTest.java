package bg.sofia.fmi.mjt.torrent;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;

public class PeerDownloadRequestTest {
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	@Test
	public void ShouldSaveFileWhenGivenCorrectInputStream() throws IOException {
		// Set it first
		final String fileLocation = "test.txt";
		final String outputText = "example text";
		File file = tempFolder.newFile(fileLocation);
		InputStream stream = new ByteArrayInputStream(outputText.getBytes());
		Socket socket = mock(Socket.class);
		//InputStream stream = mock( InputStream.class );
		when(socket.getInputStream()).thenReturn( stream );
		
		//when(stream.read( any(byte[].class), anyInt(), anyInt() )).thenReturn( 4 );
		Runnable dr = new PeerDownloadRequest(socket, file.getAbsolutePath());
		// when
		dr.run();
		// verify
		byte[] expectedData = outputText.getBytes();
		Path path = Paths.get(file.getAbsolutePath());
	    byte[] actualData = Files.readAllBytes(path);
	    Assert.assertArrayEquals( expectedData, actualData);
	}
}
