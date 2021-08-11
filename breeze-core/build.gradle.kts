jmh {
	includes.add("StringBenchmark")
}

dependencies{
	jmh("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
	jmh("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.2")
	jmh("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
	jmh("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
	jmh("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.4")
	jmh("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.4")
	jmh("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.12.4")
	jmh("com.google.code.gson:gson:2.8.7")
	jmh("com.alibaba:fastjson:1.2.76")
	jmh("org.yaml:snakeyaml:1.29")
	jmh("com.google.guava:guava:30.1.1-jre")
	jmh("org.apache.commons:commons-lang3:3.12.0")
	jmh("commons-beanutils:commons-beanutils:1.9.4")
	jmh("com.github.fangjinuo.langx:langx:3.6.2")

	testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
	testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.2")
	testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
	testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.4")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.4")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.12.4")
	testImplementation("com.google.code.gson:gson:2.8.7")
	testImplementation("com.alibaba:fastjson:1.2.76")
	testImplementation("org.yaml:snakeyaml:1.29")
	testImplementation("com.google.guava:guava:30.1.1-jre")
	testImplementation("org.apache.commons:commons-lang3:3.12.0")
	testImplementation("commons-beanutils:commons-beanutils:1.9.4")
	testImplementation("com.github.fangjinuo.langx:langx:3.6.2")
}
