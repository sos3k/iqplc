package pl.iqsoft.plc.collector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public abstract class CollectorTask implements Runnable {

	protected Socket connection;
	protected BufferedWriter out;
	protected BufferedReader in;
	
	public CollectorTask(Socket connection, BufferedWriter out, BufferedReader in) {
		this.connection = connection;
		this.out = out;
		this.in = in;
	}
	
	public abstract void runCollection();
	public abstract String getCommand();
	public abstract Integer getResponseByteSize();
	public abstract Number getValue(char[] valueChars);
	
	protected Map<Integer, Number> readData() throws Exception {
		String command = getCommand();
		Integer responseByteSize = getResponseByteSize();
		
		char[] buffer = new char[256];
		char[] valueChars = null;
		
		Map<Integer, Number> values = new TreeMap<>();
		
		out.write(command);
		out.flush();
		
		System.out.println("Wys³a³em polecenie " + command);
			
		int data = in.read(buffer);
		
		System.out.println("Przeczyta³em " + data + " bajtów.");
		
		for (int i=0; i<buffer.length; i++) {
			if (i % responseByteSize == 0) {
				valueChars = new char[responseByteSize];
			}

			valueChars[i % responseByteSize] = buffer[i];
			
			if ((i % responseByteSize) == (responseByteSize - 1)) {
				values.put((int) (i / responseByteSize), getValue(valueChars));
			}
		}
		
		return values;
	}
	
	@Override
	public void run() {
		try {
			if (this.connection != null && this.out != null && this.in != null) {
				synchronized (this.connection) {
					runCollection();	
				}				
			}			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
