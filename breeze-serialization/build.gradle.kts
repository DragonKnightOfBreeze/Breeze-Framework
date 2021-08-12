dependencies {
	api(project(":breeze-core"))
	api(project(":breeze-reflect"))
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")

	compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.2")
	compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
	//compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-yaml:1.0.0")
	//compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-xml:1.0.0")
	//compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-properties:1.0.0")
	//compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-csv:1.0.0")
	compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.4")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.4")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.12.4")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.12.4")
	compileOnly("com.google.code.gson:gson:2.8.7")
	compileOnly("com.alibaba:fastjson:1.2.76")
	compileOnly("org.yaml:snakeyaml:1.29")
	//Avro (Avro format)
	//CBOR (CBOR format)
	//Ion (Ion format)
	//Protobuf (Protobuf format)
	//Smile (Smile format)

	testCompileOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.2")
	testCompileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
	//testCompileOnly("org.jetbrains.kotlinx:kotlinx-serialization-yaml:1.0.0")
	//testCompileOnly("org.jetbrains.kotlinx:kotlinx-serialization-xml:1.0.0")
	//testCompileOnly("org.jetbrains.kotlinx:kotlinx-serialization-properties:1.0.0")
	//testCompileOnly("org.jetbrains.kotlinx:kotlinx-serialization-csv:1.0.0")
	testCompileOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
	testCompileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.4")
	testCompileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.4")
	testCompileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.12.4")
	testCompileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.12.4")
	testCompileOnly("com.google.code.gson:gson:2.8.7")
	testCompileOnly("com.alibaba:fastjson:1.2.76")
	testCompileOnly("org.yaml:snakeyaml:1.29")
	//Avro (Avro format)
	//CBOR (CBOR format)
	//Ion (Ion format)
	//Protobuf (Protobuf format)
	//Smile (Smile format)
}
