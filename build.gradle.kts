//配置要用到的插件
plugins {
	id("org.gradle.maven-publish")
	id("org.gradle.signing")
	id("org.jetbrains.kotlin.jvm") version "1.6.0"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.6.0"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.6.0"
	id("org.jetbrains.dokka") version "1.6.0"
	id("me.champeau.jmh") version "0.6.6"
	//id("org.jetbrains.kotlinx.benchmark") version "0.3.1" apply false //未成功执行benchmark
}

val groupName = "icu.windea.breezeframework"
val versionName = "3.1.1"
val packagePrefix = "icu.windea.breezeframework"
val compilerArgs = listOf(
	"-Xinline-classes",
	"-Xopt-in=kotlin.RequiresOptIn",
	"-Xopt-in=kotlin.ExperimentalStdlibApi",
	"-Xopt-in=kotlin.contracts.ExperimentalContracts",
	"-Xopt-in=icu.windea.breezeframework.core.annotation.InternalApi",
	"-Xopt-in=icu.windea.breezeframework.core.annotation.UnstableApi",
	"-Xopt-in=icu.windea.breezeframework.core.annotation.TrickApi"
)
val noPublishModuleNames = arrayOf("breeze-unstable", "breeze-generator")
val java11ModuleNames = arrayOf("breeze-http", "breeze-javafx", "breeze-unstable")

