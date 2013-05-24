package pl.iqsoft.plc.collector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.Map;

import pl.iqsoft.plc.utils.ByteUtils;

public class SlowFloatCollectorTask extends CollectorTask {

	public SlowFloatCollectorTask(Socket connection, BufferedWriter out, BufferedReader in) {
		super(connection, out, in);
	}

	@Override
	public void runCollection() {
		System.out.println("Uruchomi�em w�tek SlowFloatCollectorTask");
		
		try {
			Map<Integer, Number> values = readData();
			
			System.out.println("Przeczyta�em " + values.size() + " warto�ci. Pierwsza z brzegu to: " + (values.size() > 0 ? values.get(0) : -1));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getCommand() {
		return "2000";
	}

	@Override
	public Integer getResponseByteSize() {
		return 4;
	}

	@Override
	public Float getValue(char[] valueChars) {
		ByteUtils byteUtils = new ByteUtils();
		Float floatValue = byteUtils.floatFromCharArray(valueChars);				
		
		if (floatValue != 0) {
			System.out.println("Warto�� : "+ String.copyValueOf(valueChars));
		}
		
		return floatValue;
	}
}
