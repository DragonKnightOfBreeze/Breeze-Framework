dependencies {
	api(project(":breeze-core"))

	compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.13.0")
	compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.1")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.10.1")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.10.1")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.10.1")
	compileOnly("com.google.code.gson:gson:2.8.6")
	compileOnly("com.alibaba:fastjson:1.2.62")
	compileOnly("org.yaml:snakeyaml:1.25")

	testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.1")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.10.1")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.10.1")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.10.1")
}