allprojects {
	val projectName = project.name
	val projectTitle = projectName.split("-").joinToString(" ") { it.capitalize() }
	val projectJavaVersion = when {
		project == rootProject || projectName in java11ModuleNames -> "11"
		else -> "1.8"
	}
	val projectPackagePrefix = packagePrefix

	group = groupName
	version = versionName

	//应用插件
	apply {
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.dokka")
		plugin("org.jetbrains.kotlin.plugin.noarg")
		plugin("org.jetbrains.kotlin.plugin.allopen")
		plugin("me.champeau.jmh")
		//plugin("org.jetbrains.kotlinx.benchmark")
	}

	runCatching {
		apply{
			from("$projectDir/jmh.gradle")
		}
	}

	java {
		toolchain {
			when(project.name) {
				in java11ModuleNames -> languageVersion.set(JavaLanguageVersion.of(11))
				else -> languageVersion.set(JavaLanguageVersion.of(8))
			}
		}
	}

	kotlin {
		//explicitApiWarning()
		jvmToolchain{
			this as JavaToolchainSpec
			when(project.name) {
				in java11ModuleNames -> languageVersion.set(JavaLanguageVersion.of(11))
				else -> languageVersion.set(JavaLanguageVersion.of(8))
			}
		}
	}

	noArg {
		annotation("icu.windea.breezeframework.core.annotation.NoArg")
	}

	allOpen {
		annotation("icu.windea.breezeframework.core.annotation.AllOpen")
		annotation("org.openjdk.jmh.annotations.BenchmarkMode") //jmh压测类需要开放
	}

	//配置jmh
	jmh {

	}

	//sourceSets.create("benchmarks")
	//
	//benchmark {
	//	targets {
	//		register("jmh")
	//	}
	//}

	//配置依赖仓库
	repositories {
		maven("https://maven.aliyun.com/nexus/content/groups/public")
		mavenCentral()
		maven("https://dl.bintray.com/kotlin/kotlin-eap")
	}

	//配置依赖
	dependencies {
		implementation("org.jetbrains.kotlin:kotlin-stdlib")
		testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
	}

	val projectCompiler = javaToolchains.compilerFor {
		when(project.name) {
			in java11ModuleNames -> languageVersion.set(JavaLanguageVersion.of(11))
			else -> languageVersion.set(JavaLanguageVersion.of(8))
		}
	}

	tasks {
		compileJava {
			javaCompiler.set(projectCompiler)
		}
		compileTestJava {
			javaCompiler.set(projectCompiler)
		}
		compileJmhJava {
			javaCompiler.set(projectCompiler)
		}
		compileKotlin {
			javaPackagePrefix = projectPackagePrefix
			kotlinOptions {
				jvmTarget = projectJavaVersion
				freeCompilerArgs = compilerArgs
			}
		}
		compileTestKotlin {
			javaPackagePrefix = projectPackagePrefix
			kotlinOptions {
				jvmTarget = projectJavaVersion
				freeCompilerArgs = compilerArgs
			}
		}
		compileJmhKotlin{
			javaPackagePrefix = projectPackagePrefix
			kotlinOptions {
				jvmTarget = projectJavaVersion
				freeCompilerArgs = compilerArgs
			}
		}
	}

	//配置是否需要发布
	if(!property("publish").toString().toBoolean()) return@allprojects
	//配置需要发布的模块
	if(project == rootProject || project.name in noPublishModuleNames) return@allprojects

	tasks{
		withType<org.jetbrains.dokka.gradle.DokkaTask> {
			dokkaSourceSets {
				named("main") {
					moduleName.set(projectTitle)
					//includes.from("README.md")
				}
			}
		}
	}

	//准备发布模块

	apply {
		plugin("org.gradle.maven-publish")
		plugin("org.gradle.signing")
	}

	val sourcesJar by tasks.register<Jar>("sourcesJar") {
		from(sourceSets.main.get().allSource)
		from("$rootDir/LICENSE") //添加LICENSE
		archiveClassifier.set("sources")
	}

	val dokkaJavadocJar by tasks.register<Jar>("dokkaJavadocJar") {
		dependsOn(tasks.dokkaJavadoc)
		from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
		archiveClassifier.set("javadoc")
	}

	//val dokkaHtmlJar by tasks.register<Jar>("dokkaHtmlJar") {
	//	dependsOn(tasks.dokkaHtml)
	//	from(tasks.dokkaHtml.flatMap { it.outputDirectory })
	//	archiveClassifier.set("html-doc")
	//}

	artifacts {
		archives(sourcesJar)
		archives(dokkaJavadocJar)
		//archives(dokkaHtmlJar)
	}

	//上传的配置
	publishing {
		//配置包含的jar
		publications {
			//创建maven的jar
			register<MavenPublication>("maven") {
				from(components["java"])
				artifact(sourcesJar)
				artifact(dokkaJavadocJar)
				//artifact(dokkaHtmlJar)
				pom {
					name.set(projectTitle)
					description.set("Integrated code framework written by Kotlin.")
					url.set("https://github.com/DragonKnightOfBreeze/Breeze-Framework")
					licenses {
						license {
							name.set("MIT License")
							url.set("https://github.com/DragonKnightOfBreeze/Breeze-Framework/blob/master/LICENSE")
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
						url.set("https://github.com/DragonKnightOfBreeze/Breeze-Framework")
						connection.set("scm:git@github.com:DragonKnightOfBreeze/Breeze-Framework.git")
						developerConnection.set("scm:git@github.com:DragonKnightOfBreeze/Breeze-Framework.git")
					}
				}
			}
		}
		//配置上传到的仓库
		repositories {
			//github pages
			maven {
				name = "githubPages"
				url = uri("https://maven.pkg.github.com/dragonknightofbreeze/breeze-framework")
				credentials {
					username = property("GITHUB_USERNAME").toString()
					password = property("GITHUB_PASSWORD").toString()
				}
			}
			//sonatype repository
			maven {
				name = "sonatypeRepository"
				url = uri("https://s01.oss.sonatype.org//service/local/staging/deploy/maven2")
				credentials {
					username = property("OSSRH_USERNAME").toString()
					password = property("OSSRH_PASSWORD").toString()
				}
			}
			//sonatype snapshot repository
			maven {
				name = "sonatypeSnapshotRepository"
				url = uri("https://s01.oss.sonatype.org//content/repositories/snapshots/")
				credentials {
					username = property("OSSRH_USERNAME").toString()
					password = property("OSSRH_PASSWORD").toString()
				}
			}
		}
	}

	//签名
	signing {
		//sign(configurations.archives.get())
		sign(publishing.publications.getByName("maven"))
	}
}
