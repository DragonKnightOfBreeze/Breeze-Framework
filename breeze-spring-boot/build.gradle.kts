import groovy.lang.*
import org.gradle.util.*

plugins {
	kotlin("plugin.spring") version "1.3.50"
	kotlin("plugin.jpa") version "1.3.50"
}

val optional: Action<ExternalModuleDependency> = (extra["optional"] as Closure<*>).let { ConfigureUtil.configureUsing(it) }

dependencies {
	api(project(":breeze-core"))
	
	implementation(platform("org.springframework.boot:spring-boot-dependencies:2.1.8.RELEASE"))
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web", optional)
	implementation("org.springframework.boot:spring-boot-starter-aop", optional)
	implementation("org.springframework.boot:spring-boot-starter-data-jpa", optional)
	implementation("org.springframework.boot:spring-boot-starter-cache", optional)
	implementation("org.springframework.boot:spring-boot-starter-mail", optional)
	implementation("org.springframework.boot:spring-boot-starter-security", optional)
	testImplementation("org.springframework.boot:spring-boot-starter-test", optional)
	
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.1.8.RELEASE", optional)
	
	compileOnly("com.querydsl:querydsl-apt", optional)
	implementation("com.querydsl:querydsl-jpa", optional)
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin", optional)
}
