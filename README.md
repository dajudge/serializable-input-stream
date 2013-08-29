Serializable InputStream
========================

This is an implementation of an InputStream that can be serialized
using Java's standard serialization mechanism.

By default it uses the main memory to temporarily store the data
during deserialization in order to serve it as input stream again. 

This behavior however is configurable and other serialization temp stores
can be integrated easily, a file-based temp store using the user's temp
directory is provided as well.

TODO:
 - Don't allocate the whole chunk size on deserialization, this is a potential OutOfMemory
 - Allow to limit the file size during deserialization
