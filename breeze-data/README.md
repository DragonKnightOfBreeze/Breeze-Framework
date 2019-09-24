# Summary

Provide:

* Builder extensions for `equals()`, `hashcode()` and `toString()`.
* Some useful text generators.
* Wrapped data loaders and dumpers for multi implementations, such as gson, jackson. 
* Necessary extensions for convenience.

Future plan:

* [ ] Fully adapt to kotlinx-serialization.
* [ ] Provide own-implemented, tiny and flexible serializer implementations.
* [ ] Link each dsl to corresponding loader and dumper.	

# Struct

* annotations
* domain
* enums
* extensions
* generators
    * specific
    * string
    * text
* serializers
    * csv
    * json
    * properties
    * xml
    * yaml
