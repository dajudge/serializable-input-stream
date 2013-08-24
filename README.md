Serializable input stream
=========================

This is an implementation of an input stream that can be serialized
using Java's standard serialization mechanism.

By default it uses files in the user's temporary directory to temporarily
store the data during deserialization in order to serve it as input stream
again. 

This behavior however is configurable and other serialization temp stores
can be integrated easily.

TODO:
 - Make writing chunk size configurable
 - Don't allocate the whole chunk size on deserialization, this is a potential OutOfMemory
 - Allow to limit the file size during deserialization