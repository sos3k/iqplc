package pl.iqsoft.plc.collector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public abstract class CollectorTask implements Runnable {

	protected Socket connection;
	protected BufferedWriter out;
	protected BufferedReader in;
	
	public CollectorTask(Socket connection, BufferedWriter out, BufferedReader in) {
		this.connection = connection;
		this.out = out;
		this.in = in;
	}
	
	public abstract String getCommand();
	public abstract Integer getResponseByteSize();
	public abstract Number getValue(char[] valueChars);
	
	private List<Number> readData() throws Exception {
		String command = getCommand();
		Integer responseByteSize = getResponseByteSize();
		
		char[] buffer = new char[256];
		char[] valueChars = null;
		
		List<Number> values = new LinkedList<>();
		
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
				Number n = getValue(valueChars);
				values.add(n);
			}
		}
		
		return values;
	}
	
	@Override
	public void run() {
		try {
			if (this.connection != null && this.out != null && this.in != null) {
				synchronized (this.connection) {
					System.out.println("Uruchomi³em w¹tek " + getClass().getCanonicalName());
					
					List<Number> values = readData();
					
					System.out.println("Przeczyta³em " + values.size() + " wartoœci. Pierwsza z brzegu to: " + (values.size() > 0 ? values.get(0) : -1));
				}				
			}			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
