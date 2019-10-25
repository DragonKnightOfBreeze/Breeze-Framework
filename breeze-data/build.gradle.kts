import groovy.lang.*
import org.gradle.util.*

val optional: Action<ExternalModuleDependency> = (extra["optional"] as Closure<*>).let { ConfigureUtil.configureUsing(it) }

dependencies {
	api(project(":breeze-core"))
	api(project(":breeze-reflect"))
	
	implementation(kotlin("reflect"))
	
	implementation("com.google.code.gson:gson:2.8.5", optional)
	implementation("com.alibaba:fastjson:1.2.59", optional)
	implementation("org.yaml:snakeyaml:1.4", optional)
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.10.0.pr2", optional)
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.10.0.pr2", optional)
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:2.10.0.pr2", optional)
	implementation("com.fasterxml.jackson:jackson-dataformat-csv:0.5.0", optional)
}
