@file:Suppress("SpellCheckingInspection")

import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
	`maven-publish`
	id("nebula.optional-base") version "3.0.3"
	id("com.jfrog.bintray") version "1.8.4"
	kotlin("jvm") version "1.3.50"
	id("org.jetbrains.dokka") version "0.9.18"
}

allprojects {
	apply(plugin = "org.gradle.maven-publish")
	apply(plugin = "nebula.optional-base")
	apply(plugin = "com.jfrog.bintray")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.dokka")
	
	//version需要写到allprojects里面
	group = "com.windea.breezeframework"
	version = "1.0.1"
	
	val siteUrl = "https://github.com/DragonKnightOfBreeze/breeze-framework"
	val gitUrl = "https://github.com/DragonKnightOfBreeze/breeze-framework.git"
	
	repositories {
		//使用阿里云代理解决Gradle构建过慢的问题
		maven("http://maven.aliyun.com/nexus/content/groups/public/")
		mavenCentral()
		jcenter()
	}
	
	sourceSets["main"].java.setSrcDirs(setOf("src/main/kotlin"))
	sourceSets["test"].java.setSrcDirs(setOf("src/main/kotlin"))
	
	dependencies {
		implementation(kotlin("stdlib"))
		implementation(kotlin("reflect"))
		testImplementation(kotlin("test"))
		testImplementation(kotlin("test-junit"))
	}
	
	tasks.withType<KotlinCompile> {
		kotlinOptions.jvmTarget = "11"
	}
	
	//配置dokka
	tasks.dokka {
		outputFormat = "html"
		outputDirectory = "$buildDir/javadoc"
	}
	
	//构建source jar
	val sourcesJar by tasks.creating(Jar::class) {
		archiveClassifier.set("sources")
		from(sourceSets.main.get().allJava)
	}
	
	//构建javadoc jar
	val javdocJar by tasks.creating(Jar::class) {
		archiveClassifier.set("javadoc")
		group = JavaBasePlugin.DOCUMENTATION_GROUP
		description = "Kotlin documents of Breeze Framework."
		from(tasks.dokka)
	}
	
	//上传的配置
	publishing {
		//配置包含的jar
		publications {
			create<MavenPublication>("maven") {
				from(components["java"])
				artifact(sourcesJar)
				artifact(javdocJar)
				
				pom {
					name.set("Breeze Framework")
					description.set("""
						Integrated code framework base on Kotlin,
						provide many useful extensions for standard library and some frameworks.
					""".trimIndent())
					url.set("https://github.com/DragonKnightOfBreeze/breeze-framework")
					licenses {
						license {
							name.set("MIT License")
							url.set("https://github.com/DragonKnightOfBreeze/breeze-framework/blob/master/LICENSE")
						}
					}
					developers {
						developer {
							id.set("DragonKnightOfBreeze")
							name.set("Windea")
							email.set("dk_breeze@qq.com")
						}
					}
					scm {
						connection.set(gitUrl)
						developerConnection.set(gitUrl)
						url.set(siteUrl)
					}
				}
			}
			
			//配置上传到的仓库
			repositories {
				//maven本地仓库
				maven {
					url = uri("$buildDir/repository")
				}
				//jcenter远程公共仓库
				bintray {
					//从系统环境变量得到bintray的user和api key，可能需要重启电脑生效
					user = System.getenv("BINTRAY_USER")
					key = System.getenv("BINTRAY_API_KEY")
					setPublications("maven")
					pkg.userOrg = "breeze-knights"
					pkg.repo = rootProject.name
					pkg.name = project.name
					pkg.websiteUrl = siteUrl
					pkg.vcsUrl = gitUrl
					pkg.setLabels("kotlin", "framework")
					pkg.setLicenses("MIT")
					pkg.version.name = "1.0.1"
					pkg.version.vcsTag = "1.0.x"
				}
			}
		}
	}
}


