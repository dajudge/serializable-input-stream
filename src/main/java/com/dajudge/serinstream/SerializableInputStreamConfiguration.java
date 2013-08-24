package com.dajudge.serinstream;

/**
 * A singleton class to configure serialization used by
 * {@link SerializableInputStream}. The default configuration serialized to a
 * file in the user's temp directory, imposes no limit on the upload size and
 * does not use encryption.
 * 
 * @author Alex Stockinger
 */
public class SerializableInputStreamConfiguration {
	private static SerializableInputStreamConfiguration INSTANCE = createDefaultConfiguration();

	private SerializationTempStore tempStore;

	/**
	 * Creates the default configuration.
	 * 
	 * @return the default configuration.
	 */
	private static SerializableInputStreamConfiguration createDefaultConfiguration() {
		final SerializableInputStreamConfiguration ret = new SerializableInputStreamConfiguration();
		ret.setSerializationTempStore(new TempFileSerializationTempStore());
		return ret;
	}

	/**
	 * Sets the serialization temp store. The temp store is used for persisting
	 * serialization data while the stream is retrieved. By default the user's
	 * temporary directory is used for storing the data in order to avoid out of
	 * memory situations.
	 * 
	 * @param tempStore
	 *            the temp store the be used for deserialization.
	 */
	public void setSerializationTempStore(final SerializationTempStore tempStore) {
		this.tempStore = tempStore;
	}

	public SerializationTempStore getSerializationTempStore() {
		return tempStore;
	}

	/**
	 * Retrieves the singleton instance.
	 * 
	 * @return the singleton instance.
	 */
	public static SerializableInputStreamConfiguration getInstance() {
		return INSTANCE;
	}
}
