dependencies {
	api(project(":breeze-core"))
	api(project(":breeze-serialization"))
	api(project(":breeze-reflect"))
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")

	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("com.google.guava:guava:30.1.1-jre")

	implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.2")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.4")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.4")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.4")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.12.4")
	implementation("com.google.code.gson:gson:2.8.7")
	implementation("com.alibaba:fastjson:1.2.76")
	implementation("org.yaml:snakeyaml:1.29")
	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("commons-beanutils:commons-beanutils:1.9.4")
	implementation("com.github.fangjinuo.langx:langx:3.6.2")
}
