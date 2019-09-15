import groovy.lang.*
import org.gradle.util.*

plugins {
	id("org.springframework.boot") version "2.1.7.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("plugin.spring") version "1.3.50"
	kotlin("plugin.jpa") version "1.3.50"
}

val optional: Action<ExternalModuleDependency> = (extra["optional"] as Closure<*>).let { ConfigureUtil.configureUsing(it) }

dependencies {
	api(project(":breeze-core"))
	
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web", optional)
	implementation("org.springframework.boot:spring-boot-starter-aop", optional)
	implementation("org.springframework.boot:spring-boot-starter-data-jpa", optional)
	implementation("org.springframework.boot:spring-boot-starter-cache", optional)
	implementation("org.springframework.boot:spring-boot-starter-mail", optional)
	implementation("org.springframework.boot:spring-boot-starter-security", optional)
	testImplementation("org.springframework.boot:spring-boot-starter-test", optional)
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor", optional)
	
	compileOnly("com.querydsl:querydsl-apt", optional)
	implementation("com.querydsl:querydsl-jpa", optional)
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin", optional)
}
