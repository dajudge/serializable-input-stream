package com.dajudge.serinstream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Instance of an in-memory serialization store.
 * 
 * @author Alex Stockinger
 * 
 */
public class MemorySerializationTempStoreInstance implements TempStoreInstance {

	private ByteArrayInputStream stream;

	@Override
	public <T> T store(OutputStreamCallback<T> callback) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		T ret = callback.callback(outStream);
		stream = new ByteArrayInputStream(outStream.toByteArray());
		return ret;
	}

	@Override
	public InputStream retrieve() {
		if (stream == null) {
			throw new IllegalStateException("No input stream is available");
		}
		ByteArrayInputStream ret = stream;
		stream = null;
		return ret;
	}

	@Override
	public void release() {
	}
}
