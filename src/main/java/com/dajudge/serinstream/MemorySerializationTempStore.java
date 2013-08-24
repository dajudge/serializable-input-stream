package com.dajudge.serinstream;

/**
 * In-memory serialization temp store.
 * 
 * @author Alex Stockinger
 * 
 */
public class MemorySerializationTempStore implements SerializationTempStore {

	@Override
	public TempStoreInstance createTempStoreInstance() {
		return new MemorySerializationTempStoreInstance();
	}

	@Override
	public String toString() {
		return "in-memory temp store instance";
	}

}
