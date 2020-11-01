plugins {
	kotlin("plugin.noarg") version "1.4.0-rc"
	kotlin("plugin.allopen") version "1.4.0-rc"
}

noArg {
	annotation("com.windea.breezeframework.core.annotations.NoArg")
}

allOpen {
	annotation("com.windea.breezeframework.core.annotations.AllOpen")
}

dependencies {
	compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-runtime:1.0-M1-1.4.0-rc")
	compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.2")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.11.2")
	compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.11.2")
	compileOnly("com.google.code.gson:gson:2.8.6")
	compileOnly("com.alibaba:fastjson:1.2.62")
	compileOnly("org.yaml:snakeyaml:1.25")
}
