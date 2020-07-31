plugins {
	kotlin("plugin.serialization") version "1.4.0-rc"
}

dependencies {
	api(project(":breeze-core"))
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
}
