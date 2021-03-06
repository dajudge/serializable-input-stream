package com.dajudge.serinstream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@link OutputStreamCallback} that pipes chunked data produced by a
 * {@link SerializableInputStream} from an {@link ObjectInputStream} to the
 * provided {@link OutputStream}.
 * 
 * @author Alex Stockinger
 */
public final class PipeToTempStoreCallback implements OutputStreamCallback<Integer> {
	private final ObjectInputStream in;

	private static final Logger LOG = LoggerFactory.getLogger(PipeToTempStoreCallback.class);

	/**
	 * Constructor.
	 * 
	 * @param in
	 *            the {@link ObjectInputStream} to read the chunked data from.
	 */
	public PipeToTempStoreCallback(final ObjectInputStream in) {
		this.in = in;
	}

	@Override
	public Integer callback(final OutputStream stream) throws IOException {
		int overallBytes = 0;
		int toRead = in.readInt();
		overallBytes += toRead;
		byte[] buffer = null;
		while (toRead > 0) {
			LOG.trace("Reading up to " + toRead + " bytes...");
			buffer = allocateBuffer(toRead, buffer);
			final int readLocally = in.read(buffer, 0, toRead);
			LOG.trace("  ...read " + toRead + " bytes");
			stream.write(buffer, 0, readLocally);
			toRead -= readLocally;
			if (toRead == 0) {
				toRead = in.readInt();
				overallBytes += toRead;
			}
		}
		return overallBytes;
	}

	private byte[] allocateBuffer(final int size, final byte[] buffer) {
		if (buffer != null && buffer.length >= size) {
			return buffer;
		}
		return new byte[size];
	}
}