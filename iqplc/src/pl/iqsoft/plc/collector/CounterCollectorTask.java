package pl.iqsoft.plc.collector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

import pl.iqsoft.plc.utils.ByteUtils;

public class CounterCollectorTask extends CollectorTask {

	public CounterCollectorTask(Socket connection, BufferedWriter out, BufferedReader in) {
		super(connection, out, in);
	}

	@Override
	public String getCommand() {
		return "3000";
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
