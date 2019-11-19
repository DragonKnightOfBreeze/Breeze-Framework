plugins {
	kotlin("plugin.serialization") version "1.3.60"
}

dependencies {
	api(project(":breeze-core"))
	
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.13.0")
}
