package com.dajudge.serinstream;

/**
 * A serialization temp store that uses temp files in the user's temporary
 * directory.
 * 
 * @author Alex Stockinger
 */
public class TempFileSerializationTempStore implements SerializationTempStore {

	private String prefix = "serialized_";
	private String suffix = "_tempStoreData";

	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	public void setSuffix(final String suffix) {
		this.suffix = suffix;
	}

	@Override
	public TempStoreInstance createTempStoreInstance() {
		return new TempFileTempStoreInstance(prefix, suffix);
	}

	@Override
	public String toString() {
		return "temporary file serialization store";
	}
}
