package bg.sofia.fmi.mjt.torrent;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;



public class ServerListenerTest {
	
	
	@Test
	public void connectShouldSaveUserWhenGivenProperCommand() {	  
		   
			//throw new NullPointerException();
			throw new IOException();
		   // mock socket
		   // add command to inputstream
//		   final String cmd = "connect niki 1234";
//		   final String terminationString = "";
//		   final String expectedKey = "niki";
//		   InputStream stream = new ByteArrayInputStream(cmd.getBytes());
//		   Socket socket = mock(Socket.class);
//		   when(socket.getInputStream()).thenReturn( stream );
//		   InetAddress ia = mock(InetAddress.class);
//		   when(socket.getInetAddress()).thenReturn(ia);
//		   when(ia.getHostAddress()).thenReturn("127.0.0.1");
//		   when(socket.getPort()).thenReturn(1234);
//		   ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		   when(socket.getOutputStream()).thenReturn(baos);
//		   
//		   ConcurrentHashMap<String, ClientInfo> users = new ConcurrentHashMap<>();
//		   ConcurrentHashMap<String, HashSet<String>> userFiles = new ConcurrentHashMap<>();
//		   Server server = mock(Server.class);
//		   when(server.getUsers()).thenReturn(users);
//		   when(server.getUserFiles()).thenReturn(userFiles);
//		   
//		   // run method
//		   
//	   ServerListener sl = new ServerListener(socket, server);
//	   sl.run();
//	   // check if outputstream returns expected info
//	   assertTrue(users.containsKey(expectedKey) && users.get(expectedKey) != null);
//	   stream = new ByteArrayInputStream(terminationString.getBytes());
	}
	
