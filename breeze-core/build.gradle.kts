@file:Suppress("SpellCheckingInspection")

plugins {
	kotlin("plugin.noarg") version "1.3.50"
	kotlin("plugin.allopen") version "1.3.50"
}

noArg {
	annotation("com.windea.breezeframework.core.annotations.NoArg")
}

allOpen {
	annotation("com.windea.breezeframework.core.annotations.AllOpen")
}

//dependencies {
	//implementation("org.kodein.di:kodein-di-generic-jvm:6.3.3")
	//testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek_version")
	//testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek_version")
//}
