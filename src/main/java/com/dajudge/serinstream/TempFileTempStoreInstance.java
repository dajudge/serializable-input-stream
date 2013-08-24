package com.dajudge.serinstream;

import static java.io.File.createTempFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An instance inside a {@link TempFileSerializationTempStore}.
 * 
 * @author Alex Stockinger
 */
public class TempFileTempStoreInstance implements TempStoreInstance {
    private static final Logger LOG = LoggerFactory.getLogger(TempFileTempStoreInstance.class);
    private final String prefix;
    private final String suffix;
    private File file;
    private FileInputStream stream;

    /**
     * Constructor.
     * 
     * @param prefix the prefix to be used when creating the temp files.
     * @param suffix the suffix to be used when creating the temp files.
     */
    public TempFileTempStoreInstance(final String prefix, final String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public <T> T store(final OutputStreamCallback<T> callback) throws IOException {
        file = createTempFile(prefix, suffix);
        LOG.debug("Creating temporary file serialization store instance at " + file.getAbsolutePath());
        final FileOutputStream fos = new FileOutputStream(file);
        T ret;
        try {
            ret = callback.callback(fos);
        } finally {
            fos.close();
        }
        stream = new FileInputStream(file);
        return ret;
    }

    @Override
    public InputStream retrieve() {
        if (stream == null) {
            throw new IllegalStateException("No input stream is available");
        }
        final FileInputStream ret = stream;
        stream = null;
        return ret;
    }

    @Override
    public void release() {
        if (stream != null) {
            throw new IllegalStateException("The input stream has not been retrieved, yet");
        }
        if (!file.delete()) {
            LOG.warn("The temporary file " + file.getAbsolutePath() + " could not be deleted.");
        } else {
            LOG.debug("Deleted temporary file " + file.getAbsolutePath());
        }
    }

}
