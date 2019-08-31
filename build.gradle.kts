@file:Suppress("SpellCheckingInspection")

import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.jvm.tasks.Jar

plugins {
	id("org.gradle.maven-publish")
	id("com.gradle.build-scan") version "2.4.1"
	id("org.jetbrains.dokka") version "0.9.18"
	kotlin("jvm") version "1.3.50"
}

group = "com.windea.breezeframework"
version = "1.0.0"

allprojects {
	repositories {
		//使用阿里云代理解决Gradle构建过慢的问题
		maven("http://maven.aliyun.com/nexus/content/groups/public/")
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	
	dependencies {
		implementation(kotlin("stdlib"))
		implementation(kotlin("reflect"))
		testImplementation(kotlin("test"))
		testImplementation(kotlin("test-junit"))
		
		implementation("io.github.microutils:kotlin-logging:1.6.26")
	}
}


tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "11"
}

tasks.dokka {
	outputFormat = "html"
	outputDirectory = "$buildDir/javadoc"
}

val dokkaJar by tasks.creating(Jar::class) {
	from(tasks.dokka)
}

val sourcesJar by tasks.creating(Jar::class) {
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
