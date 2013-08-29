package com.dajudge.serinstream;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

import org.junit.Test;

public class SerializableInputStreamTest {
	private static final SerializableInputStreamConfiguration CONFIG = SerializableInputStreamConfiguration.getInstance();
	private static final Charset CHARSET = Charset.forName("UTF-8");
	private static final int DEFAULT_WRITE_CHUNK_SIZE = 2048;
	private static final SerializationTempStore DEFAULT_SERIALIZATION_TEMP_STORE = new MemorySerializationTempStore();

	@Test
	public void testWithTempFile() throws IOException, ClassNotFoundException {
		final TempFileSerializationTempStore tempFileStore = new TempFileSerializationTempStore();
		CONFIG.setDefaultWriteChunkSize(DEFAULT_WRITE_CHUNK_SIZE);
		CONFIG.setSerializationTempStore(tempFileStore);
		check();
	}

	@Test
	public void testWithMemory() throws IOException, ClassNotFoundException {
		final MemorySerializationTempStore memoryStore = new MemorySerializationTempStore();
		CONFIG.setDefaultWriteChunkSize(DEFAULT_WRITE_CHUNK_SIZE);
		CONFIG.setSerializationTempStore(memoryStore);
		check();
	}

	@Test
	public void testWithSmallChunkSize() throws IOException, ClassNotFoundException {
		CONFIG.setSerializationTempStore(DEFAULT_SERIALIZATION_TEMP_STORE);
		CONFIG.setDefaultWriteChunkSize(1024 * 1024);
		check();
	}

	@Test
	public void testWithLargeChunkSize() throws IOException, ClassNotFoundException {
		CONFIG.setSerializationTempStore(DEFAULT_SERIALIZATION_TEMP_STORE);
		CONFIG.setDefaultWriteChunkSize(1);
		check();
	}

	private void check() throws IOException, ClassNotFoundException {
		final String payloadString = createPayload();
		final byte[] data = payloadString.getBytes(CHARSET);
		final ByteArrayInputStream payload = new ByteArrayInputStream(data);
		final SerializableInputStream out = new SerializableInputStream(payload);

		final ByteArrayOutputStream serializedOutputStream = new ByteArrayOutputStream();
		final ObjectOutputStream oos = new ObjectOutputStream(serializedOutputStream);
		oos.writeObject(out);
		oos.close();
		out.close();

		final ByteArrayInputStream serializedInputStream = new ByteArrayInputStream(serializedOutputStream.toByteArray());
		final ObjectInputStream ois = new ObjectInputStream(serializedInputStream);
		final SerializableInputStream in = (SerializableInputStream) ois.readObject();

		final ByteArrayOutputStream readOutputStream = new ByteArrayOutputStream();
		int read = 0;
		final byte[] buffer = new byte[2048];
		while ((read = in.read(buffer)) > 0) {
			readOutputStream.write(buffer, 0, read);
		}
		in.close();

		final String recoveredPayloadString = new String(readOutputStream.toByteArray(), CHARSET);
		assertEquals(payloadString, recoveredPayloadString);
	}

	private String createPayload() {
		String ret = "";
		for (int i = 0; i < 500; i++) {
			ret += UUID.randomUUID().toString();
		}
		return ret;
	}

}
