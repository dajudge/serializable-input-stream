package com.dajudge.serinstream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A callback that is given an output stream to operate on. TODO: class comment
 * 
 * @param <T> the return value type.
 * @author Alex Stockinger
 */
public interface OutputStreamCallback<T> {

    /**
     * The callback that is invoked with the output stream that can be used to write data.
     * 
     * @param stream the stream that can be used to write data.
     * @return an arbitrary return value.
     * @throws IOException
     */
    T callback(OutputStream stream) throws IOException;
}
