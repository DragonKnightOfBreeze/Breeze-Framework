dependencies {
	api(project(":breeze-core"))
	api(project(":breeze-reflect"))
	implementation(kotlin("reflect:1.4.0"))

	compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0")
	compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
	//compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-yaml:1.0.0")
	//compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-xml:1.0.0")
	//compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-properties:1.0.0")
	compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-csv:1.0.0")
	compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.11.2")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.11.2")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.11.2")
	compileOnly("com.google.code.gson:gson:2.8.6")
	compileOnly("com.alibaba:fastjson:1.2.62")
	compileOnly("org.yaml:snakeyaml:1.26")
	//Avro (Avro format)
	//CBOR (CBOR format)
	//Ion (Ion format)
	//Protobuf (Protobuf format)
	//Smile (Smile format)

	testCompileOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0")
	testCompileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
	testCompileOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
	testCompileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")
	testCompileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.11.2")
	testCompileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.11.2")
	testCompileOnly("com.google.code.gson:gson:2.8.6")
	testCompileOnly("com.alibaba:fastjson:1.2.62")
	testCompileOnly("org.yaml:snakeyaml:1.26")
}

buildscript {
	//配置插件仓库
	repositories {
		maven("https://dl.bintray.com/kotlin/kotlin-eap")
		maven("https://maven.aliyun.com/nexus/content/groups/public")
		mavenCentral()
		jcenter()
	}
}

//配置依赖仓库
repositories {
	maven("https://dl.bintray.com/kotlin/kotlin-eap")
	maven("https://maven.aliyun.com/nexus/content/groups/public")
	mavenCentral()
	jcenter()
}
