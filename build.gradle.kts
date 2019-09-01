@file:Suppress("SpellCheckingInspection")

import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
	id("org.gradle.maven-publish")
	id("com.gradle.build-scan") version "2.4.1"
	id("org.jetbrains.dokka") version "0.9.18"
	id("nebula.optional-base") version "3.0.3"
	kotlin("jvm") version "1.3.50"
}

group = "com.windea.breezeframework"
version = "1.0.1"

allprojects {
	repositories {
		//使用阿里云代理解决Gradle构建过慢的问题
		maven("http://maven.aliyun.com/nexus/content/groups/public/")
		mavenCentral()
	}
	
	tasks.withType<KotlinCompile> {
		kotlinOptions.jvmTarget = "11"
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "nebula.optional-base")
	
	sourceSets["main"].java.setSrcDirs(setOf("src/main/kotlin"))
	sourceSets["test"].java.setSrcDirs(setOf("src/main/kotlin"))
	
	dependencies {
		implementation(kotlin("stdlib"))
		implementation(kotlin("reflect"))
		implementation(kotlin("script-util"))
		testImplementation(kotlin("test"))
		testImplementation(kotlin("test-junit"))
		
		implementation("io.github.microutils:kotlin-logging:1.6.26")
		implementation("org.slf4j:slf4j-simple:2.0.0-alpha0")
	}
}

tasks.dokka {
	outputFormat = "html"
	outputDirectory = "$buildDir/javadoc"
}

val dokkaJar by tasks.creating(Jar::class) {
	classifier = "javadoc"
	from(tasks.dokka)
}

val sourcesJar by tasks.creating(Jar::class) {
	classifier = "source"
	from(sourceSets.main.get().allSource)
}

publishing {
	publications {
		create<MavenPublication>("default") {
			from(components["java"])
			artifact(sourcesJar)
			artifact(dokkaJar)
		}
	}
	repositories {
		maven {
			url = uri("$buildDir/repository")
		}
	}
}
