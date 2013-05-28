package pl.iqsoft.plc.utils;

import java.nio.ByteBuffer;

public class ByteUtils {
	public byte[] toByteArray(int value) {
	     return  ByteBuffer.allocate(4).putInt(value).array();
	}
	
	public char[] toCharArray(int value) {
		System.err.println("INT: " + value);
				
		byte[] bytes = toByteArray(value);
		char[] chars = new char[bytes.length];
		
		for (int i=0; i<bytes.length; i++) {
			chars[i] = (char) bytes[i];
		}
		
		return chars;
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
	
	public String convertCharArrayToString(char[] chars) {
		StringBuffer stringBuffer = new StringBuffer();
		
		for (int i=0; i<chars.length; i++) {
			stringBuffer.append(String.format("%1$04x", (chars[i] & 0xFFFF)));			
		}
		
		return stringBuffer.toString();
	}
	
	public String convertByteArrayToString(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		
		for (int i=0; i<bytes.length; i++) {
			stringBuffer.append(String.format("%1$04x", (bytes[i] & 0xFFFF)));			
		}
		
		return stringBuffer.toString();
	}
}
