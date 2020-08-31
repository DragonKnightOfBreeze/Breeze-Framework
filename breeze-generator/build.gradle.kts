dependencies {
	api(project(":breeze-core"))
	api(project(":breeze-serializer"))

	implementation(kotlin("reflect:1.4.0"))

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.11.2")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.11.2")
}
