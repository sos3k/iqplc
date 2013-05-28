package pl.iqsoft.socksrv;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Random;

import pl.iqsoft.plc.utils.ByteUtils;

public class PlcServer {
	
	private ServerSocket providerSocket;
	private Socket connection = null;
	private BufferedOutputStream out;
	private BufferedInputStream in;
	private String message;
	
	private ByteUtils byteUtils;
	
	public PlcServer() {
	}
	
	void run() {
		try {
			this.byteUtils = new ByteUtils();
			//1. creating a server socket
			this.providerSocket = new ServerSocket(5555, 10);
			//2. Wait for connection
			System.out.println("Waiting for connection");
			this.connection = this.providerSocket.accept();
			System.out.println("Connection received from " + this.connection.getInetAddress().getHostName());
			//3. get Input and Output streams
			this.out = new BufferedOutputStream(this.connection.getOutputStream());
			this.in = new BufferedInputStream(this.connection.getInputStream());
			
			//4. The two parts communicate via the input and output streams
			while (true) {
				byte[] buffer = new byte[256];
				int dataSize = this.in.read(buffer);
						
				if (dataSize < 0) {
					break;
				}
				else {
					this.message = String.valueOf(buffer).trim();
					
					if (this.message != null && this.message.equals("bye")) {
						sendMessage("bye".getBytes());
						break;
					}
					else {
						ByteBuffer byteBuffer = ByteBuffer.allocate(256);
						Random random = new Random();
						
						for (int i=0; i<64; i++) {
							Float floatValue = (random.nextFloat() * 100);
							
							Integer integerValue = Float.floatToIntBits(floatValue);
							byte[] bytes = this.byteUtils.toByteArray(integerValue);
							byteBuffer.put(bytes);
						}
						
						sendMessage(byteBuffer.array());
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
	
	private void sendMessage(byte[] bytes) {
		try {
			out.write(bytes);
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
