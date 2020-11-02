dependencies {
	api(project(":breeze-core"))
	api(project(":breeze-mapper"))

	implementation(kotlin("reflect:1.4.0"))

	compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0")
	compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
	compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.11.2")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.11.2")
	compileOnly("com.google.code.gson:gson:2.8.6")
	compileOnly("com.alibaba:fastjson:1.2.62")
	compileOnly("org.yaml:snakeyaml:1.25")

	testCompileOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
	testCompileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")
	testCompileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.11.2")
	testCompileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.11.2")
}
