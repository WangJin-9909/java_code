package com.ms.icbc.security.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import cn.com.icbc.ms.token.utils.Base64Encoder;
public class Base64 {
	public Base64() {

	}

	private static final Base64Encoder encoder = new Base64Encoder();

	public static byte[] encode(byte[] data) throws IllegalArgumentException {
		if (null == data) {
			throw new IllegalArgumentException("data is null");
		}
		return encode(data, 0, data.length);
	}

	public static byte[] encode(byte[] data, int off, int length) throws IllegalArgumentException {
		if (null == data) {
			throw new IllegalArgumentException("data is null");
		}
		int len = (length + 2) / 3 * 4;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);
		try {
			encoder.encode(data, off, length, bOut);
		} catch (Exception e) {
		}

		return bOut.toByteArray();
	}

	public static int encode(byte[] data, OutputStream out) throws IOException, IllegalArgumentException {
		if (null == data || null == out) {
			throw new IllegalArgumentException("data is null");
		}
		return encoder.encode(data, 0, data.length, out);
	}

	public static int encode(byte[] data, int off, int length, OutputStream out) throws IOException {
		if (null == data || null == out) {
			throw new IllegalArgumentException("data is null");
		}
		return encoder.encode(data, off, length, out);
	}

	public static byte[] decode(byte[] data) {
		if(null == data) {
			 throw new IllegalArgumentException("data is null");
		  }
		int len = data.length / 4 * 3;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);
		try {
			encoder.decode(data, 0, data.length, bOut);
		} catch (Exception e) {
		}

		return bOut.toByteArray();
	}

	public static byte[] decode(String data) throws IllegalArgumentException {
		if (null == data) {
			throw new IllegalArgumentException("data is null");
		}
		int len = data.length() / 4 * 3;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream(len);
		try {
			encoder.decode(data, bOut);
		} catch (Exception e) {
		}

		return bOut.toByteArray();
	}

	public static int decode(String data, OutputStream out) throws IOException, IllegalAccessException {
		if (null == data || null == out) {
			throw new IllegalAccessException("data is null");
		}
		return encoder.decode(data, out);
	}
}
