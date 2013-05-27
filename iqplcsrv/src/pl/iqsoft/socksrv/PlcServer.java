package pl.iqsoft.socksrv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class PlcServer {
	ServerSocket providerSocket;
	Socket connection = null;
	BufferedWriter out;
	BufferedReader in;
	String message;
	
	PlcServer(){}
	
	void run()
	{
		try {
			//1. creating a server socket
			providerSocket = new ServerSocket(5555, 10);
			//2. Wait for connection
			System.out.println("Waiting for connection");
			connection = providerSocket.accept();
			System.out.println("Connection received from " + connection.getInetAddress().getHostName());
			//3. get Input and Output streams
			out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//			sendMessage("Connection successful");
			
			//4. The two parts communicate via the input and output streams
			while (true) {
				char[] buffer = new char[256];
				int dataSize = in.read(buffer);
						
				if (dataSize < 0) {
					break;
				}
				else {
					message = String.valueOf(buffer).trim();
					
					if (message != null && message.equals("bye")) {
						sendMessage("bye");
						break;
					}
					else {
//						System.out.println("client>" + message + " [size: " + dataSize + "]");
						char[] cbuf = CharBuffer.allocate(4).put('1').put('2').put('3').put('4').array();
						
						sendMessage(String.valueOf(cbuf));
					}
				}
			}
			
			System.out.println("Koniec pracy");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				connection.close();
				providerSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	void sendMessage(String msg)
	{
		try {
			out.write(msg);
			out.flush();
//			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PlcServer server = new PlcServer();
		
		while(true){
			server.run();
		}
	}

}
