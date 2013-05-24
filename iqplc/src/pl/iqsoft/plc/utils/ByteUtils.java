package pl.iqsoft.plc.utils;

import java.nio.ByteBuffer;

public class ByteUtils {
	public byte[] toByteArray(int value) {
	     return  ByteBuffer.allocate(4).putInt(value).array();
	}

	public int fromByteArray(byte[] bytes) {
	     return ByteBuffer.wrap(bytes).getInt();
	}
	
	public float floatFromCharArray(char[] chars) {
		byte[] bytes = new byte[chars.length];
		
		for (int i=0; i< chars.length; i++) {
			bytes[i] = (byte) chars[i];
		}
		
		int floatInteger = fromByteArray(bytes);
		
		return Float.intBitsToFloat(floatInteger);
	}
	
	public long longFromCharArray(char[] chars) {
		byte[] bytes = new byte[chars.length];
		
		for (int i=0; i< chars.length; i++) {
			bytes[i] = (byte) chars[i];
		}
		
		long floatInteger = fromByteArray(bytes);
		
		return floatInteger;
	}
}
