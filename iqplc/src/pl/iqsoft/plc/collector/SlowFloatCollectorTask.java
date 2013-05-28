package pl.iqsoft.plc.collector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

import pl.iqsoft.plc.utils.ByteUtils;

public class SlowFloatCollectorTask extends CollectorTask {

	public SlowFloatCollectorTask(Socket connection, BufferedWriter out, BufferedReader in) {
		super(connection, out, in);
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
			System.out.println("Wartoœæ : "+ String.copyValueOf(valueChars));
		}
		
		return floatValue;
	}
}
