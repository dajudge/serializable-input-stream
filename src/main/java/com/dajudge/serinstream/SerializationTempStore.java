package com.dajudge.serinstream;

/**
 * Interface to handle storage of serialization temp data.
 * 
 * @author Alex Stockinger
 */
public interface SerializationTempStore {

	/**
	 * Creates an instance in the temp store that can be used for storing the
	 * temporary data.
	 * 
	 * @return an instance in the temp store that can be used for storing the
	 *         temporary data.
	 */
	TempStoreInstance createTempStoreInstance();
}
