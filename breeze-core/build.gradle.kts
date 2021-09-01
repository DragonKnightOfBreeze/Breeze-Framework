jmh {
	includes.add("BuildMapBenchmark")
}

//benchmark {
//	configurations{
//		named("main"){
//			include("ForEachAsyncBenchmark")
//		}
//	}
//}

dependencies{
	testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
	testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.2")
	testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
	testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.4")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.4")
	testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.12.4")
	testImplementation("com.google.code.gson:gson:2.8.7")
	testImplementation("com.alibaba:fastjson:1.2.78")
	testImplementation("org.yaml:snakeyaml:1.29")
	testImplementation("com.google.guava:guava:30.1.1-jre")
	testImplementation("org.apache.commons:commons-lang3:3.12.0")
	testImplementation("commons-beanutils:commons-beanutils:1.9.4")
	testImplementation("com.github.fangjinuo.langx:langx:3.6.6")

	jmhImplementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
	jmhImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
	jmhImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
	jmhImplementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.2")
	jmhImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
	jmhImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
	jmhImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.4")
	jmhImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.4")
	jmhImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.12.4")
	jmhImplementation("com.google.code.gson:gson:2.8.7")
	jmhImplementation("com.alibaba:fastjson:1.2.78")
	jmhImplementation("org.yaml:snakeyaml:1.29")
	jmhImplementation("com.google.guava:guava:30.1.1-jre")
	jmhImplementation("org.apache.commons:commons-lang3:3.12.0")
	jmhImplementation("commons-beanutils:commons-beanutils:1.9.4")
	jmhImplementation("com.github.fangjinuo.langx:langx:3.6.6")

	//benchmarksImplementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
	//benchmarksImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
	//benchmarksImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
	//benchmarksImplementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.2")
	//benchmarksImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
	//benchmarksImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
	//benchmarksImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.4")
	//benchmarksImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.4")
	//benchmarksImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.12.4")
	//benchmarksImplementation("com.google.code.gson:gson:2.8.7")
	//benchmarksImplementation("com.alibaba:fastjson:1.2.78")
	//benchmarksImplementation("org.yaml:snakeyaml:1.29")
	//benchmarksImplementation("com.google.guava:guava:30.1.1-jre")
	//benchmarksImplementation("org.apache.commons:commons-lang3:3.12.0")
	//benchmarksImplementation("commons-beanutils:commons-beanutils:1.9.4")
	//benchmarksImplementation("com.github.fangjinuo.langx:langx:3.6.6")
	//benchmarksImplementation(sourceSets.test.get().output + sourceSets.test.get().runtimeClasspath)
}
