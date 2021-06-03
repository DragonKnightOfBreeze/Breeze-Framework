jmh {
	this.includes.add("DefaultGeneratorBenchmark")
}

dependencies {
	testImplementation("com.google.guava:guava:28.2-jre")
	testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:1.0-M1-1.4.0-rc")
	testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.11.2")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.11.2")
	testImplementation("com.google.code.gson:gson:2.8.6")
	testImplementation("com.alibaba:fastjson:1.2.62")
	testImplementation("org.yaml:snakeyaml:1.25")
	testImplementation("org.apache.commons:commons-lang3:3.12.0")
	testImplementation("commons-beanutils:commons-beanutils:1.9.4")
	testImplementation("com.github.fangjinuo.langx:langx:3.5.3")
}
