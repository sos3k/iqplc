package pl.iqsoft.plc.collector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.Map;

import pl.iqsoft.plc.utils.ByteUtils;

public class StatusCollectorTask extends CollectorTask {

	public StatusCollectorTask(Socket connection, BufferedWriter out, BufferedReader in) {
		super(connection, out, in);
	}

	@Override
	public void runCollection() {
		System.out.println("Uruchomi³em w¹tek StatusCollectorTask");
		
		try {
			Map<Integer, Number> values = readData();
			
			System.out.println("Przeczyta³em " + values.size() + " wartoœci. Pierwsza z brzegu to: " + (values.size() > 0 ? values.get(0) : -1));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getCommand() {
		return "4000";
	}

	@Override
	public Integer getResponseByteSize() {
		return 4;
	}

	@Override
	public Long getValue(char[] valueChars) {
		ByteUtils byteUtils = new ByteUtils();
		Long longValue = byteUtils.longFromCharArray(valueChars);
		
		if (longValue != 0) {
			System.out.println("Wartoœæ : "+ String.valueOf(longValue));
		}
		
		return longValue;
	}
}
