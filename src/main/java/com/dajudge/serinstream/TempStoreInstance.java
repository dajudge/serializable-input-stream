package com.dajudge.serinstream;

import java.io.IOException;
import java.io.InputStream;

/**
 * An instance in a temp store.
 * 
 * @author Alex Stockinger
 */
public interface TempStoreInstance {
    /**
     * Invoked to store the temporary data.
     * 
     * @param callback the callback to be used for storage.
     * @throws IOException
     * @param <T> the return value type.
     * @return the return value returned by the callback.
     */
    <T> T store(OutputStreamCallback<T> callback) throws IOException;

    /**
     * Retrieves an input stream to the temporary data. This method can only be invoked once and only if
     * <code>store(OutputStreamCallback)</code> was invoked successfully. You must close the {@link InputStream} if you
     * don't need it anymore.
     * 
     * @return the input stream to the temporary data.
     */
    InputStream retrieve();

    /**
     * Used to release all resources allocated by the instance once it is not required anymore. The method
     * {@link #retrieve()} must have been invoked and the returned {@link InputStream} must have been closed before
     * invoking this method..
     */
    void release();
}
