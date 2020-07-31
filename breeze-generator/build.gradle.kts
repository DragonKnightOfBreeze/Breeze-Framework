dependencies {
	api(project(":breeze-core"))
	api(project(":breeze-serializer"))

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.1")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.10.1")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.10.1")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.10.1")
}
