import groovy.lang.*
import org.gradle.util.*

plugins {
	id("nebula.optional-base") version "3.0.3"
}

val optional: Action<ExternalModuleDependency> = (extra["optional"] as Closure<*>).let { ConfigureUtil.configureUsing(it) }

dependencies {
	api(project(":breeze-core"))
	api(project(":breeze-reflect"))

	implementation(kotlin("reflect"))

	implementation("com.google.code.gson:gson:2.8.6", optional)
	implementation("com.alibaba:fastjson:1.2.62", optional)
	implementation("org.yaml:snakeyaml:1.25", optional)
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.1", optional)
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.10.1", optional)
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.10.1", optional)
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.10.1", optional)
}