   @Test
   public void registerShouldRegisterCorrectlyWhenGivenProperCommand() throws IOException {	  
	   
	   // mock socket
	   // add command to inputstream
	   final String cmd = "connect niki 1234" + System.lineSeparator() 
	   					+ "register niki niki.txt";
	   final String terminationString = "";
	   final String expectedOutput = "Sucessfully registered files:" + System.lineSeparator()
	   				+ "niki.txt";
	   ByteArrayOutputStream expectedBaos = new ByteArrayOutputStream();
	   PrintWriter out = new PrintWriter(expectedBaos, true);
	   out.println(expectedOutput);
	   InputStream stream = new ByteArrayInputStream(cmd.getBytes());
	   Socket socket = mock(Socket.class);
	   when(socket.getInputStream()).thenReturn( stream );
	   InetAddress ia = mock(InetAddress.class);
	   when(socket.getInetAddress()).thenReturn(ia);
	   when(ia.getHostAddress()).thenReturn("127.0.0.1");
	   when(socket.getPort()).thenReturn(1234);
	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
	   when(socket.getOutputStream()).thenReturn(baos);
	   
	   ConcurrentHashMap<String, ClientInfo> users = new ConcurrentHashMap<>();
	   ConcurrentHashMap<String, HashSet<String>> userFiles = new ConcurrentHashMap<>();
	   Server server = mock(Server.class);
	   when(server.getUsers()).thenReturn(users);
	   when(server.getUserFiles()).thenReturn(userFiles);
	   
	   // run method
	   
	   ServerListener sl = new ServerListener(socket, server);
	   sl.run();
	   
	   byte[] expectedData = expectedBaos.toByteArray();
	   byte[] actualData = baos.toByteArray();
	   // check if outputstream returns expected info
	   Assert.assertArrayEquals( expectedData, actualData);
	   stream = new ByteArrayInputStream(terminationString.getBytes());
   }
   @Test
   public void registerShouldReturnInfoMessageWhenGivenBadCommand() throws IOException {
	   
	   // mock socket
	   // add command to inputstream
	   final String cmd = "connect niki 1234" + System.lineSeparator() 
	   					+ "register niki";
	   final String terminationString = "";
	   final String expectedOutput = "File Register Error - Invalid arguments";
	   ByteArrayOutputStream expectedBaos = new ByteArrayOutputStream();
	   PrintWriter out = new PrintWriter(expectedBaos, true);
	   out.println(expectedOutput);
	   InputStream stream = new ByteArrayInputStream(cmd.getBytes());
	   Socket socket = mock(Socket.class);
	   when(socket.getInputStream()).thenReturn( stream );
	   InetAddress ia = mock(InetAddress.class);
	   when(socket.getInetAddress()).thenReturn(ia);
	   when(ia.getHostAddress()).thenReturn("127.0.0.1");
	   when(socket.getPort()).thenReturn(1234);
	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
	   when(socket.getOutputStream()).thenReturn(baos);
	   
	   ConcurrentHashMap<String, ClientInfo> users = new ConcurrentHashMap<>();
	   ConcurrentHashMap<String, HashSet<String>> userFiles = new ConcurrentHashMap<>();
	   Server server = mock(Server.class);
	   when(server.getUsers()).thenReturn(users);
	   when(server.getUserFiles()).thenReturn(userFiles);
	   
	   // run method
	   
	   ServerListener sl = new ServerListener(socket, server);
	   sl.run();
	   
	   byte[] expectedData = expectedBaos.toByteArray();
	   byte[] actualData = baos.toByteArray();
	   // check if outputstream returns expected info
	   Assert.assertArrayEquals( expectedData, actualData);
	   stream = new ByteArrayInputStream(terminationString.getBytes());
   }
   @Test
   public void registerShouldReturnInfoMessageWhenUserDoesNotExist() throws IOException {	  
	// mock socket
	   // add command to inputstream
	   final String cmd = "connect niki 1234" + System.lineSeparator() 
	   					+ "register gosho test.txt";
	   final String terminationString = "";
	   final String expectedOutput = "File Register Error - No such user";
	   ByteArrayOutputStream expectedBaos = new ByteArrayOutputStream();
	   PrintWriter out = new PrintWriter(expectedBaos, true);
	   out.println(expectedOutput);
	   InputStream stream = new ByteArrayInputStream(cmd.getBytes());
	   Socket socket = mock(Socket.class);
	   when(socket.getInputStream()).thenReturn( stream );
	   InetAddress ia = mock(InetAddress.class);
	   when(socket.getInetAddress()).thenReturn(ia);
	   when(ia.getHostAddress()).thenReturn("127.0.0.1");
	   when(socket.getPort()).thenReturn(1234);
	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
	   when(socket.getOutputStream()).thenReturn(baos);
	   
	   ConcurrentHashMap<String, ClientInfo> users = new ConcurrentHashMap<>();
	   ConcurrentHashMap<String, HashSet<String>> userFiles = new ConcurrentHashMap<>();
	   Server server = mock(Server.class);
	   when(server.getUsers()).thenReturn(users);
	   when(server.getUserFiles()).thenReturn(userFiles);
	   
	   // run method
	   
	   ServerListener sl = new ServerListener(socket, server);
	   sl.run();
	   
	   byte[] expectedData = expectedBaos.toByteArray();
	   byte[] actualData = baos.toByteArray();
	   // check if outputstream returns expected info
	   Assert.assertArrayEquals( expectedData, actualData);
	   stream = new ByteArrayInputStream(terminationString.getBytes());
   }
   @Test
   public void unregisterShouldUnregisterCorrectlyWhenGivenProperCommand() throws IOException {	  
	// mock socket
	   // add command to inputstream
	   final String cmd = "connect niki 1234" + System.lineSeparator() 
	   					+ "register niki test.txt" + System.lineSeparator()
	   					+ "unregister niki test.txt test2.txt";
	   final String terminationString = ""; 
	   final String expectedOutput = "Sucessfully registered files:" + System.lineSeparator()
					+ "test.txt" + System.lineSeparator() + 
					"Sucessfully unregistered files:" + System.lineSeparator() +
					"test.txt";
	   ByteArrayOutputStream expectedBaos = new ByteArrayOutputStream();
	   PrintWriter out = new PrintWriter(expectedBaos, true);
	   out.println(expectedOutput);
	   InputStream stream = new ByteArrayInputStream(cmd.getBytes());
	   Socket socket = mock(Socket.class);
	   when(socket.getInputStream()).thenReturn( stream );
	   InetAddress ia = mock(InetAddress.class);
	   when(socket.getInetAddress()).thenReturn(ia);
	   when(ia.getHostAddress()).thenReturn("127.0.0.1");
	   when(socket.getPort()).thenReturn(1234);
	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
	   when(socket.getOutputStream()).thenReturn(baos);
	   
	   ConcurrentHashMap<String, ClientInfo> users = new ConcurrentHashMap<>();
	   ConcurrentHashMap<String, HashSet<String>> userFiles = new ConcurrentHashMap<>();
	   Server server = mock(Server.class);
	   when(server.getUsers()).thenReturn(users);
	   when(server.getUserFiles()).thenReturn(userFiles);
	   
	   // run method
	   
	   ServerListener sl = new ServerListener(socket, server);
	   sl.run();
	   
	   byte[] expectedData = expectedBaos.toByteArray();
	   byte[] actualData = baos.toByteArray();
	   // check if outputstream returns expected info
	   Assert.assertArrayEquals( expectedData, actualData);
	   stream = new ByteArrayInputStream(terminationString.getBytes());
   }
   @Test
   public void unregisterShouldReturnInfoMessageWhenGivenBadCommand() throws IOException {	  
	   // mock socket
	   // add command to inputstream
	   final String cmd = "connect niki 1234" + System.lineSeparator() 
	   					+ "register niki test.txt" + System.lineSeparator()
	   					+ "unregister niki";
	   final String terminationString = ""; 
	   final String expectedOutput = "Sucessfully registered files:" + System.lineSeparator()
					+ "test.txt" + System.lineSeparator() + 
					"File Register Error - Invalid arguments";
	   ByteArrayOutputStream expectedBaos = new ByteArrayOutputStream();
	   PrintWriter out = new PrintWriter(expectedBaos, true);
	   out.println(expectedOutput);
	   InputStream stream = new ByteArrayInputStream(cmd.getBytes());
	   Socket socket = mock(Socket.class);
	   when(socket.getInputStream()).thenReturn( stream );
	   InetAddress ia = mock(InetAddress.class);
	   when(socket.getInetAddress()).thenReturn(ia);
	   when(ia.getHostAddress()).thenReturn("127.0.0.1");
	   when(socket.getPort()).thenReturn(1234);
	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
	   when(socket.getOutputStream()).thenReturn(baos);
	   
	   ConcurrentHashMap<String, ClientInfo> users = new ConcurrentHashMap<>();
	   ConcurrentHashMap<String, HashSet<String>> userFiles = new ConcurrentHashMap<>();
	   Server server = mock(Server.class);
	   when(server.getUsers()).thenReturn(users);
	   when(server.getUserFiles()).thenReturn(userFiles);
	   
	   // run method
	   
	   ServerListener sl = new ServerListener(socket, server);
	   sl.run();
	   
	   byte[] expectedData = expectedBaos.toByteArray();
	   byte[] actualData = baos.toByteArray();
	   // check if outputstream returns expected info
	   Assert.assertArrayEquals( expectedData, actualData);
	   stream = new ByteArrayInputStream(terminationString.getBytes());
   }
   @Test
   public void unregisterShouldReturnInfoMessageWhenUserDoesNotExist() throws IOException {	  
	   // mock socket
	   // add command to inputstream
	   final String cmd = "connect niki 1234" + System.lineSeparator() 
	   					+ "register niki test.txt" + System.lineSeparator()
	   					+ "unregister gosho test.txt";
	   final String terminationString = ""; 
	   final String expectedOutput = "Sucessfully registered files:" + System.lineSeparator()
					+ "test.txt" + System.lineSeparator() + 
					"File Register Error - No such user";
	   ByteArrayOutputStream expectedBaos = new ByteArrayOutputStream();
	   PrintWriter out = new PrintWriter(expectedBaos, true);
	   out.println(expectedOutput);
	   InputStream stream = new ByteArrayInputStream(cmd.getBytes());
	   Socket socket = mock(Socket.class);
	   when(socket.getInputStream()).thenReturn( stream );
	   InetAddress ia = mock(InetAddress.class);
	   when(socket.getInetAddress()).thenReturn(ia);
	   when(ia.getHostAddress()).thenReturn("127.0.0.1");
	   when(socket.getPort()).thenReturn(1234);
	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
	   when(socket.getOutputStream()).thenReturn(baos);
	   
	   ConcurrentHashMap<String, ClientInfo> users = new ConcurrentHashMap<>();
	   ConcurrentHashMap<String, HashSet<String>> userFiles = new ConcurrentHashMap<>();
	   Server server = mock(Server.class);
	   when(server.getUsers()).thenReturn(users);
	   when(server.getUserFiles()).thenReturn(userFiles);
	   
	   // run method
	   
	   ServerListener sl = new ServerListener(socket, server);
	   sl.run();
	   
	   byte[] expectedData = expectedBaos.toByteArray();
	   byte[] actualData = baos.toByteArray();
	   // check if outputstream returns expected info
	   Assert.assertArrayEquals( expectedData, actualData);
	   stream = new ByteArrayInputStream(terminationString.getBytes());
   }
}